package com.glogachev.twitchtopgames.view.topGames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TopGamesViewModel(private val repository: TopGamesRepository) : ViewModel() {
    private var disposable: Disposable? = null

    private var _listGamesState =
        MutableLiveData<StoreResult<List<GameDB>>>(StoreResult.Loading)
    val listGamesState: LiveData<StoreResult<List<GameDB>>>
        get() = _listGamesState

    fun getListOfGames() {
        disposable = repository
            .getTopGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { gamesNetworkResult ->
                _listGamesState.value = gamesNetworkResult
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}