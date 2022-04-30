package com.example.myapplication.utils

import com.example.myapplication.fileListScreen.domain.models.Download
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat

fun ResponseBody.downloadToFileWithProgress(directory: File, filename: String): Flow<Download> =
    flow {
        emit(Download.ProgressPercentage(0))

        val file = File(directory, "${filename}.${contentType()?.subtype}")
        byteStream().use { inputStream ->
            file.outputStream().use { outputStream ->
                val totalBytes = contentLength()
                val data = ByteArray(8_192)
                var progressBytes = 0L
                while (true) {
                    val bytes = inputStream.read(data)
                    if (bytes == -1) {
                        break
                    }

                    outputStream.channel
                    outputStream.write(data, 0, bytes)
                    progressBytes += bytes

                    if (totalBytes == -1) {
                        // file size unknown
                        emit(Download.ProgressSize((progressBytes.toDouble() / (1024L * 1024L)).roundOffDecimal()))
                    } else {
                        emit(Download.ProgressPercentage(percent = ((progressBytes * 100) / totalBytes).toInt()))
                    }
                }
            }
        }
        emit(Download.Finished)
    }.flowOn(Dispatchers.IO)
        .distinctUntilChanged()

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}