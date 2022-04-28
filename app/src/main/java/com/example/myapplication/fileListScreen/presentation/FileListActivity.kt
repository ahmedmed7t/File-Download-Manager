package com.example.myapplication.fileListScreen.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityFileListBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels

@AndroidEntryPoint
class FileListActivity : AppCompatActivity() {
    private val viewModel: FileListViewModel by viewModels()
    private lateinit var binding: ActivityFileListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}