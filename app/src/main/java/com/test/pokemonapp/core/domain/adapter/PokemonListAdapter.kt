package com.test.pokemonapp.core.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.pokemonapp.core.presentation.utils.loadImage
import com.test.pokemonapp.databinding.ItemPokemonBinding
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem

class PokemonListAdapter: RecyclerView.Adapter<PokemonListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PokemonItem>() {
        override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder =
        PokemonListViewHolder(ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = differ.currentList[holder.adapterPosition]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onItemClick?.let { it(pokemon) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClick: ((PokemonItem) -> Unit)? = null
    fun setOnItemClick(listener: (PokemonItem) -> Unit) {
        onItemClick = listener
    }
}

class PokemonListViewHolder(private val binding: ItemPokemonBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: PokemonItem) {
        with(binding) {
            tvName.text = pokemon.nickname ?: pokemon.name
            imvSprite.loadImage(pokemon.sprite)
        }
    }
}