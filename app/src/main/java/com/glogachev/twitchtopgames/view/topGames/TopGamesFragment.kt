package com.glogachev.twitchtopgames.view.topGames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glogachev.twitchtopgames.App
import com.glogachev.twitchtopgames.databinding.FragmentTopGamesBinding
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.model.GameDomain
import com.glogachev.twitchtopgames.view.topGames.adapter.OnItemClickListener
import com.glogachev.twitchtopgames.view.topGames.adapter.TopGamesAdapter

class TopGamesFragment : Fragment() {
    private var _binding: FragmentTopGamesBinding? = null
    private val binding: FragmentTopGamesBinding
        get() = _binding!!

    private lateinit var viewModel: TopGamesViewModel
    private val topGamesAdapter: TopGamesAdapter = TopGamesAdapter()

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
        _binding = FragmentTopGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTopGames.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTopGames.adapter = topGamesAdapter
        viewModel.getListOfGames()
        topGamesAdapter.setListener(rvItemClickListener())
        viewModel.listGamesState.observe(
            viewLifecycleOwner,
            Observer<StoreResult<List<GameDomain>>> { result ->
                sortFetchingData(result)
            })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getListOfGames()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun rvItemClickListener(): OnItemClickListener {
        return object : OnItemClickListener {
            override fun onClick(game: GameDomain) {
                val action =
                    TopGamesFragmentDirections.actionTopGamesFragmentToDetailsFragment(gameObject = game)
                findNavController().navigate(action)
            }
        }
    }

    private fun sortFetchingData(result: StoreResult<List<GameDomain>>?) {
        when (result) {
            is StoreResult.SuccessResult -> {
                setSuccessState(result)
            }
            is StoreResult.Error -> {
                setErrorState()
            }
            is StoreResult.Loading -> {
                binding.loadingPb.isVisible = true
            }
        }
    }

    private fun setSuccessState(result: StoreResult.SuccessResult<List<GameDomain>>) {
        if (result.data.isNullOrEmpty()) {
            binding.emptyListTw.isVisible = true
        } else {
            topGamesAdapter.submitList(result.data)
            binding.emptyListTw.isVisible = false
        }
        binding.loadingPb.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.errorTw.isVisible = false
    }

    private fun setErrorState() {
        binding.loadingPb.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.errorTw.isVisible = true
        binding.emptyListTw.isVisible = false
    }
}