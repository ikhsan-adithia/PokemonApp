package com.test.pokemonapp.feature_pokedex.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.test.pokemonapp.R
import com.test.pokemonapp.databinding.FragmentPokedexBinding
import com.test.pokemonapp.core.domain.adapter.PokemonListAdapter
import com.test.pokemonapp.core.presentation.utils.GridDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PokedexFragment: Fragment(R.layout.fragment_pokedex) {

    private var _binding: FragmentPokedexBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonListAdapter

    private val viewModel by viewModels<PokedexViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokedexBinding.inflate(inflater, container, false)
        pokemonAdapter = PokemonListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.apply {
            adapter = pokemonAdapter
            addItemDecoration(GridDecoration(10))
        }

        pokemonAdapter.setOnItemClick {
            findNavController().navigate(R.id.pokemonDetailFragment, bundleOf(
                "isSaved" to true,
                "name" to it.name,
                "nickname" to it.nickname,
            ))
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { pokemons ->
                    pokemonAdapter.differ.submitList(pokemons)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}