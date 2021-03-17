package com.glogachev.twitchtopgames.view.topGames.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glogachev.twitchtopgames.App
import com.glogachev.twitchtopgames.R
import com.glogachev.twitchtopgames.databinding.FragmentTopGamesBinding
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.model.GameDomain
import com.glogachev.twitchtopgames.view.topGames.main.adapter.OnItemClickListener
import com.glogachev.twitchtopgames.view.topGames.main.adapter.TopGamesAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.alert_dialog.view.*
import timber.log.Timber

class TopGamesFragment : Fragment() {

    private var _binding: FragmentTopGamesBinding? = null
    private val binding: FragmentTopGamesBinding
        get() = _binding!!

    private lateinit var viewModel: TopGamesViewModel
    private val topGamesAdapter: TopGamesAdapter = TopGamesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            if (!isInternetAvailable(requireContext()))
                showSnackbar()
        }
        binding.nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v?.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                    binding.rvProgressBar.isVisible = true
                    viewModel.getNextGamesPage()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_send_feedback -> {
                showFeedbackDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFeedbackDialog() {
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.alert_dialog, null)
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        dialogView.feedback_tie.addTextChangedListener {
            dialogView.feedback_til.isCounterEnabled = it.toString().trim().isNotBlank()
            Timber.d(it.toString())
        }
        dialogView.alert_cancel_btn.setOnClickListener {
            alertDialog.dismiss()
        }
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

    private fun showSnackbar() {
        val snackbar = Snackbar.make(
            binding.topGamesConstraintContainer,
            "Невозможно обновить данные. Попробуйте снова",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Скрыть") {
            snackbar.dismiss()
        }
            .show()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}