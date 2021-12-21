package com.ugisozols.biometricnoteapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ugisozols.biometricnoteapp.presentation.main_screen.MainScreen
import com.ugisozols.biometricnoteapp.presentation.ui.theme.BiometricNoteAppTheme
import com.ugisozols.biometricnoteapp.util.launchBiometricFingerprintReader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiometricNoteAppTheme {
                // A surface container using the 'background' color from the theme
                val scaffoldState : ScaffoldState = rememberScaffoldState()
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

