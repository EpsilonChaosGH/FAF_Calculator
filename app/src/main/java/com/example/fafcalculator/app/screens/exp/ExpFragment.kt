package com.example.fafcalculator.app.screens.exp

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.Exp
import com.example.fafcalculator.app.model.ExpEntity
import com.example.fafcalculator.databinding.FragmentExpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpFragment : Fragment(R.layout.fragment_exp) {

    private val binding by viewBinding(FragmentExpBinding::bind)

    private val viewModel by viewModels<ExpViewModel>()

    private val adapter by lazy {
        ExpAdapter(object : Listener {
            override fun onClick(exp: ExpEntity) {
                viewModel.setCost(getString(exp.coastResId).toInt())
                findNavController().popBackStack()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayoutManager(binding, adapter)
        adapter.expList = Exp.getList()
    }

    private fun setupLayoutManager(binding: FragmentExpBinding, adapter: ExpAdapter) {
        binding.rcView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rcView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = binding.rcView.width
                val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width)
                val columns = width / itemWidth
                binding.rcView.adapter = adapter
                binding.rcView.layoutManager = GridLayoutManager(requireContext(), columns)
            }
        })
    }
}