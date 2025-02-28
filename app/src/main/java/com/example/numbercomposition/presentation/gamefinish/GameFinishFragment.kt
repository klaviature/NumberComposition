package com.example.numbercomposition.presentation.gamefinish

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numbercomposition.databinding.FragmentGameFinishBinding
import com.example.numbercomposition.domain.entities.GameResult

class GameFinishFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        gameResult = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(KEY_GAME_RESULT, GameResult::class.java)
        } else {
            requireArguments().getSerializable(KEY_GAME_RESULT) as? GameResult
        } ?: throw RuntimeException("GameResult is null")
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result_key"

        @JvmStatic
        fun newInstance(gameResult: GameResult) =
            GameFinishFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_GAME_RESULT, gameResult)
                }
            }
    }
}