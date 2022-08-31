package com.example.historyvideokotlin.utils

import android.util.Log

object MyLog {
    fun e(tag : String , message: String?) {
        Log.e(
            tag,
            getMetaInfo() + null2str(message)
        )

    }

    private fun getMetaInfo(): String? {
        return try {
            val element4 = Thread.currentThread().stackTrace[4]
            getMetaInfo(element4)
        } catch (e: Exception) {
            ""
        }
    }

    fun getMetaInfo(element: StackTraceElement): String? {
        val fullClassName = element.className
        val simpleClassName =
            fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
        val methodName = element.methodName
        val lineNumber = element.lineNumber
        return "[$simpleClassName#$methodName:$lineNumber]"
    }

    private fun null2str(string: String?): String? {
        return string ?: "(null)"
    }
}