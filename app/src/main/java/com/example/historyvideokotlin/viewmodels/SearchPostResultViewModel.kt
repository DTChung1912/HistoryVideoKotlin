package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Post
import kotlinx.coroutines.launch

class SearchPostResultViewModel(application: Application) : BaseViewModel(application) {
    val ktorPostRepository = application.repositoryProvider.ktorPostRepository

    var postList = MutableLiveData<List<Post>>()

    fun getSeacrhPost(keyword: String) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorPostRepository.getSearchPost(keyword)
            }.onSuccess {
                hideLoading()
                postList.value = it
            }.onFailure {
                hideLoading()
            }
        }
    }

}