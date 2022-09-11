package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.MyPost
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.UpdateResponse
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.network.KtorAPIService
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorUserRepository(val apiService: KtorAPIService) {

    suspend fun getUser(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse: Response<List<User>> = apiService.getUser(userId)
            if (reponse.isSuccessful) {
                val body = reponse.body()
                body?.let {
                    MyLog.e("chungnew", it[0].user_id)
                }
            }
        }
    }

    suspend fun register(user: User): List<User> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.register(user)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updateUser(user: User): User = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateUser(user)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
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

    suspend fun getMyVideoList(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<List<MyVideo>> = apiService.getMyVideoList(userId)
        }
    }

    suspend fun getMyPostList(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<List<MyPost>> = apiService.getMyPostList(userId)
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

    suspend fun getMyPost(userId: String, postId: Int): List<MyPost> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getMyPost(userId, postId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updateMyPostRead(myPostId: Int, isRead: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateMyPostRead(myPostId, isRead)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyPostDownload(myPostId: Int, isDownload: Int): UpdateResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
                val reponse = apiService.updateMyPostDownload(myPostId, isDownload)
                val body = reponse.body()!!
                if (reponse.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    suspend fun updateMyPostRate(myPostId: Int, isRate: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateMyPostRate(myPostId, isRate)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoView(myVideoId: Int, isView: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateMyVideoView(myVideoId, isView)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoLike(myVideoId: Int, isLike: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateMyVideoLike(myVideoId, isLike)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoDownload(myVideoId: Int, isDownload: Int): UpdateResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
                val reponse = apiService.updateMyVideoDownload(myVideoId, isDownload)
                val body = reponse.body()!!
                if (reponse.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    suspend fun updateMyVideoLater(myVideoId: Int, isLater: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateMyVideoLater(myVideoId, isLater)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "")
            }
        }
    }

    suspend fun updateMyVideoDontCare(myVideoId: Int, isDontCare: Int): UpdateResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
                val reponse = apiService.updateMyVideoDontCare(myVideoId, isDontCare)
                val body = reponse.body()!!
                if (reponse.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }

    suspend fun updateMyVideoViewTime(myVideoId: Int, viewTime: Int): UpdateResponse =
        coroutineScope {
            withContext(Dispatchers.IO) {
                val reponse = apiService.updateMyVideoViewTime(myVideoId, viewTime)
                val body = reponse.body()!!
                if (reponse.isSuccessful) {
                    UpdateResponse(body.isSuccess, body.data)
                } else {
                    UpdateResponse(false, "")
                }
            }
        }
}