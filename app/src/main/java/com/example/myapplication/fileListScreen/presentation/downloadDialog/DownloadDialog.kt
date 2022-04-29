package com.example.myapplication.fileListScreen.presentation.downloadDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.myapplication.databinding.DownloadDialogViewBinding

class DownloadDialog(context: Context): Dialog(context) {

    private lateinit var binding: DownloadDialogViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DownloadDialogViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialog()
    }

    fun setDownloadProgress(progress: Int){
        binding.downloadDialogProgress.progress = progress
    }

    private fun initDialog(){
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
    }
}