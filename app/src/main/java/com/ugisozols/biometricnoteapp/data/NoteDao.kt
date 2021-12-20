package com.ugisozols.biometricnoteapp.data

import androidx.room.*
import com.ugisozols.biometricnoteapp.data.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Query("DELETE FROM notes WHERE id =:noteId")
    suspend fun deleteNoteById(noteId : String)

    @Query("SELECT * FROM notes WHERE id =:noteId")
    fun getNoteById(noteId : String) : Note

}