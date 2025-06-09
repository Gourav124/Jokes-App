package com.example.jokesapp.data

import retrofit2.http.GET

interface ApiService {
    @GET("random_joke")
    suspend fun getJokes(): Jokes
}