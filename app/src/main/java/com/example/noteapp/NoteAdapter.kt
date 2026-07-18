package com.example.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.NoteEntity
import com.example.noteapp.databinding.ItemNoteBinding

class NoteAdapter : ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    private val onNoteClick: (NoteEntity) -> Unit = {}
    private val onDeleteClick: (NoteEntity) -> Unit = {}

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

        fun bind(note: NoteEntity) {
            binding.titleText.text = note.title
            binding.contentText.text = note.content
            binding.root.setOnClickListener { onNoteClick(note) }
            binding.deleteButton.setOnClickListener { onDeleteClick(note) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnNoteClick(listener: (NoteEntity) -> Unit) {
        this.onNoteClick = listener
    }

    fun setOnDeleteClick(listener: (NoteEntity) -> Unit) {
        this.onDeleteClick = listener
    }
}