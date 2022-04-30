package com.example.myapplication.fileListScreen.presentation.helpers

interface ProgressListener {
    fun onPercentageProgress(progress: Int)
    fun onSizeProgress(fileSize: Double)
    fun onDownloadSuccess(position: Int)
    fun onDownloadFail(position: Int)
}