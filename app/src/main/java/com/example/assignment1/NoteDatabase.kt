package com.example.assignment1

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
@Database(entities = [Note ::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun Notedao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

