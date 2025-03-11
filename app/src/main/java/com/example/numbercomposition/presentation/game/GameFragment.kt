package com.example.numbercomposition.presentation.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.numbercomposition.R
import com.example.numbercomposition.databinding.FragmentGameBinding
import com.example.numbercomposition.domain.entities.GameResult
import com.example.numbercomposition.domain.entities.GameResultGrade
import com.example.numbercomposition.domain.entities.GameSettings
import com.example.numbercomposition.domain.entities.Level
import com.example.numbercomposition.domain.entities.OptionColor
import com.example.numbercomposition.presentation.gamefinish.GameFinishFragment

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null

    private lateinit var viewModel: GameViewModel
    private val binding: FragmentGameBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.startGame(level)

        observeViewModel()

        setOptionClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        with(binding) {
            viewModel.sum.observe(viewLifecycleOwner) { sum ->
                sumTextView.text = sum.toString()
            }
            viewModel.visibleNumber.observe(viewLifecycleOwner) { visibleNumber ->
                visibleNumberTextView.text = visibleNumber.toString()
            }
            viewModel.options.observe(viewLifecycleOwner) { options ->
                firstOptionTextView.text = options[0].toString()
                secondOptionTextView.text = options[1].toString()
                thirdOptionTextView.text = options[2].toString()
                fourthOptionTextView.text = options[3].toString()
                fifthOptionTextView.text = options[4].toString()
                sixthOptionTextView.text = options[5].toString()
            }
            viewModel.optionColors.observe(viewLifecycleOwner) { colors ->
                setOptionsColors(colors)
            }
            viewModel.timeRemainingFormatted.observe(viewLifecycleOwner) { time ->
                timerTextView.text = time
            }
            viewModel.timeRemainingProgress.observe(viewLifecycleOwner) { progress ->
                timerProgressBar.progress = progress
            }
            viewModel.answersProgress.observe(viewLifecycleOwner) { progress ->
                answersPercentageProgressBar.progress = progress
            }
            viewModel.minRightAnswersProgress.observe(viewLifecycleOwner) { progress ->
                answersPercentageProgressBar.secondaryProgress = progress
            }
        }
        viewModel.isGameFinished.observe(viewLifecycleOwner) {
            launchGameFinishFragment(
                GameResult(
                    true,
                    0,
                    0,
                    GameResultGrade.LOST,
                    GameSettings(0, 0, 0.0, 0)
                )
            )
        }

        viewModel.answersCount.observe(viewLifecycleOwner) { count ->
            Log.d("GameFragment", "Answers count: $count")
        }
        viewModel.rightAnswersCount.observe(viewLifecycleOwner) { count ->
            Log.d("GameFragment", "Right answers count: $count")
        }
    }

    private fun setOptionsColors(colors: List<OptionColor>) {
        binding.firstOptionCardView.setCardBackgroundColor(getColor(colors[0]))
        binding.secondOptionCardView.setCardBackgroundColor(getColor(colors[1]))
        binding.thirdOptionCardView.setCardBackgroundColor(getColor(colors[2]))
        binding.fourthOptionCardView.setCardBackgroundColor(getColor(colors[3]))
        binding.fifthOptionCardView.setCardBackgroundColor(getColor(colors[4]))
        binding.sixthOptionCardView.setCardBackgroundColor(getColor(colors[5]))
    }

    private fun getColor(optionColor: OptionColor): Int {
        return ContextCompat.getColor(requireContext(), optionColor.color)
    }

    private fun setOptionClickListeners() {
        with(binding) {
            firstOptionTextView.setOnClickListener {
                viewModel.giveAnswer(firstOptionTextView.text.toString())
            }
            secondOptionTextView.setOnClickListener {
                viewModel.giveAnswer(secondOptionTextView.text.toString())
            }
            thirdOptionTextView.setOnClickListener {
                viewModel.giveAnswer(thirdOptionTextView.text.toString())
            }
            fourthOptionTextView.setOnClickListener {
                viewModel.giveAnswer(fourthOptionTextView.text.toString())
            }
            fifthOptionTextView.setOnClickListener {
                viewModel.giveAnswer(fifthOptionTextView.text.toString())
            }
            sixthOptionTextView.setOnClickListener {
                viewModel.giveAnswer(sixthOptionTextView.text.toString())
            }
        }
    }

    private fun launchGameFinishFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, GameFinishFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun parseArgs() {
        level = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_LEVEL, Level::class.java)
        } else {
            requireArguments().getParcelable(KEY_LEVEL)
        } ?: throw RuntimeException("Level is null")
    }

    companion object {

        private const val KEY_LEVEL = "level_key"

        @JvmStatic
        fun newInstance(level: Level) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
    }
}