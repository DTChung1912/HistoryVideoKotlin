package com.example.historyvideokotlin.di

import com.example.historyvideokotlin.repository.RepositoryProvider

interface DependencyProvider {
    val repositoryProvider: RepositoryProvider
}