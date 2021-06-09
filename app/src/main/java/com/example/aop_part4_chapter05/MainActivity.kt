package com.example.aop_part4_chapter05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aop_part4_chapter05.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainBinding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        initViews()
    }

    private fun initViews() {
        mainBinding.searchReposButton.setOnClickListener {
            startActivity(Intent(this, SearchReposActivity::class.java))
        }
    }
}