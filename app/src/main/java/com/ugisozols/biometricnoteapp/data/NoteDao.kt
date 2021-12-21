package com.ugisozols.biometricnoteapp.data

import androidx.room.*
import com.ugisozols.biometricnoteapp.data.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes() : Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Query("DELETE FROM notes WHERE id =:noteId")
    suspend fun deleteNoteById(noteId : String)

    @Query("SELECT * FROM notes WHERE id =:noteId")
    suspend fun getNoteById(noteId : String) : Note

}