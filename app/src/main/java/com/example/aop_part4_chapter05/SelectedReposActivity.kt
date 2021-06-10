package com.example.aop_part4_chapter05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.aop_part4_chapter05.Entity.GitHubRepos
import com.example.aop_part4_chapter05.databinding.ActivitySelectedReposBinding

class SelectedReposActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedReposBinding
    private var isliked: Boolean = false
    private var selectedRepo : GitHubRepos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedReposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData(getRepo())
        initViews()
    }

    private fun getRepo() : GitHubRepos? {
        if(intent.getParcelableExtra<GitHubRepos>("repo") != null) {
            selectedRepo = intent.getParcelableExtra("repo") as GitHubRepos?
        }
        return selectedRepo
    }

    private fun setData(selectedRepo : GitHubRepos?) {
        Glide.with(binding.avatarImageView)
            .load(selectedRepo!!.owner?.avatar_url)
            .circleCrop()
            .into(binding.avatarImageView)

        binding.fullNameTextView.text = selectedRepo.full_name
        binding.starCountTextView.text = selectedRepo.stargazers_count.toString()
        binding.languageTextView.text = selectedRepo.language
        binding.descContentTextView.text = selectedRepo.description ?: "설명 없음"
        binding.updateTimeContentTextView.text = selectedRepo.updated_at
    }

    private fun initViews() {
        binding.likeCheckButton.setOnClickListener {
            if (isliked.not()) {
                addFavoriteList()
            } else {
                deleteFavoriteList()
            }
            isliked = !isliked
        }
    }

    private fun addFavoriteList() {
        Glide.with(binding.likeCheckButton)
            .load(R.drawable.ic_like)
            .into(binding.likeCheckButton)
        Toast.makeText(this, "찜 리스트에 추가 하였습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteList() {
        Glide.with(binding.likeCheckButton)
            .load(R.drawable.ic_dislike)
            .into(binding.likeCheckButton)
        Toast.makeText(this, "찜 리스트에서 삭제 하였습니다.", Toast.LENGTH_SHORT).show()
    }
}