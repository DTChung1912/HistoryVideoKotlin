package com.example.historyvideokotlin.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseActivity
import com.example.historyvideokotlin.databinding.ActivityMainBinding
import com.example.historyvideokotlin.fragments.*
import com.example.historyvideokotlin.listener.OnToolBarListener
import com.example.historyvideokotlin.ui.FragmentNavigation
import com.example.historyvideokotlin.ui.ProgressBarDialog
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import com.example.historyvideokotlin.viewmodels.MainViewModel
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.FragNavTransactionOptions.Builder
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import com.zing.zalo.zalosdk.oauth.*
import com.zing.zalo.zalosdk.oauth.model.ErrorResponse
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    FragNavController.TransactionListener, FragmentNavigation, OnToolBarListener,
    View.OnClickListener {

    val callbackManager = CallbackManager.Factory.create()
    val RC_SIGN_IN = 123
    var mGoogleSignInClient: GoogleSignInClient? = null

    val TAB_POST = FragNavController.TAB1
    val TAB_VIDEO = FragNavController.TAB2
    val TAB_QUIZ = FragNavController.TAB3
    val TAB_MY_PAGE = FragNavController.TAB4

    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var controller: FragNavController

    private lateinit var currentDialogFragment: DialogFragment
    private lateinit var progressBarDialog: ProgressBarDialog
    private lateinit var loginViewModel: LoginViewModel

    private var userId = ""

    private lateinit var auth: FirebaseAuth

    private var contentHasLoaded = false

    private var verificationId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_HistoryVideoKotlin)
        super.onCreate(savedInstanceState)
        initData()
        progressBarDialog = ProgressBarDialog(this)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loadingLiveData.observe(this) { show ->
            if (show) {
                progressBarDialog.show()
            } else {
                progressBarDialog.dismiss()
            }
        }

        loginViewModel.userList.observe(this, { data ->
            data.let {
                userId = it[0].user_id
//                user = it[0]
            }
        })

//        setupBottomNavigationView()
        setupBottomNavigationWithNavigationComponent()

        initFacebook()
        initGoogle()
        initZalo()


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

        loginViewModel.loadingLiveData.observe(this) { show ->
            if (show) {
                progressBarDialog.show()
            } else {
                progressBarDialog.dismiss()
            }
        }

        loginViewModel.userList.observe(this, { data ->
            data.let {
                userId = it[0].user_id
//                user = it[0]
            }
        })
        auth = Firebase.auth
        initFacebook()
        initGoogle()
        initZalo()
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fragmentContainer).navigateUp()
    }

    private fun setupBottomNavigationWithNavigationComponent() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController: NavController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.postFragment,R.id.videoFragment,R.id.quizFragment,R.id.myPageFragment))
//        setupActionBarWithNavController(navController,appBarConfiguration)

//        getBinding()!!.bottomNavigationView.setupWithNavController(navController)

        NavigationUI.setupWithNavController(getBinding()!!.bottomNavigationView, navController)

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
        getViewModel()!!.getIsShowBottomMenu().observe(this) { show ->
            getBinding()!!.bottomNavigationView.visibility =
                if (show != null && show) VISIBLE else GONE
        }

        getBinding()!!.bottomNavigationView.visibility = VISIBLE;
        getBinding()!!.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val tabIndex = getTabIndex(item.itemId)
            controller.switchTab(tabIndex, Builder().build())
//            getBinding()!!.toolbar.tvTitle.text = item.title
            true
        }
        getBinding()!!.bottomNavigationView.setOnNavigationItemReselectedListener { item -> controller.clearStack() }
    }

    private fun openDrawer() {

        getBinding()!!.run {
            drawer.openDrawer(Gravity.RIGHT)
            if (HistoryUserManager.checkUserLogined()) {
                navigationview.menu.findItem(R.id.login).title = "Đăng xuất"
            } else {
                navigationview.menu.findItem(R.id.login).title = "Đăng Nhập"
            }

            navigationview.setNavigationItemSelectedListener {
                when (it.itemId) {
//                    R.id.logout -> {
//                        it.isVisible = false
//                        navigationview.menu.findItem(R.id.login).isVisible = true
//                    }

                    R.id.login -> {
                        if (HistoryUserManager.checkUserLogined()) {
                            MyLog.e("FUid", HistoryUserManager.FUid())
                            Firebase.auth.signOut()
                            LoginManager.getInstance().logOut()
                            ZaloSDK.Instance.unauthenticate()
                            drawer.closeDrawer(Gravity.RIGHT)
                        } else {
                            drawer.closeDrawer(Gravity.RIGHT)
                            MyLog.e("FUid", HistoryUserManager.FUid())
                            pushFragment(
                                HistoryLoginFragment.newInstance(),
                                HistoryUtils.getSlideTransitionAnimationOptions()
                            )
                        }
//                        it.isVisible = false
//                        navigationview.menu.findItem(R.id.logout).isVisible = true

                    }
                }
                true
            }
        }
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
        val fragment = controller.currentFrag
        if (fragment != null) {
            val options = Builder()
                .allowReordering(true)
                .customAnimations(
                    R.anim.slide_up,
                    R.anim.no_animation,
                    R.anim.no_animation,
                    R.anim.slide_down
                )
                .build()
            controller.popFragments(1, options)
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
            option = Builder().allowReordering(true).customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            ).build()
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
    }

    private fun initFacebook() {
        printHashKey()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(application)
    }

    fun printKeyHash(context: Activity): String? {
        val packageInfo: PackageInfo
        var key: String? = null
        try {
            //getting application package name, as defined in manifest
            val packageName = context.applicationContext.packageName

            //Retriving package info
            packageInfo = context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            MyLog.e("Package Name=", context.applicationContext.packageName)
            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                key = String(Base64.encode(md.digest(), 0))

                // String key = new String(Base64.encodeBytes(md.digest()));
                MyLog.e("Key Hash=", key)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            MyLog.e("Name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            MyLog.e("No such an algorithm", e.toString())
        } catch (e: Exception) {
            MyLog.e("Exception", e.toString())
        }
        return key
    }

    fun printHashKey() {

        try {
            val info: PackageInfo = this.getPackageManager().getPackageInfo(
                "com.android.facebookloginsample",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "KeyHash:",
                    Base64.encodeToString(
                        md.digest(),
                        Base64.DEFAULT
                    )
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    fun loginWithPhoneNumber() {

    }

    fun phoneVerifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId,code)
        signInbyCredentials(credential)
    }

    private fun signInbyCredentials(credential: PhoneAuthCredential) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    MyLog.e("UID", user?.uid)

                    getViewModel()!!.postUser(
                        user!!.uid, user.displayName.toString(),
                        user?.email.toString()
                    )

                    popFragment(1, HistoryUtils.getSlideTransitionAnimationOptions())
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun phoneSendVerifyCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code : String? = credential.smsCode
            if (code != null) {
                phoneVerifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@MainActivity,"fail",Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            s: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(s, token)

            verificationId = s
            showLoading(false)
        }
    }

    fun loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
            this, Arrays.asList(
                "email",
                "public_profile",
                "user_birthday"
            )
        )
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onCancel() {
                MyLog.e("loginFacebook", "cancel")
            }

            override fun onError(error: FacebookException) {
                MyLog.e("loginFacebook", error.message.toString())
            }

            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)
            }

        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    MyLog.e("UID", user?.uid)

                    getViewModel()!!.postUser(
                        user!!.uid, user.displayName.toString(),
                        user?.email.toString()
                    )

                    popFragment(1, HistoryUtils.getSlideTransitionAnimationOptions())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun initGoogle() {
        var gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

//        var account: GoogleSignInAccount? =
//            GoogleSignIn.getLastSignedInAccount(this)
//        loginAppWithGoogleAccount(account)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            loginAppWithGoogleAccount(account)
        } catch (e: ApiException) {
            loginAppWithGoogleAccount(null)
        }
    }

    private fun loginAppWithGoogleAccount(account: GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    MyLog.e("UID", user?.uid)

                    getViewModel()!!.postUser(
                        user!!.uid, user.displayName.toString(),
                        user?.email.toString()
                    )

                    popFragment(1, HistoryUtils.getSlideTransitionAnimationOptions())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        if (account != null) {
            popFragment(1, HistoryUtils.getSlideTransitionAnimationOptions())
        }

    }

    fun loginWithGoogle() {
        val intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun initZalo() {
        ZaloSDKApplication.wrap(application)
//        if(!ZaloSDK.Instance.isAuthenticate(null,null)) {
//        }
    }


    fun onLoginError(code: Int, message: String) {
        Toast.makeText(this, "[$code] $message", Toast.LENGTH_LONG).show()
    }

    private val listener = object : OAuthCompleteListener() {
        override fun onGetOAuthComplete(response: OauthResponse?) {
            if (TextUtils.isEmpty(response?.oauthCode)) {
                onLoginError(response?.errorCode ?: -1, response?.errorMessage ?: "Unknown error")
            } else {
                popFragment(1, HistoryUtils.getSlideTransitionAnimationOptions())

            }
        }

        override fun onAuthenError(errorCode: ErrorResponse?) {
            onLoginError(errorCode!!.errorCode, errorCode.errorMsg ?: "Unknown error")
        }

    }

    fun loginWithZalo() {
        ZaloSDK.Instance.authenticateZaloWithAuthenType(this, LoginVia.APP_OR_WEB, "", listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ZaloSDK.Instance.onActivityResult(this, requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {

            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onSearchClick() {
        pushFragment(
            SearchFragment.newInstance("a"),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onSettingClick() {
        openDrawer()
    }
}