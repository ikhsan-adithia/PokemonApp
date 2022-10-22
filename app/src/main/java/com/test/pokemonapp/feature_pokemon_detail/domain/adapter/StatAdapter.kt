package com.test.pokemonapp.feature_pokemon_detail.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.pokemonapp.databinding.ItemStatBinding
import com.test.pokemonapp.feature_pokemon_detail.domain.models.Stat

class StatAdapter: RecyclerView.Adapter<StatViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Stat>() {
        override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder =
        StatViewHolder(ItemStatBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(differ.currentList[holder.adapterPosition])
    }

    override fun getItemCount(): Int = differ.currentList.size
}

class StatViewHolder(private val binding: ItemStatBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(stat: Stat) {
        with(binding) {
            tvStat.text = stat.name
            val progressText = "${stat.currentProgress}/${stat.maxProgress}"
            tvProgress.text = progressText
            progress.max = stat.maxProgress
            progress.progress = stat.currentProgress
        }
    }
}