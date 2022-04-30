package com.example.myapplication.fileListScreen.domain.models

sealed class Download{
    data class ProgressPercentage(val percent: Int) : Download()
    data class ProgressSize(val fileSize: Double) : Download()
    object Finished : Download()
    object Fail : Download()
}
