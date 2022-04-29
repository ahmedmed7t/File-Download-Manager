package com.example.myapplication.fileListScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.fileListScreen.domain.models.FileModel
import com.example.myapplication.fileListScreen.domain.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FileListViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _filesList = MutableLiveData<ArrayList<FileModel>>()
    val filesList: LiveData<ArrayList<FileModel>>
        get() = _filesList


    fun setFileListValues(list: ArrayList<FileModel>){
        _filesList.postValue(list)
    }
}