package com.example.assignment1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun addnote(note : Note)

    @Query("SELECT * FROM notes")
    suspend fun getallnotes() : List<Note>

    @Query("SELECT * FROM notes WHERE notecategory =:cat")
    suspend fun getNotesBycat(cat : String) : List<Note>

    @Query("SELECT DISTINCT notecategory FROM notes")
    suspend fun getAllCategories():List<String>
    @Update
    suspend fun updatenote(note : Note)

    @Delete
    suspend fun deletenote(note : Note)
}