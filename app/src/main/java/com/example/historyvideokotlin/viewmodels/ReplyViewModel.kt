package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.MyReply
import com.example.historyvideokotlin.model.Reply
import com.example.historyvideokotlin.model.ReplyRequest
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReplyViewModel(application: Application) : BaseViewModel(application) {

    val replyList = MutableLiveData<List<Reply>>()
    private val replys = mutableListOf<Reply>()

    private val _comment = MutableLiveData<Comment>()
    val comment: LiveData<Comment> get() = _comment

    private val _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> get() = _isLiked

    private val _isDisliked = MutableLiveData(false)
    val isDisliked: LiveData<Boolean> get() = _isDisliked

    private var likes = 0
    private val _likeCount = MutableLiveData(0)
    val likeCount: LiveData<Int> get() = _likeCount

    private var dislikes = 0
    private val _dislikeCount = MutableLiveData(0)
    val dislikeCount: LiveData<Int> get() = _dislikeCount

    private val userId = HistoryUserManager.instance.UserId()

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository
    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    val myReplyList = MutableLiveData<List<MyReply>>()
    private val myReplys = mutableListOf<MyReply>()

    val isLikeList = MutableLiveData<List<Int>>()
    private val isLikes = mutableListOf<Int>()

    val isPostReply = MutableLiveData(false)

    init {
        myReplys.clear()
        isLikes.clear()
    }

    fun getReplyData(commentId: Int) {
        getReply(commentId)
    }

    private fun getReply(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getReply(commentId)
            }.onSuccess {
                replyList.value = it
                replys.addAll(it)
                MyLog.e("getReply", it.toString())
            }.onFailure {
                MyLog.e("getReply: ", it.message)
            }
        }
    }

    fun getMyReplytList(videoId: Int, commentId: Int) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getMyReplyList(userId, videoId, commentId)
            }.onSuccess {
                myReplys.addAll(it)
                MyLog.e("myReplys", it.toString())
                getIsLikeList()
                hideLoading()
            }.onFailure {
                hideLoading()
            }
        }
    }

    private fun getIsLikeList() {
        for (i in 0 until replys.size) {
            isLikes.add(0)
            for (j in myReplys) {
                if (j.reply_id!!.equals(replys[i].reply_id)) {
                    isLikes.set(i, j.isLike!!)
                    break
                }
            }
        }
        isLikeList.value = isLikes
        MyLog.e("isLikeList", isLikes.size.toString() + " " + isLikes.toString())
    }

    fun setComment(comment: Comment) {
        _comment.value = comment
        likes = comment.like_count
        dislikes = comment.dislike_count
        _likeCount.value = likes
        _dislikeCount.value = dislikes
    }

    fun setLikeData(isLikeData: Int) {
        when (isLikeData) {
            0 -> {
                _isLiked.value = false
                _isDisliked.value = false
            }
            1 -> {
                _isDisliked.value = false
                _isLiked.value = true
            }
            2 -> {
                _isLiked.value = false
                _isDisliked.value = true
            }
        }
    }

    fun updateLikeData(isLikeData: Int, commentId: Int) {
        when (isLikeData) {
            0 -> {
                if (_isLiked.value!!) {
                    _isLiked.value = false
                    updateCommentLikeCancel(commentId)
                }
                if (_isDisliked.value!!) {
                    _isDisliked.value = false
                    updateCommentDislikeCancel(commentId)
                }
            }
            1 -> {
                if (_isDisliked.value!!) {
                    _isDisliked.value = false
                    updateCommentDislikeCancel(commentId)
                }
                _isLiked.value = true
                updateCommentLike(commentId)
            }
            2 -> {
                if (_isLiked.value!!) {
                    _isLiked.value = false
                    updateCommentLikeCancel(commentId)
                }
                _isDisliked.value = true
                updateCommentDislike(commentId)
            }
        }
    }

    fun createReply(reply: ReplyRequest) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.createReply(reply)
            }.onSuccess {
                isPostReply.value = true
                MyLog.e("createReply: ", it.data.toString())
            }.onFailure {
                isPostReply.value = false
                MyLog.e("createReply: ", it.message)
            }
        }
    }

    fun updateMyReplyLike(videoId: Int, commentId: Int, replyId: Int, isLike: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(
                    ktorUserRepository.updateMyReplyLike(
                        userId,
                        videoId,
                        commentId,
                        replyId,
                        isLike
                    )
                )
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateMyCommentLike", it.data.toString())
                }
            }
        }
    }

    fun updateReplyLike(replyId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateReplyLike(replyId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateReplyLike: ", it.data.toString())
                }
            }
        }
    }

    fun updateReplyLikeCancel(replyId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateReplyLikeCancel(replyId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateMyCommentLike", it.data.toString())
                }
            }
        }
    }

    fun updateReplyDislike(replyId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateReplyDislike(replyId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateReplyDislike", it.data.toString())
                }
            }
        }
    }

    fun updateReplyDislikeCancel(replyId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateReplyDislikeCancel(replyId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateReplyDislikeCancel", it.data.toString())
                }
            }
        }
    }

    fun updateMyCommentLike(videoId: Int, commentId: Int, isLike: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorUserRepository.updateMyCommentLike(userId, videoId, commentId, isLike))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateMyCommentLike", it.data.toString())
                }
            }
        }
    }

    fun updateCommentLike(commentId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateCommentLike(commentId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    _likeCount.value = it.data
                    MyLog.e("updateCommentDislike", it.data.toString())
                }
            }
        }
    }

    fun updateCommentDislike(commentId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateCommentDislike(commentId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    _dislikeCount.value = it.data
                    MyLog.e("updateCommentDislike", it.data.toString())
                }
            }
        }
    }

    fun updateCommentLikeCancel(commentId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateCommentLikeCancel(commentId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    _likeCount.value = it.data
                    MyLog.e("updateCommentLikeCancel", it.data.toString())
                }
            }
        }
    }

    fun updateCommentDislikeCancel(commentId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateCommentDislikeCancel(commentId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    _dislikeCount.value = it.data
                    MyLog.e("updateCommentDislikeCancel", it.data.toString())
                }
            }
        }
    }

// ///////////////////////////////////////////////////////////////////////////
}
