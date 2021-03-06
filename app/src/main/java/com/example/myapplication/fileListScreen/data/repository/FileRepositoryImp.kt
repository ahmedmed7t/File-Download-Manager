package com.example.myapplication.fileListScreen.data.repository

import com.example.myapplication.fileListScreen.data.remoteDataSource.FileRemoteDataSource
import com.example.myapplication.fileListScreen.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImp @Inject constructor(
    private val fileRemoteDataSource: FileRemoteDataSource
) : FileRepository {
    override fun downloadFile(url: String) =
        fileRemoteDataSource.downloadFile(url)
}