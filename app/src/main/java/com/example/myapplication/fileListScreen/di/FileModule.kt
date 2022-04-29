package com.example.myapplication.fileListScreen.di

import com.example.myapplication.fileListScreen.data.remoteDataSource.FileRemoteDataSource
import com.example.myapplication.fileListScreen.data.remoteDataSource.FileRemoteDataSourceImp
import com.example.myapplication.fileListScreen.data.repository.FileRepositoryImp
import com.example.myapplication.fileListScreen.domain.repository.FileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FileModule {

    @Binds
    abstract fun bindRemoteDataSource(localDataSourceImp: FileRemoteDataSourceImp): FileRemoteDataSource

    @Binds
    abstract fun bindFileRepository(fileRepositoryImp: FileRepositoryImp): FileRepository
}