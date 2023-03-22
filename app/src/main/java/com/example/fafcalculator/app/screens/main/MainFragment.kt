package com.example.fafcalculator.app.screens.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.Exp
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

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
        binding.editTextMassCost.addTextChangedListener(textWatcher)
        binding.editTextMassIncome.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (binding.editTextMassIncome.text.isNotEmpty() && binding.editTextMassCost.text.isNotEmpty()) {
                if (binding.editTextMassIncome.text.toString()
                        .toInt() > 0 && binding.editTextMassCost.text.toString().toInt() > 0
                )
                    if (binding.editTextMassIncome.text.toString() != viewModel.state.value!!.config.massIncome.toString()
                        || binding.editTextMassCost.text.toString() != viewModel.state.value!!.config.massCost.toString()
                    ) {
                        viewModel.saveCurrentParams(
                            Params(
                                massCost = binding.editTextMassCost.text.toString().toInt(),
                                massIncome = binding.editTextMassIncome.text.toString().toInt()
                            )
                        )
                    }
            }
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            adapter.resultList = state.resultList
            binding.imageViewMenu.setImageResource(Exp.findImageByCoast(state.config.massCost))

            if (binding.editTextMassIncome.text.toString() != state.config.massIncome.toString()
                || binding.editTextMassCost.text.toString() != state.config.massCost.toString()
            ) {
                binding.editTextMassCost.setText(state.config.massCost.toString())
                binding.editTextMassIncome.setText(state.config.massIncome.toString())
            }
        }
    }

    private fun openExp() {
        findNavController().navigate(R.id.action_mainFragment_to_expFragment)
    }
}