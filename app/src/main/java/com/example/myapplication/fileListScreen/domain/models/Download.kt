package com.example.myapplication.fileListScreen.domain.models

import java.io.File

sealed class Download{
    data class Progress(val percent: Int) : Download()
    data class Finished(val file: File) : Download()
    object Fail : Download()
}
