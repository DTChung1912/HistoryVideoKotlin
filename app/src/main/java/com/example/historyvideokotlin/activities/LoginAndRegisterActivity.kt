package com.example.historyvideokotlin.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseActivity
import com.example.historyvideokotlin.databinding.ActivityLoginBinding
import com.example.historyvideokotlin.fragments.HistoryLoginFragment
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.zing.zalo.zalosdk.oauth.*
import com.zing.zalo.zalosdk.oauth.model.ErrorResponse
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginAndRegisterActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    val callbackManager = CallbackManager.Factory.create()
    val RC_SIGN_IN = 123
    var mGoogleSignInClient : GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setScreenNoLimit()
        initFacebook()
        initGoogle()
        initZalo()
        printKeyHash(this)

        showFragment(
            R.id.fragmentContainer, HistoryLoginFragment.newInstance(), false, null
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login;
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
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
            Log.e("Package Name=", context.applicationContext.packageName)
            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                key = String(Base64.encode(md.digest(), 0))

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("Name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("No such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
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

    fun loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
            "email",
            "public_profile",
            "user_birthday"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.e("chung", "cancel")
            }

            override fun onError(error: FacebookException) {
                Log.e("chung", error.message.toString())
            }

            override fun onSuccess(result: LoginResult) {
                val intent = Intent(this@LoginAndRegisterActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

        })
    }

    private fun initGoogle() {
        var gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        var account : GoogleSignInAccount? =
            GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            updateUI(account)
        } catch (e: ApiException) {
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    fun loginWithGoogle() {
        val intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(intent,RC_SIGN_IN)
    }

    private fun initZalo() {
        ZaloSDKApplication.wrap(application)
//        if(!ZaloSDK.Instance.isAuthenticate(null,null)) {
//            onLoginSuccess()
//        }
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    fun onLoginError(code: Int, message: String) {
        Toast.makeText(this, "[$code] $message", Toast.LENGTH_LONG).show()
    }

    private val listener = object : OAuthCompleteListener() {
        override fun onGetOAuthComplete(response: OauthResponse?) {
            if(TextUtils.isEmpty(response?.oauthCode)) {
                onLoginError(response?.errorCode ?: -1, response?.errorMessage ?: "Unknown error")
            } else {
                onLoginSuccess()
            }
        }

        override fun onAuthenError(errorCode: ErrorResponse?) {
            onLoginError(errorCode!!.errorCode, errorCode.errorMsg ?: "Unknown error")
        }

    }

    fun loginWithZalo() {
        ZaloSDK.Instance.authenticateZaloWithAuthenType(this, LoginVia.APP_OR_WEB,"", listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ZaloSDK.Instance.onActivityResult(this, requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
        if (requestCode === RC_SIGN_IN) {

            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

}