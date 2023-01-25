package com.example.albertsons.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.albertsons.R
import com.example.albertsons.databinding.ActivityMainBinding
import com.example.albertsons.viewmodel.FactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random
import kotlin.random.nextInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: FactsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FactsViewModel::class.java]
        setUpViews()
    }

    private fun setUpViews() {
        viewModel.getCatFactData()
        observeCatInfoList()
        observeErrorLiveData()
        loadCatImage()
        binding.overlay.setOnClickListener {
            viewModel.getCatFactData()
            loadCatImage()
        }
    }

    private fun observeCatInfoList() {
        viewModel.catFactLiveData.observe(this) { catFact ->
            catFact.let {
                binding.factView.apply {
                    text = it
                    setTextColor(Color.BLACK)
                }
            }
        }
    }

    private fun observeErrorLiveData() {
        viewModel.errorLiveData.observe(this) { isError ->
            if (isError) {
                binding.factView.apply {
                    text = context.getString(R.string.cat_fact_error)
                    setTextColor(Color.RED)
                }
            }
        }
    }

    private fun loadCatImage() {
        val url = buildString {
            append(getString(R.string.cat_image_url))
            append(Random.nextInt(300..400))
            append("/")
            append(Random.nextInt(500..600))
        }
        Glide.with(this).asBitmap().load(url).into(binding.image)
    }
}