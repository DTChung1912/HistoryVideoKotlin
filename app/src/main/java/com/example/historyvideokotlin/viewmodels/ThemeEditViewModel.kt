package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
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

class ThemeEditViewModel(application: Application) : BaseViewModel(application) {
    private val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    private val _isUpdate = MutableLiveData(false)
    val isUpdate: LiveData<Boolean> get() = _isUpdate

    private val _theme = MutableLiveData<Theme>()
    val theme: LiveData<Theme> get() = _theme

    val isUpdateComplete = MutableLiveData<Boolean>()
    val isAddComplete = MutableLiveData<Boolean>()

    fun setTheme(theme: Theme) {
        _theme.value = theme
        _isUpdate.value = if (theme.theme_id == 0) false else true
    }

    fun createTheme(theme: Theme) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorQuizRepository.createTheme(theme))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    isAddComplete.value = it.isSuccess
                    MyLog.e("createTheme", it.data.toString())
                }
            }
        }
    }

    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorQuizRepository.updateTheme(theme))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    isUpdateComplete.value = it.isSuccess
                    MyLog.e("updateTheme", it.data.toString())
                }
            }
        }
    }
}
