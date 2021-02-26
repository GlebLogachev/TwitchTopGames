package com.glogachev.twitchtopgames.view.topGames.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glogachev.twitchtopgames.R
import com.glogachev.twitchtopgames.data.db.GameDB

class TopGamesAdapter(private val gameDB: List<GameDB>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_top_games_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = gameDB[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return gameDB.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: GameDB) {
        val gameName = itemView.findViewById<TextView>(R.id.rv_name_tv)
        val gameViewers = itemView.findViewById<TextView>(R.id.rv_viewers_tv)
        val gameChannels = itemView.findViewById<TextView>(R.id.rv_channels_tv)
        val gameImage = itemView.findViewById<ImageView>(R.id.rv_image)

        gameName.text = item.gameName
        gameViewers.text = item.viewers
        gameChannels.text = item.channels

        Glide.with(itemView.context).load(item.image).into(gameImage)
    }
}
