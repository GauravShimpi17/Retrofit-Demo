package com.example.retrofitdemo.retrofit

import com.example.retrofitdemo.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi
{
    @GET(value = "/todos")
    suspend fun getData() : Response<List<Todo>>
}