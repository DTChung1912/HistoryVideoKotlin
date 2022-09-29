package com.example.historyvideokotlin.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RESTClient {
    private val HOME_URL = "http://192.168.1.13:8083/HistoryVideo/"
    private val BASE_URL = "https://dtchung2k.000webhostapp.com/HistoryServer/"
    private val PIRAGO_GORAKU_URL = "http://172.17.0.47:8083/historyvideo/"
    private val T2_URL = "http://192.168.43.227:8083/HistoryVideo/"

    val HOME_KTOR_URL = "http://192.168.1.13:8080/"
    val PIRAGO_GORAKU_KTOR_URL = "http://172.17.0.47:8080/"
    val T2_KTOR_URL = "http://192.168.43.227:8080/"


//    var gson = GsonBuilder()
//        .setLenient()
//        .create()

    private val apiService = Retrofit.Builder()
        .baseUrl(T2_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(APIService::class.java)

    fun getClient(): APIService {
        return apiService
    }
}
