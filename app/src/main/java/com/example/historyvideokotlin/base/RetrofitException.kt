package com.example.historyvideokotlin.base

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.RuntimeException
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitException internal constructor(
    message: String?,

    val url: String?,

    val response: Response<*>?,

    val kind: Kind, exception: Throwable?,

    val retrofit: Retrofit?
) : RuntimeException(message, exception) {

    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>?): T? {
        if (response == null || response.errorBody() == null) {
            return null
        }
        val converter: Converter<ResponseBody?, T> =
            retrofit!!.responseBodyConverter(type, arrayOfNulls(0))
        return converter.convert(response.errorBody())
    }


    fun <T> getErrorBodyAsOrNull(type: Class<T>?): T? {
        return try {
            getErrorBodyAs(type)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    enum class Kind {

        NETWORK,

        HTTP,

        UNEXPECTED
    }

    companion object {
        @JvmStatic
        fun httpError(url: String?, response: Response<*>, retrofit: Retrofit?): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
        }

        @JvmStatic
        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(
                exception.message, null, null, Kind.NETWORK, exception,
                null
            )
        }

        @JvmStatic
        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(
                exception.message, null, null, Kind.UNEXPECTED, exception,
                null
            )
        }
    }
}