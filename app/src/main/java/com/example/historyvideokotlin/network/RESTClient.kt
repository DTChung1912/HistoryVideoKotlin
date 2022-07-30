package com.example.historyvideokotlin.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RESTClient {
    private val HOME_URL = "http://192.168.1.3:8083/HistoryVideo/"
    private val BASE_URL = "https://dtchung2k.000webhostapp.com/HistoryServer/"
    private val T3_URL = "http://192.168.123.101:8083/HistoryVideo/"
    private val PIRAGO_GORAKU_URL="http://172.17.0.14:8083/historyvideo/"

    private val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(APIService::class.java)

    fun getClient() : APIService {
        return apiService
    }
}
