package com.ugisozols.biometricnoteapp.data.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ugisozols.biometricnoteapp.data.NoteDao


@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao
}