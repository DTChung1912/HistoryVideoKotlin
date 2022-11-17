package com.example.historyvideokotlin.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIServiceBuilder() {
    fun build(): KtorAPIService {
        val okHttpClientProvider = OkHttpClientProvider()
        var okHttpClient: OkHttpClient = buildOkHttp(okHttpClientProvider)
        okHttpClientProvider.okHttpClient = okHttpClient
        return Retrofit.Builder().baseUrl(RESTClient().KTOR_API_URL)
            .client(buildOkHttp(okHttpClientProvider).also { okHttpClient = it })
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(KtorAPIService::class.java)
    }

    private fun buildOkHttp(okHttpClientProvider: OkHttpClientProvider): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(contentTypeInterceptor)
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(logging)
        return httpClientBuilder.build()
    }

    private val contentTypeInterceptor = Interceptor { chain: Interceptor.Chain ->
        val request = chain.request().newBuilder().addHeader(CONTENT_TYPE, APPLICATION_JSON).build()
        chain.proceed(request)
    }

    companion object {
        private const val READ_TIME_OUT = 30L
        private const val WRITE_TIME_OUT = 30L
        private const val CONNECT_TIME_OUT = 30L
        private const val CONTENT_TYPE = "Content-Type"
        private const val APPLICATION_JSON = "application/json"
    }
}
