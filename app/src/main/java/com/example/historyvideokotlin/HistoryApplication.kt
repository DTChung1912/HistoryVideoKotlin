package com.example.historyvideokotlin

import android.app.Application
import com.example.historyvideokotlin.di.DependencyProvider
import com.example.historyvideokotlin.repository.RepositoryProvider

class HistoryApplication : Application(), DependencyProvider {
    private var historyApplicationExtension: HistoryApplicationExtension? = null
    override fun onCreate() {
        super.onCreate()
        historyApplicationExtension = HistoryApplicationExtension(this)
    }

    override val repositoryProvider: RepositoryProvider
        get() = historyApplicationExtension!!
}