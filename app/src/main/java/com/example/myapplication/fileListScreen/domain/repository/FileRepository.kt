package com.example.myapplication.fileListScreen.domain.repository

import okhttp3.ResponseBody

interface FileRepository {
    fun downloadFile(url: String): ResponseBody
}