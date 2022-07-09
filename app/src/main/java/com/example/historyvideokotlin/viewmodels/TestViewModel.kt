package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.base.BaseViewModel

class TestViewModel(application: Application) : BaseViewModel(application) {
    var test = MutableLiveData("")
}