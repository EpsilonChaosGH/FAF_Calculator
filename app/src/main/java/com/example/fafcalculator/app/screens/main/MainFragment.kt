package com.example.fafcalculator.app.screens.main

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.Exp
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.app.model.Settings
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
        addMenu()
        observeState()
        binding.editTextMassCost.addTextChangedListener(textWatcher)
        binding.editTextMassIncome.addTextChangedListener(textWatcher)
    }

    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
//                    R.id.actionSearch -> {
//                        if (binding.cityTextInput.visibility == View.VISIBLE) {
//                            binding.cityTextInput.visibility = View.GONE
//                            hideKeyboardFrom(binding.cityEditText)
//                        } else {
//                            binding.cityTextInput.visibility = View.VISIBLE
//                            showSoftKeyboard(binding.cityEditText)
//                        }
//                        true
//                    }
//                    R.id.actionLocation -> {
//                        getWeatherByCoordinates()
//                        true
//                    }
//                    else -> false
//                }
               // Toast.makeText(requireContext(), "!!!", Toast.LENGTH_SHORT).show()
                settingsDialog()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun settingsDialog() {
        val config = viewModel.state.value!!.config

        val currentIndex = when(config.sacuCost){
            6450 -> 0
            5320 -> 1
            else -> 0
        }
        val list = listOf(6450, 5320)
        val costItems = list
            .map { "value = $it" }
            .toTypedArray()

        val dialog2 = AlertDialog.Builder(requireContext())
            .setTitle("Sacu mass coast")
            .setSingleChoiceItems(costItems, currentIndex) { dialog, witch ->
                dialog.dismiss()
                viewModel.saveCurrentSettings(
                    Settings(
                        sacuIncome = config.sacuIncome,
                        sacuCost = list[witch],
                        secMax = config.secMax
                    )
                )
            }
            .create()
        dialog2.show()
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

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboardFrom(view: View?) {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}