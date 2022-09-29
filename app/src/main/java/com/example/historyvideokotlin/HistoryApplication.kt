package com.example.historyvideokotlin

import android.app.Application
import com.example.historyvideokotlin.di.DependencyProvider
import com.example.historyvideokotlin.repository.RepositoryProvider

class HistoryApplication :
    Application(),
    DependencyProvider {
    private val historyApplicationExtension by lazy { HistoryApplicationExtension(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        historyApplicationExtension = HistoryApplicationExtension(this)
    }

    override val repositoryProvider: RepositoryProvider
        get() = historyApplicationExtension

    companion object {
        lateinit var instance : HistoryApplication
    }
}