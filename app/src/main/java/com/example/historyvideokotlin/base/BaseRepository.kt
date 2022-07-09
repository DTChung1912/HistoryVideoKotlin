package com.example.historyvideokotlin.base

abstract class BaseRepository {

    internal var apiService : APIService? = null

    init {
        if (apiService == null) {
            apiService = RESTClient().getClient()
        }
    }

}