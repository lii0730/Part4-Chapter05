package com.example.aop_part4_chapter05.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aop_part4_chapter05.DB.DatabaseProvider
import com.example.aop_part4_chapter05.Entity.GitHubRepos
import com.example.aop_part4_chapter05.R
import com.example.aop_part4_chapter05.databinding.ActivitySelectedReposBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SelectedReposActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivitySelectedReposBinding
    private var isliked: Boolean = false
    private var selectedRepo: GitHubRepos? = null

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedReposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData(getRepo())
        initViews()
    }

    //TODO: 선택된 repo의 정보를 가져옴
    private fun getRepo(): GitHubRepos? {
        if (intent.getParcelableExtra<GitHubRepos>("repo") != null) {
            selectedRepo = intent.getParcelableExtra("repo") as GitHubRepos?
        }
        return selectedRepo
    }

    //TODO: 선택된 repo의 정보를 SetData
    private fun setData(selectedRepo: GitHubRepos?) {
        Glide.with(binding.avatarImageView)
            .load(selectedRepo!!.owner?.avatar_url)
            .circleCrop()
            .into(binding.avatarImageView)

        binding.fullNameTextView.text = selectedRepo.full_name
        binding.starCountTextView.text = selectedRepo.stargazers_count.toString()
        binding.languageTextView.text = selectedRepo.language
        binding.descContentTextView.text = selectedRepo.description ?: "설명 없음"
        binding.updateTimeContentTextView.text = selectedRepo.updated_at

        setLikeState(selectedRepo)
    }

    private fun initViews() {
        binding.likeCheckButton.setOnClickListener {
            if (isliked.not()) {
                setImageState(isliked.not())
                addFavoriteList()
            } else {
                setImageState(isliked.not())
                deleteFavoriteList()
            }
            isliked = !isliked
        }
    }

    //TODO: Favorite DB에 있는지 확인 후 이미지 상태 변경
    private fun setLikeState(repo: GitHubRepos?) {
        launch {
            withContext(Dispatchers.IO) {
                val repository = DatabaseProvider.getFavoriteDB(applicationContext).favoriteDao()
                    .getFavoriteRepo(repo!!.full_name)
                withContext(Dispatchers.Main) {
                    isliked = repository != null
                    setImageState(isliked)
                }
            }
        }
    }

    private fun setImageState(isliked: Boolean) {
        if (isliked) {
            Glide.with(binding.likeCheckButton)
                .load(R.drawable.ic_like)
                .into(binding.likeCheckButton)
        } else {
            Glide.with(binding.likeCheckButton)
                .load(R.drawable.ic_dislike)
                .into(binding.likeCheckButton)
        }
    }

    private fun addFavoriteList() {
        launch {
            withContext(Dispatchers.IO) {
                DatabaseProvider.getFavoriteDB(applicationContext).favoriteDao()
                    .insertRepo(selectedRepo!!)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SelectedReposActivity,
                        "찜 리스트에 추가 하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun deleteFavoriteList() {
        launch {
            withContext(Dispatchers.IO) {
                DatabaseProvider.getFavoriteDB(applicationContext).favoriteDao()
                    .deleteRepo(selectedRepo!!.full_name)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SelectedReposActivity,
                        "찜 리스트에서 삭제 하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}