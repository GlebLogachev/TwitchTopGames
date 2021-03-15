package com.glogachev.twitchtopgames.view.topGames.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glogachev.twitchtopgames.domain.TopGamesRepository

class TopGamesViewModelFactory(private val repository: TopGamesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TopGamesViewModel(repository) as T
    }

}