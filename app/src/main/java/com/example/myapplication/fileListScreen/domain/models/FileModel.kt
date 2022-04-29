package com.example.myapplication.fileListScreen.domain.models

data class FileModel(
    val id: Int,
    val type: FileType,
    val url: String,
    val name: String
)