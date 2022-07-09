package com.example.historyvideokotlin.base

import com.example.historyvideokotlin.model.User
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RESTClient {
    private val HOME_URL = "http://192.168.1.7:8083/HistoryVideo/"
    private val BASE_URL = "https://dtchung2k.000webhostapp.com/HistoryServer/"
    private val LOCAL_URL = "http://192.168.123.103:8083/HistoryVideo/"
    private val PIRAGO_URL = "http://192.168.123.103:8083/HistoryVideo/"


    private val apiService = Retrofit.Builder()
        .baseUrl(PIRAGO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(APIService::class.java)

    fun getClient() : APIService{
        return apiService
    }
}
