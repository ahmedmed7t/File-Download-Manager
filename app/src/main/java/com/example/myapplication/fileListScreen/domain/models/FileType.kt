package com.example.myapplication.fileListScreen.domain.models

import com.google.gson.annotations.SerializedName

enum class FileType(extention: String) {
    @SerializedName("VIDEO")
    VIDEO("mp4"),
    @SerializedName("PDF")
    PDF("pdf")
}