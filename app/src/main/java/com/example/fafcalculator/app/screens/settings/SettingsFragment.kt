package com.example.fafcalculator.app.screens.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.app.collectFlow
import com.example.fafcalculator.app.model.SacuCost
import com.example.fafcalculator.app.model.Settings
import com.example.fafcalculator.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        config()
    }

    private fun config(){
        collectFlow(viewModel.configState) { config ->

            when (config.sacuCost) {
                SacuCost.MASS_5320 -> binding.radioButton5320.isChecked = true
                SacuCost.MASS_6450 -> binding.radioButton6450.isChecked = true
            }
            binding.radioButton5320.setOnClickListener {
                viewModel.saveCurrentSettings(
                    Settings(
                        sacuIncome = config.sacuIncome,
                        sacuCost = SacuCost.MASS_5320,
                        secMax = config.secMax
                    )
                )
            }

            binding.radioButton6450.setOnClickListener {
                viewModel.saveCurrentSettings(
                    Settings(
                        sacuIncome = config.sacuIncome,
                        sacuCost = SacuCost.MASS_6450,
                        secMax = config.secMax
                    )
                )
            }
        }
    }
}