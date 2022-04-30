package com.example.myapplication.app.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface FileApiService {
    @Streaming
    @GET
    fun downloadFileByUrl(@Url fileUrl: String): ResponseBody
}