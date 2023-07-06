package com.example.fafcalculator.app.screens.main

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
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
import com.example.fafcalculator.app.model.ExpState
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
        }
        addMenu()
        observeConfig()
        observeResult()
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
                settingsDialog()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    fun settingsDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_settings, null)

        val radioButton5320: RadioButton = view.findViewById(R.id.radioButton5320)
        val radioButton6450: RadioButton = view.findViewById(R.id.radioButton6450)

        viewModel.configState.observe(viewLifecycleOwner) { config ->

            when (config.sacuCost) {
                5320 -> radioButton5320.isChecked = true
                6450 -> radioButton6450.isChecked = true
            }
            radioButton5320.setOnClickListener {
                viewModel.saveCurrentSettings(
                    Settings(
                        sacuIncome = config.sacuIncome,
                        sacuCost = 5320,
                        secMax = config.secMax
                    )
                )
            }

            radioButton6450.setOnClickListener {
                viewModel.saveCurrentSettings(
                    Settings(
                        sacuIncome = config.sacuIncome,
                        sacuCost = 6450,
                        secMax = config.secMax
                    )
                )
            }
        }
        val listener = DialogInterface.OnClickListener { _, _ -> }
        val builder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
            .setPositiveButton(R.string.ok, listener)
            .create()
        builder.setView(view)
        builder.show()
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (binding.editTextMassIncome.text.isNotEmpty() && binding.editTextMassCost.text.isNotEmpty()) {
                if (binding.editTextMassIncome.text.toString()
                        .toInt() > 0 && binding.editTextMassCost.text.toString().toInt() > 0
                )
                    if (binding.editTextMassIncome.text.toString() != viewModel.configState.value!!.massIncome.toString()
                        || binding.editTextMassCost.text.toString() != viewModel.configState.value!!.massCost.toString()
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

    private fun observeResult() {
        viewModel.resultState.observe(viewLifecycleOwner) { state ->
            adapter.resultList = state
        }
    }

    private fun observeConfig() {
        viewModel.configState.observe(viewLifecycleOwner) { config ->
            binding.imageViewMenu.setImageResource(ExpState.findImageByCoast(config.massCost))

            if (binding.editTextMassIncome.text.toString() != config.massIncome.toString()
                || binding.editTextMassCost.text.toString() != config.massCost.toString()
            ) {
                binding.editTextMassCost.setText(config.massCost.toString())
                binding.editTextMassIncome.setText(config.massIncome.toString())
            }
        }
    }

    private fun openExp() {
        activity?.let { hideKeyboardFrom(view) }
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