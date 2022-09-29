package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorUserRepository(val apiService: KtorAPIService) {

    suspend fun getUser(userId: String): User = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<User> = apiService.getUser(userId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                User("", "", "", "", "", "", "", "", 0)
            }
        }
    }

    suspend fun register(user: User): List<User> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.register(user)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updateUser(user: User): User = coroutineScope {
        withContext(Dispatchers.IO) {
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
                    account_type_id = body.account_type_id
                )
            } else {
                user
            }
        }
    }

    suspend fun getMyVideoList(userId: String): MyVideoRespone = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getMyVideoList(userId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                MyVideoRespone(listOf(), listOf(), "", 0)
            }
        }
    }

    suspend fun getMyVideoList(userId: String, type: Int): MyVideoRespone = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getMyVideoListType(userId, type)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                MyVideoRespone(listOf(), listOf(), "", 0)
            }
        }
    }

    suspend fun getMyPostList(userId: String): MyPostResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getMyPostList(userId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                MyPostResponse(listOf(), listOf(), "", 0)
            }
        }
    }

    suspend fun getMyVideo(userId: String, videoId: Int): List<MyVideoStatus> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getMyVideo(userId, videoId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                listOf()
            }
        }
    }

    suspend fun getMyPost(userId: String, postId: Int): List<MyPost> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getMyPost(userId, postId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun postMyPost(myPost: MyPost): CreateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.postMyPost(myPost)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                CreateResponse(false, "")
            }
        }
    }

    suspend fun postMyVideo(myVideo: MyVideoStatus): CreateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
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
        withContext(Dispatchers.IO) {
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
            withContext(Dispatchers.IO) {
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
        withContext(Dispatchers.IO) {
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
        withContext(Dispatchers.IO) {
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
        withContext(Dispatchers.IO) {
            val response = apiService.updateMyVideoLike(myVideoId, isLike)
            val body = response.body()!!
            if (response.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoDownload(myVideoId: Int, isDownload: Int): UpdateResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
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
        withContext(Dispatchers.IO) {
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
            withContext(Dispatchers.IO) {
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
            withContext(Dispatchers.IO) {
                val response = apiService.updateMyVideoViewTime(myVideoId, viewTime)
                val body = response.body()!!
                if (response.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    // Delete

    suspend fun deleteMyPost(myPostId: Int): DeleteResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
                val response = apiService.deleteMyPost(myPostId)
                val body = response.body()!!
                if (response.isSuccessful) {
                    DeleteResponse(body.isSuccess, body.data)
                } else {
                    DeleteResponse(false, "")
                }
            }
        }

    suspend fun deleteMyVideo(myVideoId: Int): DeleteResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
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
