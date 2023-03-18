package com.example.fafcalculator.app.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.databinding.FragmentMainBinding

class MainFragment() : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewMenu.setOnClickListener {
            openExp()
        }
    }

    private fun openExp() {
        findNavController().navigate(R.id.action_mainFragment_to_expFragment)

    }
}