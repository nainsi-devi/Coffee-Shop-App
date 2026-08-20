package com.example.coffieshopapp.Repository

import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CoffeeRepository(private val apiService: ApiService) {

    /**
     * Cold flow - starts emitting when collected.
     * Manages state transitions and error handling for API calls.
     */
    fun getCoffees(): Flow<StateClass> = flow {
        emit(StateClass.Loading)
        try {
            val response = apiService.getCoffees()
            // Fix: response.success is Boolean?, so we compare with true
            if (response.success == true) {
                emit(StateClass.Success(response.data ?: emptyList()))
            } else {
                emit(StateClass.Error(response.message ?: "Failed to fetch coffee list"))
            }
        } catch (e: IOException) {
            // Handle network failure (e.g., no internet, timeout)
            emit(StateClass.Error("Network Error: Please check your internet connection."))
        } catch (e: HttpException) {
            // Handle HTTP errors (e.g., 404, 500)
            val errorMessage = when (e.code()) {
                404 -> "Resource not found"
                500 -> "Internal server error"
                else -> "Server error: ${e.code()}"
            }
            emit(StateClass.Error(errorMessage))
        } catch (e: Exception) {
            // Handle any other unexpected exceptions
            emit(StateClass.Error(e.message ?: "An unknown error occurred"))
        }
    }
}