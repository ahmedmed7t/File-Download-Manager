package com.example.myapplication.fileListScreen.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityFileListBinding
import com.example.myapplication.fileListScreen.presentation.adapter.FileListAdapter
import com.example.myapplication.fileListScreen.presentation.downloadDialog.DownloadDialog
import com.example.myapplication.fileListScreen.presentation.helpers.FileClickListener
import com.example.myapplication.utils.FileUtils
import com.example.myapplication.fileListScreen.presentation.helpers.ProgressListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class FileListActivity : AppCompatActivity(), FileClickListener, ProgressListener {
    private val viewModel: FileListViewModel by viewModels()
    private lateinit var binding: ActivityFileListBinding

    private val listAdapter: FileListAdapter = FileListAdapter(arrayListOf(), this)
    private val downloadDialog by lazy { DownloadDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        observeViewModelValues()

        loadFileList()
    }

    private fun observeViewModelValues() =
        viewModel.apply {
            filesList.observe(this@FileListActivity) { files ->
                listAdapter.setFiles(files)
            }
        }

    private fun initRecyclerView() =
        binding.fileListRecyclerView.apply {
            setHasFixedSize(true)
            adapter = listAdapter
        }

    private fun loadFileList() {
        val jsonList = FileUtils.loadJSONFromAsset(this) ?: ""
        val fileList = FileUtils.convertJsonToList(jsonList)
        viewModel.setFileListValues(fileList)
    }

    override fun onItemClicked(position: Int) {
        binding.fileListProgressBar.visibility = View.VISIBLE
        viewModel.loadFile(position, getStorageFile(), this)
    }

    private fun getStorageFile(): File {
        val file = File(externalCacheDir.toString())
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    override fun onPercentageProgress(progress: Int) {
        hideProgressBar()
        updateDialogPercentageProgress(progress)
    }

    override fun onSizeProgress(fileSize: Double) {
        hideProgressBar()
        updateDialogFileSizeProgress(fileSize)
    }

    override fun onDownloadSuccess(position: Int) {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        dismissDownloadDialog()
    }

    override fun onDownloadFail(position: Int) {
        hideProgressBar()
        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
        dismissDownloadDialog()
    }

    private fun updateDialogPercentageProgress(progress: Int) {
        dismissDownloadDialog()
        downloadDialog.apply {
            enablePercentageMode()
            setDownloadPercentageProgress(progress)
        }
    }

    private fun updateDialogFileSizeProgress(fileSize: Double) {
        dismissDownloadDialog()
        downloadDialog.apply {
            enableFileSizeMode()
            setDownloadSizeProgress(fileSize)
        }
    }

    private fun hideProgressBar() {
        if (binding.fileListProgressBar.isVisible)
            binding.fileListProgressBar.visibility = View.GONE
    }

    private fun dismissDownloadDialog() {
        if (downloadDialog.isShowing)
            downloadDialog.dismiss()
    }

}