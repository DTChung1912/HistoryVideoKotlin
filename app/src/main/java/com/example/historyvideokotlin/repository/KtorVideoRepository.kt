package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class KtorVideoRepository(val apiService: KtorAPIService) {
    suspend fun getVideoList(): List<Video> = coroutineScope {
        withContext(IO) {
            val response = apiService.getVideoList()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getVideoDetail(videoId: Int): List<Video> = coroutineScope {
        withContext(IO) {
            val response = apiService.getVideoDetail(videoId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getSearchVideo(keyword: String): List<Video> = coroutineScope {
        withContext(IO) {
            val response = apiService.getSearchVideo(keyword)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getNextVideo(videoId: Int): List<Video> = coroutineScope {
        withContext(IO) {
            val response = apiService.getNextVideo(videoId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getComment(videoId: Int): List<Comment> = coroutineScope {
        withContext(IO) {
            val response = apiService.getComment(videoId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getReply(commentId: Int): List<Reply> = coroutineScope {
        withContext(IO) {
            val response = apiService.getReply(commentId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun createVideo(video: Video): CreateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.createVideo(video)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                CreateResponse(false, "")
            }
        }
    }

    suspend fun createComment(comment: CommentRequest): CreateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.createComment(comment)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                CreateResponse(false, "")
            }
        }
    }

    suspend fun createReply(reply: ReplyRequest): CreateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.createReply(reply)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                CreateResponse(false, "")
            }
        }
    }

    // Update Video

    suspend fun updateVideo(video: Video): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideo(video)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoLike(videoId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideoLike(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateVideoLikeCancel(videoId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideoLikeCancel(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateVideoDislike(videoId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideoDislike(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateVideoDislikeCancel(videoId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideoDislikeCancel(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateVideoDownload(videoId: Int): UpdateResponse = coroutineScope {
        withContext(IO) {
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
        withContext(IO) {
            val response = apiService.updateVideoView(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateVideoComment(videoId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideoComment(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateVideoCommentCancel(videoId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateVideoCommentCancel(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    // Update Comment

    suspend fun updateCommentLike(commentId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateCommentLike(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateCommentLikeCancel(commentId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateCommentLikeCancel(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateCommentDislike(commentId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateCommentDislike(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateCommentDislikeCancel(commentId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateCommentDislikeCancel(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateCommentReply(commentId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateCommentReply(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateCommentReplyCancel(commentId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateCommentReplyCancel(commentId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    // Update Reply

    suspend fun updateReplyLike(replyId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateReplyLike(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateReplyLikeCancel(replyId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateReplyLikeCancel(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateReplyDislike(replyId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateReplyDislike(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    suspend fun updateReplyDislikeCancel(replyId: Int): CountResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateReplyDislikeCancel(replyId)
            val body = response.body()!!
            if (response.isSuccessful) {
                CountResponse(body.isSuccess, body.data)
            } else {
                CountResponse(false, -1)
            }
        }
    }

    // Delete

    suspend fun deleteVideo(videoId: Int): DeleteResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.deleteVideo(videoId)
            val body = response.body()!!
            if (response.isSuccessful) {
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "")
            }
        }
    }

    suspend fun deleteComment(replyId: Int): DeleteResponse = coroutineScope {
        withContext(IO) {
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
        withContext(IO) {
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
