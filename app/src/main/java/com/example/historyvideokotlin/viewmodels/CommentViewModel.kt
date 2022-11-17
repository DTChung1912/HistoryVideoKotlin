package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.CommentRequest
import com.example.historyvideokotlin.model.MyComment
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : BaseViewModel(application) {

    var commentList = MutableLiveData<List<Comment>>()
    private var comments = mutableListOf<Comment>()
    val ktorUserRepository = application.repositoryProvider.ktorUserRepository
    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    private val userId = HistoryUserManager.instance.UserId()

    private val _commentCount = MutableLiveData(0)
    val commentCount: LiveData<Int> get() = _commentCount

    val isLikeList = MutableLiveData<List<Int>>()
    private val isLikes = mutableListOf<Int>()

    val myCommentList = MutableLiveData<List<MyComment>>()
    private val myComments = mutableListOf<MyComment>()

    val isPostComment = MutableLiveData(false)

    init {
        comments.clear()
        myComments.clear()
    }

    fun getCommentData(videoId: Int) {
        getComment(videoId)
    }

    private fun getIsLikeList() {
        for (i in 0 until comments.size) {
            isLikes.add(0)
            for (j in myComments) {
                if (j.comment_id!!.equals(comments[i].comment_id)) {
                    isLikes.set(i, j.isLike!!)
                    break
                }
            }
        }
        isLikeList.value = isLikes
        MyLog.e("isLikeList", isLikes.size.toString() + " " + isLikeList.toString())
    }

    private fun getComment(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorVideoRepository.getComment(videoId)
            }.onSuccess {
                commentList.value = it
                comments.addAll(it)
                _commentCount.value = it.size
                MyLog.e("commentList", it.toString())
                hideLoading()
            }.onFailure {
                hideLoading()
            }
        }
    }

    fun getMyCommentList(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getMyCommentList(userId, videoId)
            }.onSuccess {
                myCommentList.value = it
                myComments.addAll(it)
                MyLog.e("myComments", it.toString())
                getIsLikeList()
                hideLoading()
            }.onFailure {
                MyLog.e("myComments", it.toString())
                hideLoading()
            }
        }
    }

    fun createComment(comment: CommentRequest, videoId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.createComment(comment))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateMyCommentLike", it.data.toString())
                    if (it.isSuccess) {
                        isPostComment.value = true
                    }
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
                    MyLog.e("updateCommentDislikeCancel", it.data.toString())
                }
            }
        }
    }

    fun updateCommentReply(commentId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateCommentReply(commentId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateCommentReply", it.data.toString())
                }
            }
        }
    }

    fun updateCommentReplyCancel(commentId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateCommentReplyCancel(commentId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateCommentReplyCancel", it.data.toString())
                }
            }
        }
    }
}
