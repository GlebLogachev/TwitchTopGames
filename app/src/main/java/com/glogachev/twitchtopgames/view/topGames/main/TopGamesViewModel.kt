package com.glogachev.twitchtopgames.view.topGames.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import com.glogachev.twitchtopgames.domain.model.GameDomain
import com.glogachev.twitchtopgames.utils.schedule
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TopGamesViewModel @Inject constructor(
    private val repository: TopGamesRepository
) : ViewModel() {
    private var disposable: Disposable? = null

    private var _listGamesState =
        MutableLiveData<StoreResult<List<GameDomain>>>(StoreResult.Loading)
    val listGamesState: LiveData<StoreResult<List<GameDomain>>>
        get() = _listGamesState

    fun getListOfGames() {
        disposable = repository
            .getFirstGamePage()
            .schedule()
            .subscribe { gamesNetworkResult ->
                _listGamesState.value = gamesNetworkResult
            }
    }

    fun getNextGamesPage() {
        disposable = repository
            .getNextGamesPage()
            .schedule()
            .subscribe { gamesNetworkResult ->
                _listGamesState.value = gamesNetworkResult
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}