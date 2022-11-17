package com.example.historyvideokotlin.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RESTClient {
    private val HOME_HOST = "http://192.168.1.13:"
    private val PIRAGO_GORAKU_HOST = "http://172.17.0.73:"
    private val T2_HOST = "http://192.168.0.102:"
    private val CHUNG_HOST = "http://192.168.43.227:"
    private val PHP_SERVER = "8083/HistoryVideo/"
    private val KTOR_SERVER = "8080/"

    val KTOR_API_URL = CHUNG_HOST + KTOR_SERVER
    private val PHP_API_URL = CHUNG_HOST + PHP_SERVER

//    var gson = GsonBuilder()
//        .setLenient()
//        .create()

    private val apiService = Retrofit.Builder()
        .baseUrl(PHP_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(APIService::class.java)

    fun getClient(): APIService {
        return apiService
    }
}
