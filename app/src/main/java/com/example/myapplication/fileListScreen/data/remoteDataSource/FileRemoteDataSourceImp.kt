package com.example.myapplication.fileListScreen.data.remoteDataSource

import com.example.myapplication.app.api.FileApiService
import javax.inject.Inject

class FileRemoteDataSourceImp @Inject constructor(
    private val fileApiService: FileApiService
) : FileRemoteDataSource {
    override fun downloadFile(url: String) =
        fileApiService.downloadFileByUrl(url)
}