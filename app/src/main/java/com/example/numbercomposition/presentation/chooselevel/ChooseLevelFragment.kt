package com.example.numbercomposition.presentation.chooselevel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numbercomposition.R
import com.example.numbercomposition.databinding.FragmentChooseLevelBinding
import com.example.numbercomposition.domain.entities.Level
import com.example.numbercomposition.presentation.game.GameFragment

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            testLevelCardView.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            easyLevelCardView.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            normalLevelCardView.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            hardLevelCardView.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_fragment_container, GameFragment.newInstance(level))
            .commit()
    }

    companion object {

        @JvmStatic
        fun newInstance() = ChooseLevelFragment()
    }
}