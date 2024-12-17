package com.example.noteappfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.noteappfirebase.Navigation.NotesNavigation
import com.example.noteappfirebase.ui.theme.NoteAppFirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppFirebaseTheme {
                NotesNavigation()
            }
        }
    }
}