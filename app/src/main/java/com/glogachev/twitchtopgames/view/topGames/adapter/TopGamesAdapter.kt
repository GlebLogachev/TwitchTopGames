package com.glogachev.twitchtopgames.view.topGames.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.databinding.RvTopGamesItemsBinding

interface OnItemClickListener {
    fun onClick(game: GameDB)
}

class TopGamesAdapter : ListAdapter<GameDB, GamesViewHolder>(topGamesDiffCallback) {
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvTopGamesItemsBinding.inflate(layoutInflater, parent, false)
        return GamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

class GamesViewHolder(private val gamesBinding: RvTopGamesItemsBinding) :
    RecyclerView.ViewHolder(gamesBinding.root) {

    fun bind(item: GameDB, listener: OnItemClickListener) {
        gamesBinding.rvNameTv.text = item.gameName
        gamesBinding.rvViewersTv.text = item.viewers
        gamesBinding.rvChannelsTv.text = item.channels
        Glide.with(itemView.context).load(item.image).into(gamesBinding.rvImage)

        gamesBinding.gamesCardView.setOnClickListener {
            listener.onClick(item)
        }
    }
}

val topGamesDiffCallback = object : DiffUtil.ItemCallback<GameDB>() {
    override fun areItemsTheSame(oldItem: GameDB, newItem: GameDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameDB, newItem: GameDB): Boolean {
        return oldItem == newItem
    }
}
