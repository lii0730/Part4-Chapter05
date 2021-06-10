package com.example.aop_part4_chapter05

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part4_chapter05.Adapter.SearchResultRepositoryAdapter
import com.example.aop_part4_chapter05.Entity.GitHubRepos
import com.example.aop_part4_chapter05.databinding.ActivitySearchReposBinding
import com.example.aop_part4_chapter05.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchReposActivity : AppCompatActivity(), CoroutineScope {

	private val searchBinding: ActivitySearchReposBinding by lazy {
		ActivitySearchReposBinding.inflate(layoutInflater)
	}

	private lateinit var searchAdapter : SearchResultRepositoryAdapter

	private var list : List<GitHubRepos>? = null

	private val job : Job = Job()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(searchBinding.root)

		initRecyclerView()
		initViews()
	}

	private fun initViews() {
		searchBinding.searchButton.setOnClickListener {
			//TODO: repository 정보 받아오기
			launch {
				try {
					withContext(Dispatchers.IO) {
						list = RetrofitUtil.GitHubService.getRepos(
							searchBinding.searchKeywordEditText.text.toString()
						)
						withContext(Dispatchers.Main) {
							//TODO: Adapter 로 Repos List 전달
							list?.let {
								searchAdapter.submitList(it)
							}
						}
					}
				} catch (e: Exception){
					Log.e("getRepository", e.toString())
					Toast.makeText(this@SearchReposActivity, "검색 결과 없음", Toast.LENGTH_SHORT).show()
				}
			}
		}
	}

	private fun initRecyclerView() {
		searchAdapter = SearchResultRepositoryAdapter(onClickedItem = { repo ->
			//TODO: repository 클릭했을 경우 동작 처리
			val intent = Intent(this, SelectedReposActivity::class.java)
			intent.putExtra("repo", repo)
			startActivity(intent)
		})

		searchBinding.resultRecyclerview.apply {
			this.layoutManager = LinearLayoutManager(this@SearchReposActivity)
			this.adapter = searchAdapter
		}
	}

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + job
}