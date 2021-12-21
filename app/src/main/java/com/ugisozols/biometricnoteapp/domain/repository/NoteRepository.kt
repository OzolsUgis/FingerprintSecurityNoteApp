package com.ugisozols.biometricnoteapp.domain.repository

import android.app.Application
import android.content.Context
import com.ugisozols.biometricnoteapp.data.NoteDao
import com.ugisozols.biometricnoteapp.data.entities.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNote(note : Note){
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(noteId : String){
        noteDao.deleteNoteById(noteId)
    }

    fun getAllNotes() : Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    suspend fun getNoteById(noteId : String)  :Note{
        return noteDao.getNoteById(noteId = noteId)
    }
}