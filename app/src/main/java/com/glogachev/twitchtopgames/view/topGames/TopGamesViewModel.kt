package com.glogachev.twitchtopgames.view.topGames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TopGamesViewModel(private val repository: TopGamesRepository) : ViewModel() {
    private var disposable: Disposable? = null
    private var _topGamesList = MutableLiveData<List<GameDB>>()
    val topGamesList: LiveData<List<GameDB>>
        get() = _topGamesList

    private var _isNetworkProblem = MutableLiveData<Boolean>(false)
    val isNetworkProblem: LiveData<Boolean>
        get() = _isNetworkProblem
    private var _isEmptyList = MutableLiveData<Boolean>(false)
    val isEmptyList: LiveData<Boolean>
        get() = _isEmptyList
    private var _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getListOfGames() {
        disposable = repository
            .getTopGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe({ listOfGames ->
                /*тест пустого списка
                //val emptyGamesList = listOf<GameDB>()
                 if (emptyGamesList.isNotEmpty()) { */
                /*тест лоадера
                Thread.sleep(5000L)*/
                _isLoading.value = false
                if (listOfGames.isNotEmpty()) {
                    _topGamesList.value = listOfGames
                } else {
                    _isEmptyList.value = true
                }
            },
                {
                    _isNetworkProblem.value = true
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}