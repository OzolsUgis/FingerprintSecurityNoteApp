package com.ugisozols.biometricnoteapp.presentation.main_screen

import android.app.Application
import android.os.Build
import android.os.CancellationSignal
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugisozols.biometricnoteapp.data.entities.Note
import com.ugisozols.biometricnoteapp.domain.repository.NoteRepository
import com.ugisozols.biometricnoteapp.util.launchBiometricFingerprintReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val context: Application
) : ViewModel() {

    private var cancellationSignal: CancellationSignal? = null

    var addNewNoteDialogIsOpen by mutableStateOf(false)
        private set

    var noteTitle by mutableStateOf("")
        private set

    var noteContent by mutableStateOf("")
        private set

    var hasFingerprintSecurityPassed by mutableStateOf(false)
    private set


    @RequiresApi(Build.VERSION_CODES.R)
    fun fingerprint() = viewModelScope.launch {
        launchBiometricFingerprintReader(
            { getCancelationSignal() },
            authenticationSucceeded = {
                hasFingerprintSecurityPassed = true
            },
            authenticationError = {
                hasFingerprintSecurityPassed = false
            },
            context
        )
    }




    private fun getCancelationSignal() : CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Log.d("MY_APP_TAG","Authentication cancelation signal")
        }

        return cancellationSignal as CancellationSignal
    }


    fun onDialogIsOpenChange(isDialogVisible: Boolean) {
        addNewNoteDialogIsOpen = isDialogVisible
    }

    fun onNoteTitleChange(title: String) {
        noteTitle = title
    }

    fun onNoteContentChange(content: String) {
        noteContent = content
    }

    var note by mutableStateOf<Note?>(null)
    private set

    fun getNoteById(noteId: String){
        viewModelScope.launch {
            note = noteRepository.getNoteById(noteId)
        }
    }


    fun deleteNote(noteId : String) = viewModelScope.launch{
        noteRepository.deleteNote(noteId)
    }

    fun saveNote() = viewModelScope.launch {
        noteRepository.insertNote(
            Note(
                title = noteTitle,
                content = noteContent
            )
        )
        noteTitle = ""
        noteContent = ""
    }

    fun onNoteDetailDismissClick(){
        note = null
    }

    private val _allNotes = noteRepository.getAllNotes()
    val allNotes: Flow<List<Note>> = _allNotes
}