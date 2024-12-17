package com.example.noteappfirebase.Navigation

sealed class NotesNavigationitem(val route:String) {
    object SplashScreen:NotesNavigationitem(
        route = "splash_screen"
    )

    object NotesScreen:NotesNavigationitem(
        route = "notes_screen"
    )

    object InsertNoteScreen:NotesNavigationitem(
        route = "insert_note_screen"
    )
}