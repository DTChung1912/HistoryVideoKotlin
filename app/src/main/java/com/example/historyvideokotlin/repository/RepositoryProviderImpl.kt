package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.network.KtorAPIService

class RepositoryProviderImpl(
    private val apiService: KtorAPIService,
    override val ktorUserRepository: KtorUserRepository = KtorUserRepository(apiService),
    override val ktorPostRepository: KtorPostRepository = KtorPostRepository(apiService),
    override val ktorVideoRepository: KtorVideoRepository = KtorVideoRepository(apiService),
    override val ktorQuizRepository: KtorQuizRepository = KtorQuizRepository(apiService)
) : RepositoryProvider