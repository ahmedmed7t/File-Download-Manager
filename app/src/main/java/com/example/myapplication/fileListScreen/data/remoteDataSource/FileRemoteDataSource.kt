package com.example.myapplication.fileListScreen.data.remoteDataSource

import okhttp3.ResponseBody
import retrofit2.Call

interface FileRemoteDataSource {
    fun downloadFile(url: String): Call<ResponseBody>
}