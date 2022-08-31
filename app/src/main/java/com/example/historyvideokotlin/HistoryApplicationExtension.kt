package com.example.historyvideokotlin

import com.example.historyvideokotlin.network.APIServiceBuilder
import com.example.historyvideokotlin.repository.RepositoryProvider
import com.example.historyvideokotlin.repository.RepositoryProviderImpl

class HistoryApplicationExtension(val app : HistoryApplication)
    : RepositoryProvider by RepositoryProviderImpl(APIServiceBuilder().build())