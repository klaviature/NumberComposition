package com.example.numbercomposition.presentation.greeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numbercomposition.R
import com.example.numbercomposition.databinding.FragmentGreetingBinding
import com.example.numbercomposition.presentation.chooselevel.ChooseLevelFragment

class GreetingFragment : Fragment() {

    private var _binding: FragmentGreetingBinding? = null
    private val binding: FragmentGreetingBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startGameButton.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchChooseLevelFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, ChooseLevelFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {

        @JvmStatic
        fun newInstance() = GreetingFragment()
    }
}