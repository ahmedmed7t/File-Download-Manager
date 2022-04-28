package com.example.myapplication.fileListScreen.presentation

import androidx.lifecycle.ViewModel
import com.example.myapplication.fileListScreen.domain.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FileListViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {

}