package com.example.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.NoteEntity

class NoteAdapter(
    private val onDelete: (NoteEntity) -> Unit
) : ListAdapter<NoteEntity, NoteAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val contentText: TextView = itemView.findViewById(R.id.contentText)

        fun bind(note: NoteEntity) {
            titleText.text = note.title
            contentText.text = note.content
            itemView.setOnClickListener { /* TODO: Add click handler */ }
            itemView.setOnLongClickListener {
                onDelete(note)
                true
            }
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
}