package com.example.myapplication.fileListScreen.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FileListItemBinding
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
        holder.binding.fileItemNameTextView.text = fileList[position].name
        holder.binding.fileItemActionImageView.setOnClickListener {
            fileClickListener.onItemClicked(position)
        }
    }

    override fun getItemCount() = fileList.size

    inner class FileItemViewHolder(val binding: FileListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setFiles(list: ArrayList<FileModel>){
        fileList = list
        notifyDataSetChanged()
    }
}