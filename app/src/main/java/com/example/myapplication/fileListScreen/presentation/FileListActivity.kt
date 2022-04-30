package com.example.myapplication.fileListScreen.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFileListBinding
import com.example.myapplication.fileListScreen.domain.models.Download
import com.example.myapplication.fileListScreen.presentation.adapter.FileListAdapter
import com.example.myapplication.fileListScreen.presentation.downloadDialog.DownloadDialog
import com.example.myapplication.fileListScreen.presentation.helpers.FileClickListener
import com.example.myapplication.fileListScreen.presentation.helpers.FileUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileListActivity : AppCompatActivity(), FileClickListener {
    private val viewModel: FileListViewModel by viewModels()
    private lateinit var binding: ActivityFileListBinding

    private val listAdapter: FileListAdapter = FileListAdapter(arrayListOf(), this)
    private val downloadDialog = DownloadDialog(this)

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

            downloadProgress.observe(this@FileListActivity) { downloadProgress ->
                when (downloadProgress) {
                    is Download.Progress -> {
                        updateDialogProgress(downloadProgress.percent)
                    }
                    is Download.Fail -> {
                        TODO()
                    }
                    is Download.Finished -> {
                        TODO()
                    }
                }
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
        downloadDialog.show()
        viewModel.loadFile(position, externalCacheDir!!)
    }

    private fun updateDialogProgress(progress: Int) {
        if (downloadDialog.isShowing) {
            downloadDialog.setDownloadProgress(progress)
        }
    }
}