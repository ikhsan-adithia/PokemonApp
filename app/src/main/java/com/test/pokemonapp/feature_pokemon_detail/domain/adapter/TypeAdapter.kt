package com.test.pokemonapp.feature_pokemon_detail.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.pokemonapp.databinding.ItemTypeBinding
import com.test.pokemonapp.feature_pokemon_detail.domain.models.Type

class TypeAdapter: RecyclerView.Adapter<TypeViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Type>() {
        override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder =
        TypeViewHolder(ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(differ.currentList[holder.adapterPosition])
    }

    override fun getItemCount(): Int = differ.currentList.size
}

class TypeViewHolder(private val binding: ItemTypeBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(type: Type) {
        with(binding) {
            tvTypeName.text = type.name
            tvTypeName.backgroundTintList = ContextCompat.getColorStateList(tvTypeName.context, type.color)
        }
    }
}