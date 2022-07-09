package com.example.historyvideokotlin.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import java.util.*

abstract class BaseActivity<V : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {
    private var binding: B? = null
    private var viewModel: V? = null
    private lateinit var handler: Handler

    protected abstract fun getLayoutId(): Int

    protected abstract fun getViewModelClass(): Class<V>

    protected abstract fun onAppEvent(event: AppEvent<String, Objects>)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        initData()

        window.navigationBarColor = resources.getColor(R.color.black)
        viewModel!!.getViewEventLiveData().observe(this) { event ->
            if (event == null) {
                return@observe
            }
            onAppEvent(event)
        }
        viewModel!!.getLoadingLiveData().observe(this) { loading ->
            showLoading(loading != null && loading)
        }
    }

    fun initData() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = ViewModelProvider(this).get(getViewModelClass())
    }

    protected fun showLoading(isLoading: Boolean) {}

    fun getBinding(): B? {
        return binding
    }

    fun getViewModel(): V? {
        return viewModel
    }

    fun replaceFragment(id: Int, fragment: Fragment, addToBackStack: Boolean) {
        handler.post {
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            transaction.replace(id, fragment, fragment.tag)
            if (addToBackStack) {
                transaction.addToBackStack(fragment.tag)
            }
            transaction.commitAllowingStateLoss()
        }
    }

    open fun replaceFragment(
        id: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        tag: String?
    ) {
        handler.post {
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            transaction.replace(id, fragment!!, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()
        }
    }

    open fun showFragment(
        id: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        tag: String?
    ) {
        handler.post {
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            transaction.add(id, fragment!!, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()
        }
    }

    protected open fun showFragmentWithoutAnimation(
        id: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        tag: String?
    ) {
        handler.post {
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.add(id, fragment!!, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()
        }
    }

    fun setScreenNoLimit() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray_dark)
    }

    open fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}

