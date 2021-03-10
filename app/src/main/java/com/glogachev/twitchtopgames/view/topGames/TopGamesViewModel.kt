package com.glogachev.twitchtopgames.view.topGames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import com.glogachev.twitchtopgames.domain.model.GameDomain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TopGamesViewModel(private val repository: TopGamesRepository) : ViewModel() {
    private var disposable: Disposable? = null

    private var _listGamesState =
        MutableLiveData<StoreResult<List<GameDomain>>>(StoreResult.Loading)
    val listGamesState: LiveData<StoreResult<List<GameDomain>>>
        get() = _listGamesState

    fun getListOfGames() {
        disposable = repository
            .getTopGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { gamesNetworkResult ->
                Timber.d(gamesNetworkResult.toString())
                _listGamesState.value = gamesNetworkResult
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}