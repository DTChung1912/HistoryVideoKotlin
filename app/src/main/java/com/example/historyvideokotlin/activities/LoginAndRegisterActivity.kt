package com.example.historyvideokotlin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseActivity
import com.example.historyvideokotlin.databinding.ActivityLoginBinding
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*


class LoginAndRegisterActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(), View.OnClickListener {
    var isUserLogined: Boolean = false
    lateinit var callbackManager : CallbackManager
    val EMAIL = "email"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setScreenNoLimit()
        setUpButtonClick()

        getBinding()!!.loginButton.setReadPermissions(Arrays.asList(EMAIL))

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Log.e("chung",error.message.toString())
            }

            override fun onSuccess(result: LoginResult) {
                val intent = Intent(this@LoginAndRegisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })

        getBinding()!!.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {
            }

            override fun onSuccess(result: LoginResult) {
                val intent = Intent(this@LoginAndRegisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }

    override fun onStart() {
        super.onStart()

        if (isUserLogined) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login;
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onClick(v: View?) {
        if (lifecycle.currentState !== Lifecycle.State.RESUMED) {
            return
        }
        when (v!!.id) {
            R.id.btnLogin -> {
                openMainActivity()
            }
            R.id.ibFacebook -> {
                loginWithFacebook()
            }
            R.id.ibZalo -> {
                loginWithZalo()
            }
            R.id.ibGoogle -> {
                loginWithGoogle()
            }
        }
    }

    private fun setUpButtonClick() {
        getBinding()!!.btnLogin.setOnClickListener(this)
        getBinding()!!.ibFacebook.setOnClickListener(this)
        getBinding()!!.ibZalo.setOnClickListener(this)
        getBinding()!!.ibGoogle.setOnClickListener(this)
    }

    private fun loginWithGoogle() {

    }

    private fun loginWithZalo() {

    }

    private fun loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
    }

    private fun openMainActivity() {
        var email = getBinding()!!.edtEmail.text.toString()
        var password = getBinding()!!.edtPassword.text.toString()
        var isUserLogined = getViewModel()!!.loginWithEmail(email,password)

        if (isUserLogined) {
            Toast.makeText(
                this,
                "Failed to login, ",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}