package com.pinedeveloper.homework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinedeveloper.homework.R
import com.pinedeveloper.homework.databinding.ActivityMainBinding
import com.pinedeveloper.homework.di.DaggerAppComponent
import com.pinedeveloper.homework.presentation.Adapter.LoadStateAdapter
import com.pinedeveloper.homework.presentation.Adapter.RickAdapterPage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pagedAdapter = RickAdapterPage()
    private val viewModel: MainViewModel by viewModels{
        DaggerAppComponent.create().mainViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadRickAndMorty()
        setListenerError()
        setListenerBttReload()
    }

    private fun setListenerError(){
        viewModel.throwable.observe(this){
            val showError = it != null
            binding.swipeRefresh.isVisible = !showError
            binding.bttReload.isVisible = showError

            if(showError) Toast.makeText(this,it?.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun setListenerBttReload(){
        binding.bttReload.setOnClickListener {
            pagedAdapter.refresh()
            binding.swipeRefresh.isVisible = true
            binding.bttReload.isVisible = false
        }
    }

    private fun loadRickAndMorty(){
        binding.rvRickAndMorty.hasFixedSize()
        binding.rvRickAndMorty.layoutManager = LinearLayoutManager(this)
        binding.rvRickAndMorty.adapter = pagedAdapter.withLoadStateFooter(LoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener {
            pagedAdapter.refresh()
        }
        pagedAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(lifecycleScope)

        viewModel.pagedRickAndMorty.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(lifecycleScope)

    }
}