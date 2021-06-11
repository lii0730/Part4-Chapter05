package com.example.aop_part4_chapter05.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import com.example.aop_part4_chapter05.BuildConfig
import com.example.aop_part4_chapter05.databinding.ActivityLoginBinding
import com.example.aop_part4_chapter05.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {

    private val loginBinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        initLoginButton()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            setIntent(it)
            it.data?.getQueryParameter("code")?.let { code ->
                //TODO: get Access Token

                Log.i("getCode", code)
                launch(coroutineContext) {
                    getAccessToken(code)
                    hideProgress()
                }
                launchReposActivity()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun initLoginButton() {
        loginBinding.loginButton.setOnClickListener {
            //TODO: github login 기능 구현
            loginGithub()
        }
    }

    private fun loginGithub() {
        val loginUri = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()

        CustomTabsIntent.Builder().build().also {
            showProgress()
            it.launchUrl(this@LoginActivity, loginUri)
        }

    }


    private fun launchReposActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun showProgress() {
        loginBinding.loginButton.isVisible = false
        loginBinding.progressBar.isVisible = true
        loginBinding.loginText.isVisible = true
    }

    private fun hideProgress() {
        loginBinding.loginButton.isVisible = true
        loginBinding.progressBar.isVisible = false
        loginBinding.loginText.isVisible = false
    }

    private fun getAccessToken(code: String) {

        launch {
            //TODO: code라는 파라미터를 가지고 Authorize 한 사용자의 Access Token 을 구함
            //TODO: code는 Access Token이 아님 / code를 파라미터로 하여 요청을 해야 원하는 Access Token을 구할 수 있다.
            //TODO: https://github.com/login/oauth/access_token 에 post 요청
            withContext(Dispatchers.IO) {
                val response = RetrofitUtil.authApiService.getAccessToken(
                    BuildConfig.GITHUB_CLIENT_ID,
                    BuildConfig.GITHUB_CLIENT_SECRET,
                    code
                )
                Log.i("access_token", response.access_token)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

}