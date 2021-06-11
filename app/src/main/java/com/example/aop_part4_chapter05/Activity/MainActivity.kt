package com.example.aop_part4_chapter05.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part4_chapter05.Adapter.RepositoryAdapter
import com.example.aop_part4_chapter05.DB.DatabaseProvider
import com.example.aop_part4_chapter05.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val mainBinding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter : RepositoryAdapter

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        initViews()
        initRecyclerView()
        loadLikeRepositories()
    }

    override fun onRestart() {
        super.onRestart()
        loadLikeRepositories()
    }

    private fun initViews() {
        mainBinding.searchReposButton.setOnClickListener {
            startActivity(Intent(this, SearchReposActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        adapter = RepositoryAdapter(onClickedItem ={ repo->
            val intent = Intent(this, SelectedReposActivity::class.java)
            intent.putExtra("repo", repo)
            startActivity(intent)
        })

        mainBinding.likeReposRecyclerView.adapter = adapter
        mainBinding.likeReposRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadLikeRepositories() {
        launch {
            withContext(Dispatchers.IO) {
                val favoriteList = DatabaseProvider.getFavoriteDB(applicationContext).favoriteDao().getAllFavoriteRepo()
                withContext(Dispatchers.Main) {
                    mainBinding.noLikeReposTextView.isVisible = favoriteList.size <= 0
                    adapter.submitList(favoriteList)
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}