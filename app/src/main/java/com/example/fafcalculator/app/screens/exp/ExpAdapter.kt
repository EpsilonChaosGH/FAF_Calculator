package com.example.fafcalculator.app.screens.exp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.ExpEntity
import com.example.fafcalculator.databinding.ExpItemBinding

interface Listener {
    fun onClick(exp: ExpEntity)
}

class ExpAdapter(
    var listener: Listener
) : RecyclerView.Adapter<ExpAdapter.ExpHolder>(), View.OnClickListener {

    class ExpHolder(
        val binding: ExpItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var expList = listOf<ExpEntity>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val exp = v.tag as ExpEntity

        when (v.id) {
            R.id.expItem -> listener.onClick(exp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExpItemBinding.inflate(inflater, parent, false)

        binding.expItem.setOnClickListener(this)

        return ExpHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpHolder, position: Int) {
        val exp = expList[position]

        with(holder.binding) {
            expItem.tag = exp
            im.setImageResource(exp.iconResId)
            tvTitle.setBackgroundResource(exp.factionResId)
            tvTitle.setText(exp.titleResId)
            tvMass.setText(exp.costResId)
        }

    }

    override fun getItemCount() = expList.size
}
