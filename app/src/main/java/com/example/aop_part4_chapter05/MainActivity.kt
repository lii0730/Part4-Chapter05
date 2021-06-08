package com.example.aop_part4_chapter05

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import com.example.aop_part4_chapter05.DTO.AuthApiDTO
import com.example.aop_part4_chapter05.Entity.AuthApiResponse
import com.example.aop_part4_chapter05.Services.AuthApiService
import com.example.aop_part4_chapter05.URL.URL
import com.example.aop_part4_chapter05.databinding.ActivityMainBinding
import com.example.aop_part4_chapter05.utility.RetrofitUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

	private val mainBinding: ActivityMainBinding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	val job : Job = Job()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(mainBinding.root)

		initLoginButton()
	}

	private fun initLoginButton() {
		mainBinding.loginButton.setOnClickListener {
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
			it.launchUrl(this@MainActivity, loginUri)
		}
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)
		intent?.let {
			setIntent(it)
			it.data?.getQueryParameter("code")?.let { code->
				//TODO: get Access Token
				showProgress()
				launch(coroutineContext) {
					getAccessToken(code)
				}
				hideProgress()
				launchReposActivity()
			}
		}
	}

	private fun launchReposActivity() {
		val intent = Intent(this@MainActivity, SearchReposActivity::class.java)
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		startActivity(intent)
	}

	private fun showProgress() {
		mainBinding.loginButton.isVisible = false
		mainBinding.progressBar.isVisible = true
		mainBinding.loginText.isVisible = true
	}

	private fun hideProgress() {
		mainBinding.loginButton.isVisible = true
		mainBinding.progressBar.isVisible = false
		mainBinding.loginText.isVisible = false
	}

	suspend fun getAccessToken(code: String) = launch(coroutineContext) {
		//TODO: code라는 파라미터를 가지고 Authorize 한 사용자의 Access Token 을 구함
		//TODO: code는 Access Token이 아님 / code를 파라미터로 하여 요청을 해야 원하는 Access Token을 구할 수 있다.
		//TODO: https://github.com/login/oauth/access_token 에 post 요청
		withContext(Dispatchers.IO) {
			val response = RetrofitUtil.authApiService.getAccessToken(
				BuildConfig.GITHUB_CLIENT_ID,
				BuildConfig.GITHUB_CLIENT_SECRET,
				code
			)
		}
	}

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + job
}