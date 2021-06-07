package com.example.aop_part4_chapter05

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.example.aop_part4_chapter05.databinding.ActivityMainBinding
import kotlinx.coroutines.*
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
			it.launchUrl(this, loginUri)
		}
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)

		intent!!.data?.getQueryParameter("code")?.let{
			//TODO: get Access Token
			launch(coroutineContext) {
				getAccessToken(it)
			}
		}
	}

	suspend fun getAccessToken(code: String) = withContext(Dispatchers.IO) {
		val response
	}

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + job
}