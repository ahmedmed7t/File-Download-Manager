package com.example.myapplication.fileListScreen.domain.repository

import retrofit2.Call
import okhttp3.ResponseBody

interface FileRepository {
    fun downloadFile(url: String): Call<ResponseBody>
}