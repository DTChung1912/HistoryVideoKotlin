package com.example.historyvideokotlin.repository

interface RepositoryProvider {
    val ktorUserRepository: KtorUserRepository
    val ktorPostRepository: KtorPostRepository
    val ktorVideoRepository: KtorVideoRepository
    val ktorQuizRepository: KtorQuizRepository
}
