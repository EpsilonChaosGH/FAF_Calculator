package com.example.fafcalculator.app.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.core_data.Repository
import com.example.fafcalculator.core_data.mappers.toParams
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<List<Result>>()
    val state : LiveData<List<Result>> = _state

    init {
        viewModelScope.launch {
            Log.e("aaa","l")
            listenCurrentResult()
        }
    }

    private suspend fun listenCurrentResult(){
        repository.listenCurrentResult().collect{ result ->
            _state.value = result
        }
    }



}