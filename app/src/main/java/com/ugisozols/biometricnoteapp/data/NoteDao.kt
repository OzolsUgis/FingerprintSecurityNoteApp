package com.ugisozols.biometricnoteapp.data

import androidx.room.*
import com.ugisozols.biometricnoteapp.data.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAll() : List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNoteById(id : String)

    @Query("SELECT * FROM Note WHERE id =:noteId")
    fun getNoteById(noteId : String) : Note

}