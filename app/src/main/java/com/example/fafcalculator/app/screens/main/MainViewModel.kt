package com.example.fafcalculator.app.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fafcalculator.app.model.MainState
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.core_data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state

    init {
        viewModelScope.launch {
            listenCurrentResult()
        }
    }

    private suspend fun listenCurrentResult() {
        repository.listenCurrentResult().collect { mainState ->
            _state.value = mainState
        }
    }

    fun saveCurrentParams(params: Params) {
        viewModelScope.launch {
            repository.setCurrentParams(params)
        }
    }
}