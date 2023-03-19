package com.example.fafcalculator.app.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment() : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val adapter by lazy(mode = LazyThreadSafetyMode.NONE) { MainAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerViewResult.adapter = adapter
            recyclerViewResult.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            imageViewMenu.setOnClickListener {
                openExp()
            }
            recyclerViewResult.adapter
        }
        observeState()
    }

    private fun observeState(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            adapter.resultList = state
        }
    }

    private fun openExp() {
        findNavController().navigate(R.id.action_mainFragment_to_expFragment)

    }
}