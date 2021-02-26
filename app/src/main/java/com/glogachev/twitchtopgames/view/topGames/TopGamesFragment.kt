package com.glogachev.twitchtopgames.view.topGames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.glogachev.twitchtopgames.App
import com.glogachev.twitchtopgames.R
import com.glogachev.twitchtopgames.view.topGames.adapter.TopGamesAdapter
import kotlinx.android.synthetic.main.fragment_top_games.*

class TopGamesFragment : Fragment() {

    private lateinit var viewModel: TopGamesViewModel
    private lateinit var topGamesAdapter: TopGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = App.appRepository
        val viewModelFactory = TopGamesViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(TopGamesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfGames()
        viewModel.topGamesList.observe(viewLifecycleOwner, { list->
            topGamesAdapter = TopGamesAdapter(list)
            rv_top_games.also {
                it.hasFixedSize()
                it.adapter = topGamesAdapter
            }
        })
        viewModel.isNetworkProblem.observe(viewLifecycleOwner,{ isNetworkProblem ->
            network_problem_tw.isVisible = isNetworkProblem
        })
        viewModel.isEmptyList.observe(viewLifecycleOwner, Observer { isEmptyList ->
            empty_list_tw.isVisible = isEmptyList
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            loading_pb.isVisible = isLoading
        })

        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = false
            viewModel.getListOfGames()
        }
    }


}