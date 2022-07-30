package com.example.historyvideokotlin.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseActivity
import com.example.historyvideokotlin.databinding.ActivityMainBinding
import com.example.historyvideokotlin.fragments.MyPageFragment
import com.example.historyvideokotlin.fragments.PostFragment
import com.example.historyvideokotlin.fragments.QuizFragment
import com.example.historyvideokotlin.fragments.VideoFragment
import com.example.historyvideokotlin.ui.FragmentNavigation
import com.example.historyvideokotlin.ui.ProgressBarDialog
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import com.example.historyvideokotlin.viewmodels.MainViewModel
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.FragNavTransactionOptions.Builder
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import com.zing.zalo.zalosdk.oauth.ZaloSDK
import org.json.JSONObject
import java.util.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    FragNavController.TransactionListener, FragmentNavigation, View.OnClickListener {

    val TAB_POST = FragNavController.TAB1
    val TAB_VIDEO = FragNavController.TAB2
    val TAB_QUIZ = FragNavController.TAB3
    val TAB_MY_PAGE = FragNavController.TAB4

    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var controller: FragNavController

    private lateinit var currentDialogFragment: DialogFragment
    private lateinit var progressBarDialog : ProgressBarDialog
    private lateinit var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setScreenNoLimit()
        setupBottomNavigationView()
        setUpToolbar()
        openDrawer()
        init()

        var accessToken = AccessToken.getCurrentAccessToken()
        var request: GraphRequest = GraphRequest.newMeRequest(accessToken, object :
            GraphRequest.GraphJSONObjectCallback {
            override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {

            }
        })
    }

    private fun init() {
        progressBarDialog = ProgressBarDialog(this)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.getLoadingLiveData().observe(this) { show ->
            if (show) {
                progressBarDialog.show()
            } else {
                progressBarDialog.dismiss()
            }
        }
    }

    private fun setupBottomNavigationView() {
        fragments = ArrayList()
        fragments.add(PostFragment.newInstance())
        fragments.add(VideoFragment.newInstance())
        fragments.add(QuizFragment.newInstance())
        fragments.add(MyPageFragment.newInstance())
        controller = FragNavController(supportFragmentManager, R.id.fragmentContainer)
        controller.transactionListener = this
        controller.fragmentHideStrategy = FragNavController.DETACH
        controller.navigationStrategy =
            UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    getBinding()!!.bottomNavigationView.setSelectedItemId(
                        getTabId(index)
                    )
                }
            })

        controller.rootFragments = fragments
        controller.defaultTransactionOptions = Builder().customAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right
        ).build()
        controller.initialize(TAB_POST, null)

        getBinding()!!.bottomNavigationView.visibility = VISIBLE;
        getBinding()!!.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val tabIndex = getTabIndex(item.itemId)
            controller.switchTab(tabIndex, Builder().build())
            getBinding()!!.toolbar.tvTitle.text = item.title
            true
        }
        getBinding()!!.bottomNavigationView.setOnNavigationItemReselectedListener { item -> controller.clearStack() }
    }

    private fun setUpToolbar() {
        setSupportActionBar(getBinding()!!.toolbar.appBar)
    }

    private fun openDrawer() {
//        getBinding()!!.drawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        getBinding()!!.toolbar.ivMenu.setOnClickListener {
//            getBinding()!!.drawer.setDrawerLockMode(LOCK_MODE_LOCKED_OPEN)
            getBinding()!!.drawer.openDrawer(Gravity.RIGHT)
            getBinding()!!.navigationview.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.logout -> {
                        FirebaseAuth.getInstance().signOut()
                        LoginManager.getInstance().logOut()
                        ZaloSDK.Instance.unauthenticate()
                        startActivity(Intent(this, LoginAndRegisterActivity::class.java))
                    }
                }
                true
            }
        }
    }

    override fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return false

    }

    private fun getTabId(i: Int): Int {
        when (i) {
            TAB_POST -> return R.id.menu_post
            TAB_VIDEO -> return R.id.menu_video
            TAB_QUIZ -> return R.id.menu_quiz
            TAB_MY_PAGE -> return R.id.menu_my_page
        }
        return -1
    }

    private fun getTabIndex(i: Int): Int {
        when (i) {
            R.id.menu_post -> return TAB_POST
            R.id.menu_video -> return TAB_VIDEO
            R.id.menu_quiz -> return TAB_QUIZ
            R.id.menu_my_page -> return TAB_MY_PAGE
        }
        return TAB_POST
    }

    private fun getTile(i: Int) {

    }

    fun dismissCurrentDialogFragment() {
        if (currentDialogFragment != null) {
            currentDialogFragment.dismissAllowingStateLoss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        if (controller != null) {
            controller.onSaveInstanceState(outState)
        }
    }

    override fun onBackPressed() {
        if (controller == null) {
            return
        }
        if (!controller.popFragment()) {
            super.onBackPressed()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }


    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {

    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        getBinding()!!.bottomNavigationView.selectedItemId = getTabId(index)
    }

    override fun clearBackStack() {
        controller.clearStack()
    }

    override fun showDialogFragment(fragment: DialogFragment) {
        dismissCurrentDialogFragment()
        currentDialogFragment = fragment
//        currentDialogFragment.show(supportFragmentManager, PlayerFullFragment::class.java.getName())
    }

    override fun pushFragment(fragment: Fragment, options: FragNavTransactionOptions) {
        var option = options
        if (option == null) {
            option = Builder().allowReordering(true).build()
        }
        controller?.pushFragment(fragment, option)
    }

    override fun popFragment(depth: Int, options: FragNavTransactionOptions) {
        var option = options
        if (option == null) {
            option = Builder().allowStateLoss(true).allowReordering(true)
                .build()
        }
        controller?.popFragments(depth, option)
    }

    override fun onClick(v: View?) {
        if (lifecycle.currentState !== Lifecycle.State.RESUMED) {
            return
        }
        when (v!!.id) {
        }
    }


}