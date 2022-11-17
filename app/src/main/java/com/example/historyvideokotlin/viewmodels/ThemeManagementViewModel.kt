package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ThemeManagementViewModel(application: Application) : BaseViewModel(application) {
    val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    var themeList = MutableLiveData<List<Theme>>()

    fun getTheme() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorQuizRepository.getTheme()
            }.onSuccess {
                hideLoading()
                themeList.value = it
            }.onFailure {
                MyLog.e("getTheme", it.message)
                hideLoading()
            }
        }
    }

    fun deleteTheme(themeId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorQuizRepository.deleteTheme(themeId))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("deleteTheme", it.data.toString())
                }
            }
        }
    }
}
