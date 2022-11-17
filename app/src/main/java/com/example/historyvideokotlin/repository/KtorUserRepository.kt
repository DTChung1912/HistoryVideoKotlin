package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorUserRepository(val apiService: KtorAPIService) {

    suspend fun getUserList(): List<User> = coroutineScope {
        withContext(IO) {
            val response: Response<List<User>> = apiService.getUserList()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                listOf()
            }
        }
    }

    suspend fun getUser(userId: String): User = coroutineScope {
        withContext(IO) {
            val response: Response<User> = apiService.getUser(userId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                User("", "", "", "", "", "", "", "", 0, 2)
            }
        }
    }

    suspend fun register(user: User): List<User> = coroutineScope {
        withContext(IO) {
            val response = apiService.register(user)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updateUser(user: User): User = coroutineScope {
        withContext(IO) {
            val response = apiService.updateUser(user)
            val body = response.body()!!
            if (response.isSuccessful) {
                User(
                    user_id = body.user_id,
                    user_name = body.user_name,
                    user_image = body.user_image,
                    email = body.email,
                    birthday = body.birthday,
                    phone_number = body.phone_number,
                    address = body.address,
                    last_active = body.last_active,
                    account_type_id = body.account_type_id,
                    access_id = body.access_id
                )
            } else {
                user
            }
        }
    }

    suspend fun getMyVideoList(userId: String): List<MyVideoModel> = coroutineScope {
        withContext(IO) {
            val response = apiService.getMyVideoList(userId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
//                MyVideoRespone(listOf(), listOf(), "", 0)
                listOf()
            }
        }
    }

    suspend fun getMyVideoList(userId: String, type: Int): List<MyVideoModel> = coroutineScope {
        withContext(IO) {
            val response = apiService.getMyVideoListType(userId, type)
            if (response.isSuccessful) {
                response.body()!!
            } else {
//                MyVideoRespone(listOf(), listOf(), "", 0)
                listOf()
            }
        }
    }

    suspend fun getMyPostList(userId: String): MyPostResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.getMyPostList(userId, 1)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                MyPostResponse(listOf(), listOf(), "", 0)
            }
        }
    }

    suspend fun getMyVideo(userId: String, videoId: Int): MyVideoStatus = coroutineScope {
        withContext(IO) {
            val response = apiService.getMyVideo(userId, videoId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                MyVideoStatus()
            }
        }
    }

    suspend fun getMyPost(userId: String, postId: Int): List<MyPost> = coroutineScope {
        withContext(IO) {
            val response = apiService.getMyPost(userId, postId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getMyReplyList(userId: String, videoId: Int, commentId: Int): List<MyReply> =
        coroutineScope {
            withContext(IO) {
                val response = apiService.getMyReplyList(userId, videoId, commentId)
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    listOf()
                }
            }
        }

    suspend fun getMyCommentList(userId: String, videoId: Int): List<MyComment> = coroutineScope {
        withContext(IO) {
            val response = apiService.getMyCommentList(userId, videoId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                listOf()
            }
        }
    }

    suspend fun getMyComment(userId: String, videoId: Int, commentId: Int): MyComment =
        coroutineScope {
            withContext(IO) {
                val response = apiService.getMyComment(userId, videoId, commentId)
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    MyComment()
                }
            }
        }

    suspend fun getMyReply(userId: String, videoId: Int, commentId: Int, replyId: Int): MyReply =
        coroutineScope {
            withContext(IO) {
                val response = apiService.getMyReply(userId, videoId, commentId, replyId)
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    MyReply()
                }
            }
        }

    suspend fun postMyPost(myPost: MyPost): CreateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.postMyPost(myPost)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                CreateResponse(false, "")
            }
        }
    }

    suspend fun postMyVideo(myVideo: MyVideoStatus): CreateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.postMyVideo(myVideo)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                CreateResponse(false, "")
            }
        }
    }

    // Update My Post

    suspend fun updateMyPostRead(myPostId: Int, isRead: Int): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateMyPostRead(myPostId, isRead)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyPostDownload(myPostId: Int, isDownload: Int): UpdateResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.updateMyPostDownload(myPostId, isDownload)
                val body = response.body()!!
                if (response.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    suspend fun updateMyPostRate(myPostId: Int, isRate: Int): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateMyPostRate(myPostId, isRate)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    // Update MyVideo

    suspend fun updateMyVideoView(myVideoId: Int, isView: Int): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateMyVideoView(myVideoId, isView)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoLike(myVideoId: Int, isLike: Int): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateMyVideoLike(myVideoId, isLike)
            val body = response.body()
            body?.let {
                if (it.isSuccess) {
                    return@withContext UpdateResponse(it.isSuccess, it.data)
                } else {
                    return@withContext UpdateResponse(false, "")
                }
            }
            UpdateResponse(false, "")
        }
    }

    suspend fun updateMyVideoDownload(myVideoId: Int, isDownload: Int): UpdateResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.updateMyVideoDownload(myVideoId, isDownload)
                val body = response.body()!!
                if (response.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    suspend fun updateMyVideoLater(myVideoId: Int, isLater: Int): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response = apiService.updateMyVideoLater(myVideoId, isLater)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoDontCare(myVideoId: Int, isDontCare: Int): UpdateResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.updateMyVideoDontCare(myVideoId, isDontCare)
                val body = response.body()!!
                if (response.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    suspend fun updateMyVideoViewTime(myVideoId: Int, viewTime: Int): UpdateResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.updateMyVideoViewTime(myVideoId, viewTime)
                val body = response.body()!!
                if (response.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    // Update My Comment

    suspend fun updateMyCommentLike(
        userId: String,
        videoId: Int,
        commentId: Int,
        isLike: Int
    ): UpdateResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.updateMyCommentLike(userId, videoId, commentId, isLike)
                val body = response.body()
                body?.let {
                    if (it.isSuccess) {
                        return@withContext UpdateResponse(it.isSuccess, it.data)
                    } else {
                        return@withContext UpdateResponse(false, "")
                    }
                }
                UpdateResponse(false, "")
            }
        }

    // Update My Reply

    suspend fun updateMyReplyLike(
        userId: String,
        videoId: Int,
        commentId: Int,
        replyId: Int,
        isLike: Int
    ): UpdateResponse =
        coroutineScope {
            withContext(IO) {
                val response =
                    apiService.updateMyReplyLike(userId, videoId, commentId, replyId, isLike)
                val body = response.body()
                body?.let {
                    if (it.isSuccess) {
                        return@withContext UpdateResponse(it.isSuccess, it.data)
                    } else {
                        return@withContext UpdateResponse(false, "")
                    }
                }
                UpdateResponse(false, "")
            }
        }

    // Delete

    suspend fun deleteUser(userId: String): DeleteResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.deleteUser(userId)
                val body = response.body()?: DeleteResponse(false, "null")
                if (response.isSuccessful) {
                    DeleteResponse(body.isSuccess, body.data)
                } else {
                    DeleteResponse(false, "")
                }
            }
        }

    suspend fun deleteMyPost(myPostId: Int): DeleteResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.deleteMyPost(myPostId)
                val body = response.body() ?: DeleteResponse(false, "null")
                if (response.isSuccessful) {
                    DeleteResponse(body.isSuccess, body.data)
                } else {
                    DeleteResponse(false, "")
                }
            }
        }

    suspend fun deleteMyVideo(myVideoId: Int): DeleteResponse =
        coroutineScope {
            withContext(IO) {
                val response = apiService.deleteMyVideo(myVideoId)
                val body = response.body()!!
                if (response.isSuccessful) {
                    DeleteResponse(body.isSuccess, body.data)
                } else {
                    DeleteResponse(false, "")
                }
            }
        }
}
