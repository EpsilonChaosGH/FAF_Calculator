package com.example.fafcalculator.app.screens.exp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fafcalculator.core_data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun setCost(cost: Int) {
        viewModelScope.launch {
            repository.setCost(cost)
        }
    }
}