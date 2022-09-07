package com.example.historyvideokotlin.base

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.ui.FragmentNavigation
import com.example.historyvideokotlin.ui.ProgressBarDialog
import com.example.historyvideokotlin.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ncapdevi.fragnav.FragNavTransactionOptions
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class BaseFragment<V : BaseViewModel, B : ViewDataBinding> : Fragment() {
    internal lateinit var binding: B
    internal lateinit var viewModel: V
    private lateinit var mainViewModel: MainViewModel
    private lateinit var fragmentNavigation: FragmentNavigation
    private lateinit var progressBarDialog: ProgressBarDialog
    private var dialogs: ConcurrentHashMap<String, Dialog> = ConcurrentHashMap()

    val auth = Firebase.auth
    val user: FirebaseUser? = auth.getCurrentUser()

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getViewModel(): V

    protected fun getBinding(): B {
        return binding
    }

    protected abstract fun getAnalyticsScreenName(): String?

    protected abstract fun initData()

    protected abstract fun onAppEvent(event: AppEvent<String, Objects>)

    protected open fun getMainViewModel(): MainViewModel? {
        if (activity !is MainActivity) {
            throw RuntimeException("MainViewModel can only use in MainActivity")
        }
        return mainViewModel
    }

    fun checkUserLogined() : Boolean {
        if(user != null) {
            return true
        } else {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarDialog = ProgressBarDialog(requireContext())
        viewModel = getViewModel()
//        if (activity !is MainActivity) {
//            mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//        }
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.getViewEventLiveData().observe(viewLifecycleOwner) { event ->
            if (event == null) {
                return@observe
            }
            onAppEvent(event)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { loading ->
            showLoading(loading != null && loading)
        }
        initData()
    }

    override fun onStop() {
        super.onStop()
        if (progressBarDialog != null && progressBarDialog.isShowing) {
            progressBarDialog.dismiss()
        }
        val keyList = ArrayList<String>()
        for (key in dialogs.keys) {
            val dialog = dialogs.get(key)
            if (dialog != null && dialog.isShowing) {
                dialog.dismiss()
                keyList.add(key)
            }
        }
        for (key in keyList) {
            dialogs.remove(key)
        }
    }


    protected fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarDialog.show()
        } else {
            progressBarDialog.dismiss()
        }
    }

    fun onBackPresses(): Boolean {
        return false
    }

    protected open fun clearBackStack() {
        if (fragmentNavigation != null) {
            fragmentNavigation.clearBackStack()
        }
    }

    protected fun showDialogFragment(fragment: DialogFragment) {
        if (fragmentNavigation != null) {
            fragmentNavigation.showDialogFragment(fragment)
        }
    }

    protected open fun pushFragment(fragment: Fragment, options: FragNavTransactionOptions) {
        if (fragmentNavigation != null) {
            fragmentNavigation.pushFragment(fragment, options)
        }
    }

    protected open fun popFragment(options: FragNavTransactionOptions) {
        popFragments(1, options)
    }

    protected open fun popFragments(depth: Int, options: FragNavTransactionOptions) {
        if (fragmentNavigation != null) {
            if (options != null) {
                fragmentNavigation.popFragment(depth, options)
            }
        }
    }

    protected open fun replaceFragment(id: Int, fragment: Fragment?, addToBackStack: Boolean) {
        val activity: Activity? = activity
        if (activity is BaseActivity<*, *> && !activity.isFinishing()) {
            activity.replaceFragment(id, fragment!!, addToBackStack)
        }
    }

    protected open fun replaceFragment(
        id: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        tag: String?
    ) {
        val activity: Activity? = activity
        if (activity is BaseActivity<*, *> && !activity.isFinishing()) {
            activity.replaceFragment(id, fragment, addToBackStack, tag)
        }
    }

    protected open fun showFragment(
        id: Int,
        fragment: Fragment?,
        addToBackStack: Boolean,
        tag: String?
    ) {
        val activity: Activity? = activity
        if (activity is BaseActivity<*, *> && !activity.isFinishing()) {
            activity.showFragment(id, fragment, addToBackStack, tag)
        }
    }


    protected open fun showBottomMenu(isShow: Boolean) {
        if (mainViewModel != null) {
            mainViewModel.getIsShowBottomMenu().postValue(isShow)
        }
    }

    protected open fun showMessage(
        title: String?,
        message: String?,
        buttonText: Int,
        clickListener: DialogInterface.OnClickListener?
    ) {
        val old = dialogs[message]
        if (old != null && old.isShowing) {
            old.dismiss()
            dialogs.remove(message)
        }
        if (activity != null) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title)
            }
            val dialog: AlertDialog = builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonText, clickListener)
                .create()
            dialog.setOnDismissListener { dialog1: DialogInterface? ->
                dialogs.remove(
                    message
                )
            }
            dialogs[message!!] = dialog
            dialog.show()
        }
    }

    protected open fun nextFragment(fragment: Fragment) {
        findNavController().navigate(fragment!!.id)
    }

}