package com.example.coffieshopapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffieshopapp.Repository.CoffeeRepository
import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.data.Model.Coffee
import com.example.coffieshopapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoffeeViewModel(private val repository: CoffeeRepository = CoffeeRepository(RetrofitInstance.api)) : ViewModel() {

    // Hot flow - holds state and survives configuration changes
    private val _coffeeState = MutableStateFlow<StateClass>(StateClass.Idle)
    val coffeeState: StateFlow<StateClass> = _coffeeState.asStateFlow()

    init {
        fetchCoffees()
    }

    fun fetchCoffees() {
        viewModelScope.launch {
            repository.getCoffees().collect { state ->
                _coffeeState.value = state
            }
        }
    }

    // Function to get coffee detail from the existing list without calling API
    fun getCoffeeById(coffeeId: Int): Coffee? {
        val currentState = _coffeeState.value
        return if (currentState is StateClass.Success) {
            currentState.data.find { it.id == coffeeId }
        } else {
            null
        }
    }
}