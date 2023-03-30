package com.example.myapplication.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.example.myapplication.R
import com.example.myapplication.databinding.SwipeItemBinding

class SwipeSongAdapter : BaseSongAdapter(R.layout.swipe_item) {

    lateinit var binding : SwipeItemBinding
    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.itemView.apply {
            val text = "${song.title} - ${song.subtitle}"
            binding.tvPrimary.text = text
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }
}
