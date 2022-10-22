package com.test.pokemonapp.feature_pokemon_detail.presentation

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.test.pokemonapp.R
import com.test.pokemonapp.core.presentation.utils.loadImage
import com.test.pokemonapp.databinding.DialogGiveNicknameBinding
import com.test.pokemonapp.databinding.FragmentPokemonDetailBinding
import com.test.pokemonapp.feature_pokemon_detail.domain.adapter.StatAdapter
import com.test.pokemonapp.feature_pokemon_detail.domain.adapter.TypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment: Fragment(R.layout.fragment_pokemon_detail) {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var statAdapter: StatAdapter
    private lateinit var typeAdapter: TypeAdapter

    private val viewModel by viewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        statAdapter = StatAdapter()
        typeAdapter = TypeAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            arguments?.getBoolean("isSaved", false)?.let { saved ->
                if (saved) {
                    btnRelease.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            viewModel.releasePokemon()
                            Toast.makeText(requireContext(), "Goodbye ${viewModel.state.value?.nickname ?: viewModel.state.value?.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    btnCatch.visibility = View.GONE
                } else {
                    btnRelease.visibility = View.GONE
                    btnCatch.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            viewModel.catchPokemon()
                        }
                    }
                }
            }

            rvType.apply {
                adapter = typeAdapter
            }
            rvStat.apply {
                adapter = statAdapter
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.state.collectLatest {
                            it?.let { pokemon ->
                                tvPokemonName.text = pokemon.nickname ?: pokemon.name
                                imvSprite.loadImage(pokemon.sprite)
                                typeAdapter.differ.submitList(pokemon.types)
                                statAdapter.differ.submitList(pokemon.stats)
                            }
                        }
                    }

                    launch {
                        viewModel.uiEvent.collectLatest {
                            if (it.contains("caught", true)) {
                                showDialogOption()
                            }
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showDialogOption() {
        val viewBinding = DialogGiveNicknameBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(viewBinding.root)
        }.create()

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        viewBinding.btnConfirm.setOnClickListener {
            viewModel.saveToPokedex(viewBinding.etNickname.text.toString())
            dialog.dismiss()
        }

        viewBinding.btnNo.setOnClickListener {
            viewModel.saveToPokedex(viewBinding.etNickname.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}