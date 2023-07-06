package com.example.fafcalculator.app.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fafcalculator.app.model.Config
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.app.model.ResultState
import com.example.fafcalculator.app.model.Settings
import com.example.fafcalculator.core_data.Repository
import com.example.fafcalculator.core_data.mappers.toResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _resultState = MutableLiveData<List<ResultState>>()
    val resultState: LiveData<List<ResultState>> = _resultState

    private val _configState = MutableLiveData<Config>()
    val configState: LiveData<Config> = _configState

    init {
        viewModelScope.launch {
            listenCurrentResult()
        }

        viewModelScope.launch {
            listenCurrentConfig()
        }
    }

    private suspend fun listenCurrentResult() {
        repository.getResultFlow().collect { results ->
            _resultState.value = results.map { it.toResultState() }
        }
    }

    private fun listenCurrentConfig() {
        viewModelScope.launch {
            repository.getConfigFlow().collect() {
                _configState.value = it
            }
        }
    }

    fun saveCurrentParams(params: Params) {
        viewModelScope.launch {
            repository.setCurrentParams(params)
        }
    }

    fun saveCurrentSettings(settings: Settings) {
        viewModelScope.launch {
            repository.setCurrentSettings(settings)
        }
    }
}