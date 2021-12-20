package com.ugisozols.biometricnoteapp.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.ugisozols.biometricnoteapp.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

}