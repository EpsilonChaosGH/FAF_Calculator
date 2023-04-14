package com.example.fafcalculator.app.screens.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fafcalculator.R
import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.databinding.ResultItemBinding
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(
        val binding: ResultItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var resultList = listOf<Result>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ResultItemBinding.inflate(inflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val result = resultList[position]

        holder.binding.resultViewSacu.text = result.sacu.toString()
        holder.binding.resultViewMassIncome.text = result.massIncome.toString()
        holder.binding.resultViewTime.text = secToDataFormat(result.time.toLong())

        if (result.best) {
            holder.binding.resultItem.setBackgroundResource(R.color.green_aeon)
            holder.binding.timeImageView.setImageResource(R.drawable.ic_star)
        } else {
            holder.binding.resultItem.setBackgroundResource(R.color.trans)
            holder.binding.timeImageView.setImageResource(R.drawable.ic_time)
        }
    }

    private fun secToDataFormat(sec: Long): String {
        return when (sec) {
            in 0..3600 -> SimpleDateFormat("mm:ss", Locale.getDefault()).format(sec * 1000)
            in 3600..Long.MAX_VALUE -> SimpleDateFormat(
                "hh:mm:ss:",
                Locale.getDefault()
            ).format(sec * 1000)
            else -> "..."
        }
    }

    override fun getItemCount() = resultList.size
}