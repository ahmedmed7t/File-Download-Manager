package com.example.myapplication.fileListScreen.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FileListItemBinding
import com.example.myapplication.fileListScreen.domain.models.DownloadStatus
import com.example.myapplication.fileListScreen.domain.models.FileModel
import com.example.myapplication.fileListScreen.presentation.helpers.FileClickListener

class FileListAdapter(
    private var fileList: ArrayList<FileModel>,
    private val fileClickListener: FileClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileItemViewHolder {
        val binding =
            FileListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as FileItemViewHolder
        holder.binding.apply {
            fileItemNameTextView.text = fileList[position].name
            fileItemActionImageView.setOnClickListener {
                if (fileList[position].downloadStatus == null)
                    fileClickListener.onItemClicked(position)
            }

            fileList[position].downloadStatus?.let { downloadStatus ->
                when (downloadStatus) {
                    DownloadStatus.DOWNLOADED -> {
                        fileItemActionImageView.setImageResource(R.drawable.green_correct_icon)
                    }
                    DownloadStatus.FAIL -> {
                        fileItemActionImageView.setImageResource(R.drawable.red_cancel_icon)
                    }
                }
            }
        }
    }

    override fun getItemCount() = fileList.size

    fun setFiles(list: ArrayList<FileModel>) {
        fileList = list
        notifyDataSetChanged()
    }

    fun updateDownloadedItem(position: Int) {
        fileList[position].downloadStatus = DownloadStatus.DOWNLOADED
        notifyItemChanged(position)
    }

    fun updateFailItem(position: Int) {
        fileList[position].downloadStatus = DownloadStatus.FAIL
        notifyItemChanged(position)
    }

    inner class FileItemViewHolder(val binding: FileListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}