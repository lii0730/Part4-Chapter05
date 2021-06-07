package com.example.aop_part4_chapter05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aop_part4_chapter05.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private val mainBinding: ActivityMainBinding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(mainBinding.root)

		initLoginButton()
	}

	private fun initLoginButton() {
		mainBinding.loginButton.setOnClickListener {
			//TODO: github login 기능 구현
			Toast.makeText(this, "로그인 버튼 클릭", Toast.LENGTH_SHORT).show()
		}
	}
}