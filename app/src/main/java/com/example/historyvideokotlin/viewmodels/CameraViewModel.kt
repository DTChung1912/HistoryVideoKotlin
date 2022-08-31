package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.base.BaseViewModel

class CameraViewModel(application: Application) : BaseViewModel(application) {

    private val isEnableTorch = MutableLiveData(false)

    fun getLiveDataIsEnableTorch(): MutableLiveData<Boolean?>? {
        return isEnableTorch
    }

    fun getIsEnableTorch(): Boolean {
        return isEnableTorch.value!!
    }

    fun setIsEnableTorch() {
        if (isEnableTorch.value != null) {
            val isEnable = isEnableTorch.value!!
            isEnableTorch.value = !isEnable
        }
    }

    fun disableTorch() {
        isEnableTorch.value = false
    }
}