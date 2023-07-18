package com.example.fafcalculator.app.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fafcalculator.app.model.Config
import com.example.fafcalculator.app.model.Settings
import com.example.fafcalculator.core_data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _configState = MutableStateFlow<Config?>(null)
    val configState: StateFlow<Config?> = _configState.asStateFlow()

    init {
        viewModelScope.launch {
            listenCurrentConfig()
        }
    }

    private fun listenCurrentConfig() {
        viewModelScope.launch {
            repository.getConfigFlow().collect() {
                _configState.value = it
            }
        }
    }

    fun saveCurrentSettings(settings: Settings) {
        viewModelScope.launch {
            repository.setCurrentSettings(settings)
        }
    }
}