package com.glogachev.twitchtopgames.view.topGames.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.glogachev.twitchtopgames.R
import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var game: GameDB

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        game = args.gameObject
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(game.image).into(binding.detailsImage)
        binding.detailsGameName.text = game.gameName
        binding.detailsViewers.text = getString(R.string.details_viewers, game.viewers)
        binding.detailsChannels.text = getString(R.string.details_channels, game.channels)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}