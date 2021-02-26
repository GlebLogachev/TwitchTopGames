package com.glogachev.twitchtopgames.view.topGames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.glogachev.twitchtopgames.App
import com.glogachev.twitchtopgames.databinding.FragmentTopGamesBinding
import com.glogachev.twitchtopgames.view.topGames.adapter.TopGamesAdapter

class TopGamesFragment : Fragment() {
    private lateinit var binding: FragmentTopGamesBinding
    private lateinit var viewModel: TopGamesViewModel
    private lateinit var topGamesAdapter: TopGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = App.appRepository
        val viewModelFactory = TopGamesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TopGamesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfGames()
        viewModel.topGamesList.observe(viewLifecycleOwner, { list ->
            topGamesAdapter = TopGamesAdapter(list)
            binding.rvTopGames.also {
                it.hasFixedSize()
                it.adapter = topGamesAdapter
            }
        })
        viewModel.isNetworkProblem.observe(viewLifecycleOwner, { isNetworkProblem ->
            binding.networkProblemTw.isVisible = isNetworkProblem
        })
        viewModel.isEmptyList.observe(viewLifecycleOwner, Observer { isEmptyList ->
            binding.emptyListTw.isVisible = isEmptyList
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loadingPb.isVisible = isLoading
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getListOfGames()
        }
    }


}