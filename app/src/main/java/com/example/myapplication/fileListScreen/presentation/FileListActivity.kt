package com.example.myapplication.fileListScreen.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFileListBinding
import com.example.myapplication.fileListScreen.presentation.adapter.FileListAdapter
import com.example.myapplication.fileListScreen.presentation.downloadDialog.DownloadDialog
import com.example.myapplication.fileListScreen.presentation.helpers.FileClickListener
import com.example.myapplication.fileListScreen.presentation.helpers.ProgressListener
import com.example.myapplication.utils.FileUtils
import com.example.myapplication.utils.hasPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class FileListActivity : AppCompatActivity(), FileClickListener, ProgressListener {
    private val viewModel: FileListViewModel by viewModels()
    private lateinit var binding: ActivityFileListBinding

    private val listAdapter: FileListAdapter = FileListAdapter(arrayListOf(), this)
    private val downloadDialog by lazy { DownloadDialog(this) }

    private var REQUEST_CODE = 112

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        observeViewModelValues()

        loadFileList()
    }

    private fun initRecyclerView() =
        binding.fileListRecyclerView.apply {
            setHasFixedSize(true)
            adapter = listAdapter
        }

    private fun observeViewModelValues() =
        viewModel.apply {
            filesList.observe(this@FileListActivity) { files ->
                listAdapter.setFiles(files)
            }
        }

    private fun loadFileList() {
        val jsonList = FileUtils.loadJSONFromAsset(this) ?: ""
        val fileList = FileUtils.convertJsonToList(jsonList)
        viewModel.setFileListValues(fileList)
    }

    private fun getStorageFile(): File {
        val file = File(externalCacheDir.toString())
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    private fun checkIfHasPermission(position: Int) {
        if (Build.VERSION.SDK_INT >= 23) {
            val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (!hasPermissions(PERMISSIONS)) {
                REQUEST_CODE = position
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE)
            } else {
                loadFile(position)
            }
        } else {
            loadFile(position)
        }
    }

    private fun loadFile(position: Int) {
        binding.fileListProgressBar.visibility = View.VISIBLE
        viewModel.loadFile(position, getStorageFile(), this)
    }

    private fun updateDialogPercentageProgress(progress: Int) {
        downloadDialog.apply {
            showDownloadDialog()
            enablePercentageMode()
            setDownloadPercentageProgress(progress)
        }
    }

    private fun updateDialogFileSizeProgress(fileSize: Double) {
        downloadDialog.apply {
            showDownloadDialog()
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

    private fun showDownloadDialog() {
        if (!downloadDialog.isShowing)
            downloadDialog.show()
    }

    override fun onItemClicked(position: Int) =
        checkIfHasPermission(position)

    override fun onPercentageProgress(progress: Int) {
        hideProgressBar()
        updateDialogPercentageProgress(progress)
    }

    override fun onSizeProgress(fileSize: Double) {
        hideProgressBar()
        updateDialogFileSizeProgress(fileSize)
    }

    override fun onDownloadSuccess(position: Int) {
        listAdapter.updateDownloadedItem(position)
        dismissDownloadDialog()
    }

    override fun onDownloadFail(position: Int) {
        hideProgressBar()
        listAdapter.updateFailItem(position)
        dismissDownloadDialog()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadFile(REQUEST_CODE)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.permission_denied_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}