package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.toString

class MainActivity : AppCompatActivity() {
    private lateinit var list: ListView
    private lateinit var db: NoteDatabase
    private var notes: MutableList<Note> = mutableListOf()
    private var filterednotes: MutableList<Note> = mutableListOf()
    private lateinit var noteDao: NoteDao
    private lateinit var categorySpinner:Spinner

    private lateinit var noteAdapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn =findViewById<Button>(R.id.button)
        val shownotes =findViewById<Button>(R.id.button3)
        val filter =findViewById<Button>(R.id.button2)
        val notetitle = findViewById<EditText>(R.id.editTextText)
        val category = findViewById<EditText>(R.id.editTextTextcategory)
        val content = findViewById<EditText>(R.id.editTextTextContent)
        var adapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1)
        list.adapter = adapter
        list = findViewById<ListView>(R.id.list)
        db = NoteDatabase.getDatabase(this)
        val categoryspinner = findViewById<Spinner>(R.id.spinner)


        btn.setOnClickListener() {
            val notitle = notetitle.text.toString().trim()
            val cat = category.text.toString().trim()
            val combined = "$title($cat)"
          val content = content.text.toString().trim()

            if(notitle.isNotEmpty() && content.isNotEmpty() && cat.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    db.Notedao()
                        .addnote(Note(title = notitle, content = content, notecategory = cat))
                    withContext(Dispatchers.Main) {
                        adapter.add(combined)
                        adapter.notifyDataSetChanged()
                        notetitle.text.clear()
                        category.text.clear()
                    }
                }
            }
        else {
            Toast.makeText(this,"please fill all the inputs ",Toast.LENGTH_SHORT).show()
        }

        }

        //btn showall
        shownotes.setOnClickListener {
            loadnotes()
        }
        //btn filter
        filter.setOnClickListener {
           filter()
        }


        }

    private fun loadnotes(){
        lifecycleScope.launch(Dispatchers.IO) {
            notes = db.Notedao().getallnotes().toMutableList()
            withContext(Dispatchers.Main) {
                val adapternote: ArrayAdapter<String> = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    notes.map { it.title }
                )
                list.adapter = adapternote

                list.setOnItemClickListener { _, _, position, _ ->
                    val selectednote = notes[position]
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    // Pass data to the next activity
                    intent.putExtra("noteid", selectednote.id)
                    intent.putExtra("notename", selectednote.title)
                    startActivity(intent)
                }
            }
        }
    }
    private suspend fun spinner() {
        val categories = noteDao.getAllCategories().toMutableList()
        categories.add(0, "All Categories")
        val adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        categorySpinner.adapter = adapter

    }

    private fun filter(){
        val selectedCategory = categorySpinner.selectedItem?.toString()
        if (selectedCategory == "All Categories" || selectedCategory == null) {
            loadnotes()
            return
        }
            lifecycleScope.launch(Dispatchers.IO){
            filterednotes = db.Notedao().getNotesBycat(selectedCategory).toMutableList()
            withContext(Dispatchers.Main){
                noteAdapter.updateList(filterednotes)
            }
        }
    }
}


   /* override fun onRestart() {
        super.onRestart()
    }*/
