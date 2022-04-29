package com.example.myapplication.fileListScreen.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFileListBinding
import com.example.myapplication.fileListScreen.presentation.adapter.FileListAdapter
import com.example.myapplication.fileListScreen.presentation.helpers.FileClickListener
import com.example.myapplication.fileListScreen.presentation.helpers.FileUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileListActivity : AppCompatActivity(), FileClickListener {
    private val viewModel: FileListViewModel by viewModels()
    private lateinit var binding: ActivityFileListBinding

    private val listAdapter: FileListAdapter = FileListAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        observeViewModelValues()

        loadFileList()
    }

    private fun observeViewModelValues() {
        viewModel.filesList.observe(this) { files ->
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

    }
}