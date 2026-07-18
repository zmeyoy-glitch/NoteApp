package com.example.noteapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.NoteUiModel
import com.example.noteapp.databinding.ItemNoteBinding

class NoteAdapter(
    private val onNoteClick: (NoteUiModel) -> Unit,
    private val onDeleteClick: (Long) -> Unit
) : ListAdapter<NoteUiModel, NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteUiModel) {
            binding.titleText.text = note.title.ifEmpty { "Untitled" }
            binding.contentText.text = note.content.take(100).ifEmpty { "No content" }
            
            binding.root.setOnClickListener {
                onNoteClick(note)
            }

            binding.deleteButton.setOnClickListener {
                onDeleteClick(note.id)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<NoteUiModel>() {
        override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel): Boolean {
            return oldItem == newItem
        }
    }
}