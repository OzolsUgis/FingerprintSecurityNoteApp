package com.ugisozols.biometricnoteapp.presentation.main_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugisozols.biometricnoteapp.data.entities.Note
import com.ugisozols.biometricnoteapp.util.Constants.FAB_TEXT




@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val securityPassed = viewModel.hasFingerprintSecurityPassed
    val notes = viewModel.allNotes.collectAsState(initial = emptyList()).value
    if (!securityPassed){
        viewModel.fingerprint()
        Box(modifier = Modifier.fillMaxSize().background(Color.Black))
    }else{
        FloatingAddButton(onButtonClick = { viewModel.onDialogIsOpenChange(true) }, viewModel)
        ListOfNotes(
            notes,
            viewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun FloatingAddButton(
    onButtonClick: () -> Unit,
    viewModel: MainScreenViewModel
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = FAB_TEXT) },
                onClick = { onButtonClick() },
                icon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = FAB_TEXT)
                }
            )
        }
    ) {
        val dialogIsVisible = viewModel.addNewNoteDialogIsOpen
        if (dialogIsVisible) {
            ShowDialog(
                onDismiss = { viewModel.onDialogIsOpenChange(false) },
                onDoneButtonClick = { viewModel.saveNote() },
                viewModel = viewModel
            )
        }

    }
}

@Composable
fun ListOfNotes(
    notes: List<Note>,
    viewModel: MainScreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Notes :", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(content = {
            if (notes.isEmpty()) {
                item {
                    Text(text = "There is 0 saved notes")
                }
            } else {
                items(notes) { note ->
                    NotesListItem(
                        note = note,
                        onNoteClick = { viewModel.getNoteById(note.id) },
                        onDeleteClick = { viewModel.deleteNote(note.id) }
                    )

                }
            }
        }
        )
        viewModel.note?.let { note ->
            Dialog(onDismissRequest = { viewModel.onNoteDetailDismissClick() }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(Modifier.wrapContentHeight()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = note.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            IconButton(onClick = {
                                viewModel.deleteNote(note.id)
                                viewModel.onNoteDetailDismissClick()
                            }){
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Note")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = note.content)
                    }
                }
            }
        }
    }
}

@Composable
fun NotesListItem(
    note: Note,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .clickable {
                onNoteClick()
            }
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = note.title, fontSize = 20.sp)
        IconButton(onClick = { onDeleteClick() }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Note")
        }
    }
}


@Composable
fun ShowDialog(
    onDismiss: () -> Unit,
    onDoneButtonClick: () -> Unit,
    viewModel: MainScreenViewModel
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Add new note:")
        },
        text = {
            Column {
                TextField(
                    value = viewModel.noteTitle,
                    onValueChange = { titleInput ->
                        viewModel.onNoteTitleChange(titleInput)
                    },
                    placeholder = {
                        Text(text = "Note title")
                    }
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = viewModel.noteContent,
                    onValueChange = { contentInput ->
                        viewModel.onNoteContentChange(contentInput)
                    },
                    placeholder = {
                        Text(text = "Note content")
                    }
                )
            }

        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    onDoneButtonClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Add new note"
                    )
                }
            }
        }

    )
}

