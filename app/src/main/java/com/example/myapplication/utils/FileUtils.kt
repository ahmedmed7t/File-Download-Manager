package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.fileListScreen.domain.models.FileModel
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

object FileUtils {
    fun loadJSONFromAsset(context: Context) =
        try {
            val inputStream: InputStream = context.assets.open("getListOfFilesResponse.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }

    fun convertJsonToList(jsonString: String) =
        ArrayList(Gson().fromJson(jsonString, Array<FileModel>::class.java).asList())
}