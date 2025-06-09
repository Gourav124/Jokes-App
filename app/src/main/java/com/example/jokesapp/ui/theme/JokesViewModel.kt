package com.example.jokesapp.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesapp.data.Jokes
import com.example.jokesapp.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class JokesViewModel : ViewModel() {
    private val _jokes = MutableStateFlow<Jokes?>(null)
    val jokes: StateFlow<Jokes?> = _jokes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchJokes() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                Log.d("JokesViewModel", "Fetching joke...")
                val joke = RetrofitInstance.api.getJokes()
                Log.d("JokesViewModel", "Received joke: ${joke.setup}")
                _jokes.value = joke
            } catch (e: Exception) {
                Log.e("JokesViewModel", "Error fetching joke", e)
                _error.value = when (e) {
                    is UnknownHostException -> "No internet connection. Please check your network and try again."
                    else -> "Something went wrong. Please try again."
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}