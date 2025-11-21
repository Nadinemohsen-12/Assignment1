package com.example.assignment1
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
    class DetailActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail)

            val title = intent.getStringExtra("title")
            val category = intent.getStringExtra("category")
            val content = intent.getStringExtra("content")

            findViewById<TextView>(R.id.txtTitle).text = title
            findViewById<TextView>(R.id.txtCategory).text = category
            findViewById<TextView>(R.id.txtContent).text = content
        }
    }
