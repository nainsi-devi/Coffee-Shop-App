package com.example.coffieshopapp

import com.example.coffieshopapp.data.Model.Coffee

sealed class StateClass {
    data object Idle : StateClass()
    data object Loading : StateClass()
    data class Success(val data: List<Coffee>) : StateClass()
    data class Error(val message: String) : StateClass()
}
