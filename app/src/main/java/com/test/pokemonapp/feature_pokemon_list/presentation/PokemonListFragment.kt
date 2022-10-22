package com.test.pokemonapp.feature_pokemon_list.presentation

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
import com.test.pokemonapp.core.presentation.utils.GridDecoration
import com.test.pokemonapp.core.presentation.utils.listenForPagination
import com.test.pokemonapp.databinding.FragmentPokemonListBinding
import com.test.pokemonapp.core.domain.adapter.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PokemonListFragment: Fragment(R.layout.fragment_pokemon_list) {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonListAdapter

    private val viewModel by viewModels<PokemonListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        pokemonAdapter = PokemonListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rv.apply {
                adapter = pokemonAdapter
                addItemDecoration(GridDecoration(10))
                listenForPagination {
                    viewModel.loadNextPage()
                }
            }

            btnPokedex.setOnClickListener {
                findNavController().navigate(R.id.pokedexFragment)
            }

            pokemonAdapter.setOnItemClick {
                findNavController().navigate(R.id.pokemonDetailFragment, bundleOf(
                    "name" to it.name,
                    "sprite" to it.sprite,
                ))
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collectLatest { state ->
                        pokemonAdapter.differ.submitList(state.pokemons)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}