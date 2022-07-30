package com.example.historyvideokotlin.base

import com.example.historyvideokotlin.network.APIService
import com.example.historyvideokotlin.network.RESTClient

abstract class BaseRepository {

    internal var apiService : APIService? = null

    init {
        if (apiService == null) {
            apiService = RESTClient().getClient()
        }
    }

}