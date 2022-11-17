package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.data.HistoryDatabase
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Keyword
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.repository.KeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SearchVideoViewModel(application: Application) : BaseViewModel(application) {
    private val _isSearch = MutableLiveData(true)
    val isSearch: LiveData<Boolean> get() = _isSearch

    private val _keyword = MutableLiveData<String>()
    val keyword: LiveData<String> get() = _keyword

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository
    private val keywordRepository: KeywordRepository

    var videoList = MutableLiveData<List<Video>>()
    val keywordList: LiveData<List<Keyword>>

    init {
        val database = HistoryDatabase.getDatabase(application)
        keywordRepository = KeywordRepository(database.keywordDao())
        keywordList = keywordRepository.videoKeyword
    }

    fun insertKeyword(keyword: Keyword) {
        viewModelScope.launch(IO) {
            keywordRepository.insertKeyword(keyword)
        }
    }

    fun deleteKeyword(keyword: Keyword) {
        viewModelScope.launch(IO) {
            keywordRepository.deleteKeyword(keyword)
        }
    }

    fun setSearchVisible(isVisible: Boolean) {
        _isSearch.value = isVisible
    }

    fun setKeyword(keyword: String) {
        _keyword.value = keyword
        getSeacrhVideo(keyword)
        setSearchVisible(false)
    }

    private fun getSeacrhVideo(keyword: String) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorVideoRepository.getSearchVideo(keyword)
            }.onSuccess {
                hideLoading()
                videoList.value = it
            }.onFailure {
                hideLoading()
            }
        }
    }

}