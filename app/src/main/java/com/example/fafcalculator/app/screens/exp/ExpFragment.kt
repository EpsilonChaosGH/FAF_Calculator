package com.example.fafcalculator.app.screens.exp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.databinding.FragmentExpBinding

class ExpFragment : Fragment(R.layout.fragment_exp) {

    private val binding by viewBinding(FragmentExpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}