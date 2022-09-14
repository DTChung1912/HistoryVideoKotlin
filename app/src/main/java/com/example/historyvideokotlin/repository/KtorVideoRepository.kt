package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class KtorVideoRepository(val apiService: KtorAPIService) {
    suspend fun getVideo(): List<Video> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getVideo()
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getSearchVideo(keyword: String): List<Video> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getSearchVideo(keyword)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getComment(videoId: Int): List<Comment> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getComment(videoId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getReply(commentId: Int): List<Reply> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getReply(commentId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun postComment(comment: Comment): Comment = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.postComment(comment)
            if (reponse.isSuccessful) {
                reponse.body()!!
            } else {
                Comment(0, 0, "", "", "", "", "", "", "", "")
            }
        }
    }

    suspend fun postReply(reply: Reply): Reply = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.postReply(reply)
            if (reponse.isSuccessful) {
                reponse.body()!!
            } else {
                Reply(0, 0, "", "", "", "", "", "", "", "")
            }
        }
    }

    suspend fun getMyVideo(userId: String, videoId: Int): List<MyVideo> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getMyVideo(userId, videoId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    // Update Video

    suspend fun updateVideoLike(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoLike(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoLikeCancel(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoLikeCancel(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoDislike(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoDislike(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoDislikeCancel(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoDislikeCancel(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoDownload(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoDownload(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoView(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoView(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoComment(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoComment(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Update Comment

    suspend fun updateCommentLike(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateCommentLike(commentId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentLikeCancel(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateCommentLikeCancel(commentId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentDislike(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateCommentDislike(commentId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentDislikeCancel(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateCommentDislikeCancel(commentId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateCommentReply(commentId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateCommentReply(commentId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Update Reply

    suspend fun updateReplyLike(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateReplyLike(replyId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateReplyLikeCancel(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateReplyLikeCancel(replyId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateReplyDislike(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateReplyDislike(replyId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateReplyDislikeCancel(replyId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateReplyDislikeCancel(replyId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Delete

    suspend fun deleteComment(replyId: Int): DeleteResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.deleteComment(replyId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "")
            }
        }
    }

    suspend fun deleteReply(replyId: Int): DeleteResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.deleteReply(replyId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "")
            }
        }
    }
}