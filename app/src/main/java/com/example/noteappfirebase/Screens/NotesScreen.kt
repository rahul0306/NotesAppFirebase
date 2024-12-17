package com.example.noteappfirebase.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteappfirebase.Model.Notes
import com.example.noteappfirebase.Navigation.NotesNavigationitem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

//val snapshotStateListSaver: Saver<SnapshotStateList<Notes>, Any> =
//    listSaver(
//        save = { it.toList() },
//        restore = { mutableStateListOf(*it.toTypedArray()) }
//    )

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val db = FirebaseFirestore.getInstance()
    val notesDb = db.collection("notes")

    val notesList = remember() {
        mutableStateListOf<Notes>()
    }

    val loading = remember() { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        notesDb.addSnapshotListener { value, error ->
            if (error != null) {
                println("Snapshot listener error: ${error.message}")
                loading.value = false
            } else {
                val data = value?.toObjects(Notes::class.java)
                println("Fetched data: $data") // Debug fetched data
                notesList.clear()
                data?.let { notesList.addAll(it) }
                loading.value = true
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(shape = RoundedCornerShape(corner = CornerSize(100.dp)),
                contentColor = Color.White,
                containerColor = Color.Red,
                onClick = {
                   navHostController.navigate(route = NotesNavigationitem.InsertNoteScreen.route+"/defaultId")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add Icon",
                    )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Column(
                modifier = modifier
                    .padding(10.dp)
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = "Notes", style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                )
                if (loading.value) {
                    LazyColumn {
                        items(notesList) { notes ->
                            NotesListItem(
                                notes,
                                notesDb,
                                navHostController
                            )
                        }
                    }
                } else {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = modifier
                                .size(20.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotesListItem(
    notes: Notes,
    notesDb: CollectionReference,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
            .background(color = Color(0xFF252525)).clickable {
                navHostController.navigate(route = NotesNavigationitem.InsertNoteScreen.route+"/${notes.id}")
            }
    )
    {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Note",
            tint = Color.White,
            modifier = modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clickable {
                    notesDb
                        .document(notes.id)
                        .delete()
                }
        )
        Column(
            modifier.padding(20.dp)
        ) {
            Text(
                text = notes.title, style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = notes.description, style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.LightGray
                )
            )
        }
    }
}

