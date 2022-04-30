package com.example.myapplication.fileListScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fileListScreen.domain.models.Download
import com.example.myapplication.fileListScreen.domain.models.FileModel
import com.example.myapplication.fileListScreen.domain.repository.FileRepository
import com.example.myapplication.fileListScreen.presentation.helpers.ProgressListener
import com.example.myapplication.utils.downloadToFileWithProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.*
import javax.inject.Inject

@HiltViewModel
class FileListViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _filesList = MutableLiveData<ArrayList<FileModel>>()
    val filesList: LiveData<ArrayList<FileModel>>
        get() = _filesList

    fun setFileListValues(list: ArrayList<FileModel>) {
        _filesList.postValue(list)
    }

    fun loadFile(position: Int, externalDirectory: File, listener: ProgressListener) {
        viewModelScope.launch {
            val url = _filesList.value?.get(position)?.url
            url?.let {
                fileRepository.downloadFile(url).enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        viewModelScope.launch {
                            response.body()?.downloadToFileWithProgress(
                                externalDirectory, _filesList.value?.get(position)?.name ?: "name"
                            )?.collect { downloadProgress ->
                                when (downloadProgress) {
                                    is Download.ProgressPercentage -> {
                                        listener.onPercentageProgress(downloadProgress.percent)
                                    }
                                    is Download.ProgressSize -> {
                                        listener.onSizeProgress(downloadProgress.fileSize)
                                    }
                                    is Download.Fail -> {
                                        listener.onDownloadFail(position)
                                    }
                                    is Download.Finished -> {
                                        listener.onDownloadSuccess(position)
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        listener.onDownloadFail(position)
                    }
                })
            }
        }
    }
}