package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    private lateinit var isShowBottomMenu: MutableLiveData<Boolean>

    fun getIsShowBottomMenu(): MutableLiveData<Boolean> {
        return isShowBottomMenu
    }


}