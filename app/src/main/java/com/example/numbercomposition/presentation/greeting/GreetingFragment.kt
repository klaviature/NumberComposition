package com.example.numbercomposition.presentation.greeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numbercomposition.R
import com.example.numbercomposition.databinding.FragmentGreetingBinding

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

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = GreetingFragment()
    }
}