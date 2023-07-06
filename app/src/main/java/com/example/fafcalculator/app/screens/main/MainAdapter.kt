package com.example.fafcalculator.app.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.ResultState
import com.example.fafcalculator.databinding.ResultItemBinding

class MainDiffCallback(
    private val oldList: List<ResultState>,
    private val newList: List<ResultState>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].sacu == newList[newItemPosition].sacu
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(
        private val binding: ResultItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(result: ResultState) = with(binding) {
            resultViewSacu.text = result.sacu
            resultViewMassIncome.text = result.massIncome
            resultViewTime.text = result.time

            if (result.best) {
                resultItem.setBackgroundResource(R.color.green_aeon)
                timeImageView.setImageResource(R.drawable.ic_star)
            } else {
                resultItem.setBackgroundResource(R.color.trans)
                timeImageView.setImageResource(R.drawable.ic_time)
            }
        }
    }

    var resultList = listOf<ResultState>()
        set(value) {
            val diffCallback = MainDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ResultItemBinding.inflate(inflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(resultList[position])
    }

    override fun getItemCount() = resultList.size
}