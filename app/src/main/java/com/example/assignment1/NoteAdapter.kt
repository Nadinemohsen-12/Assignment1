package com.example.assignment1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class NoteAdapter(private var notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Holds the views for each single row
    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.editTextText)
        val category: TextView = view.findViewById(R.id.editTextTextcategory)
    }

    // Creates one row (list_note_item.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main, parent, false)
        return NoteViewHolder(view)
    }

    // Puts data into each row
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.category.text = note.notecategory
    }

    // Number of items in the list
    override fun getItemCount() = notes.size

    // Update the list when filtering or showing all
    fun updateList(newList: List<Note>) {
        notes = newList
        notifyDataSetChanged()
    }
}
