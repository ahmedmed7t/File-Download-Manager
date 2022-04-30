package com.example.myapplication.fileListScreen.presentation.downloadDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.myapplication.databinding.DownloadDialogViewBinding

class DownloadDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DownloadDialogViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DownloadDialogViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialog()
    }

    fun setDownloadPercentageProgress(progress: Int) {
        binding.downloadDialogProgressValue.text = "$progress %"
        binding.downloadDialogPercentageProgress.progress = progress

    }

    fun setDownloadSizeProgress(fileSize: Double) {
        binding.downloadDialogProgressValue.text = "$fileSize MB"
    }

    fun enablePercentageMode() {
        binding.downloadDialogSizeProgress.visibility = View.GONE
        binding.downloadDialogPercentageProgress.visibility = View.VISIBLE
    }

    fun enableFileSizeMode() {
        binding.downloadDialogSizeProgress.visibility = View.VISIBLE
        binding.downloadDialogPercentageProgress.visibility = View.GONE
    }

    private fun initDialog() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
    }
}