package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.os.UserManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PostViewModel(application: Application) : BaseViewModel(application) {

    var disposable2 = CompositeDisposable()

    fun getPostPerson() {
    }

    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_post_tab_person)
            add(R.string.title_post_tab_events)
            add(R.string.title_post_tab_places)
            add(R.string.title_post_tab_timeline)
            add(R.string.title_post_tab_country)
        }
        return titleIds
    }
}