package com.example.myapplication.fileListScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fileListScreen.domain.models.Download
import com.example.myapplication.fileListScreen.domain.models.FileModel
import com.example.myapplication.fileListScreen.domain.repository.FileRepository
import com.example.myapplication.utils.downloadToFileWithProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileListViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _filesList = MutableLiveData<ArrayList<FileModel>>()
    val filesList: LiveData<ArrayList<FileModel>>
        get() = _filesList


    private val _downloadProgress = MutableLiveData<Download>()
    val downloadProgress: LiveData<Download>
        get() = _downloadProgress

    fun setFileListValues(list: ArrayList<FileModel>) {
        _filesList.postValue(list)
    }


    fun loadFile(position: Int, externalDirectory: File) {
        viewModelScope.launch {
            val url = _filesList.value?.get(position)?.url

            url?.let {
                fileRepository.downloadFile(url).downloadToFileWithProgress(
                    externalDirectory, _filesList.value?.get(position)?.name ?: "name"
                ).collect {  download ->
                    _downloadProgress.postValue(download)
                }
            }
        }

    }
}


