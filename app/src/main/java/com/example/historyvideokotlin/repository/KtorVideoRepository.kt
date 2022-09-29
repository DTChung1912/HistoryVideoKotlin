package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class KtorVideoRepository(val apiService: KtorAPIService) {
    suspend fun getVideoList(): List<Video> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getVideoList()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getVideoDetail(videoId: Int): List<Video> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getVideoDetail(videoId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getSearchVideo(keyword: String): List<Video> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getSearchVideo(keyword)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getComment(videoId: Int): List<Comment> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getComment(videoId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getReply(commentId: Int): List<Reply> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getReply(commentId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun postComment(comment: Comment): Comment = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.postComment(comment)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                Comment(0, 0, "", "", "", "", "", "", "", "")
            }
        }
    }

    suspend fun postReply(reply: Reply): Reply = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.postReply(reply)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                Reply(0, 0, "", "", "", "", "", "", "", "")
            }
        }
    }

    // Update Video

    suspend fun updateVideoLike(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoLike(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoLikeCancel(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoLikeCancel(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoDislike(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoDislike(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoDislikeCancel(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoDislikeCancel(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoDownload(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoDownload(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoView(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoView(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoComment(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateVideoComment(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Update Comment

    suspend fun updateCommentLike(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateCommentLike(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentLikeCancel(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateCommentLikeCancel(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentDislike(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateCommentDislike(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentDislikeCancel(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateCommentDislikeCancel(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentReply(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateCommentReply(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Update Reply

    suspend fun updateReplyLike(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateReplyLike(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateReplyLikeCancel(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateReplyLikeCancel(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateReplyDislike(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateReplyDislike(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateReplyDislikeCancel(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.updateReplyDislikeCancel(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Delete

    suspend fun deleteComment(replyId: Int): DeleteResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.deleteComment(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "")
            }
        }
    }

    suspend fun deleteReply(replyId: Int): DeleteResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.deleteReply(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "")
            }
        }
    }
}