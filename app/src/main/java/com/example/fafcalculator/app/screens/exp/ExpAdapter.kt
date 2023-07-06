package com.example.fafcalculator.app.screens.exp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.ExpEntity
import com.example.fafcalculator.databinding.ExpItemBinding

interface Listener {
    fun onClick(exp: ExpEntity)
}

class ExpDiffCallback(
    private val oldList: List<ExpEntity>,
    private val newList: List<ExpEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].titleResId == newList[newItemPosition].titleResId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}

class ExpAdapter(
    var listener: Listener
) : RecyclerView.Adapter<ExpAdapter.ExpHolder>() {

    class ExpHolder(
        private val binding: ExpItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(exp: ExpEntity, listener: Listener) = with(binding) {
            expItem.tag = exp
            im.setImageResource(exp.iconResId)
            tvTitle.setBackgroundResource(exp.factionResId)
            tvTitle.setText(exp.titleResId)
            tvMass.setText(exp.costResId)

            expItem.setOnClickListener { listener.onClick(exp) }
        }
    }

    var expList = listOf<ExpEntity>()
        set(value) {
            val diffCallback = ExpDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExpItemBinding.inflate(inflater, parent, false)

        return ExpHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpHolder, position: Int) {
        holder.onBind(expList[position], listener)
    }

    override fun getItemCount() = expList.size
}