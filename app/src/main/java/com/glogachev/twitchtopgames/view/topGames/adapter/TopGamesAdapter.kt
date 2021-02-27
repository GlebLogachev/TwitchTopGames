package com.glogachev.twitchtopgames.view.topGames.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.databinding.RvTopGamesItemsBinding

class TopGamesAdapter() : RecyclerView.Adapter<ViewHolder>() {
    private var gameDB: List<GameDB> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvTopGamesItemsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gameDB[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return gameDB.size
    }

    fun setGameList(gameList: List<GameDB>) {
        gameDB = gameList
        notifyDataSetChanged()
    }
}

class ViewHolder(private val gamesBinding: RvTopGamesItemsBinding) :
    RecyclerView.ViewHolder(gamesBinding.root) {

    fun bind(item: GameDB) {
        gamesBinding.rvNameTv.text = item.gameName
        gamesBinding.rvViewersTv.text = item.viewers
        gamesBinding.rvChannelsTv.text = item.channels
        Glide.with(itemView.context).load(item.image).into(gamesBinding.rvImage)
    }
}
