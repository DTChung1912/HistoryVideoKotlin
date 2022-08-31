package com.example.historyvideokotlin.di

import android.app.Application
import com.example.historyvideokotlin.repository.RepositoryProvider

val Application.repositoryProvider: RepositoryProvider
    get() = (this as DependencyProvider).repositoryProvider