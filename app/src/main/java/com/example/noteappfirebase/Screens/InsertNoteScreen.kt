package com.example.noteappfirebase.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteappfirebase.Model.Notes
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InsertNoteScreen(
    navHostController: NavHostController,
    id: String?,
    modifier: Modifier = Modifier
) {

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    val notesDb = db.collection("notes")

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (id != "defaultId") {
            notesDb.document(id.toString()).get().addOnSuccessListener {
                val data = it.toObject(Notes::class.java)
                title.value = data!!.title
                description.value = data.description
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(corner = CornerSize(100.dp)),
                contentColor = Color.White,
                containerColor = Color.Red,
                onClick = {
                    if (title.value.isEmpty() && description.value.isEmpty()) {
                        Toast.makeText(
                            context, "Title and Description cannot be empty", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val myNoteId = if (id != "defaultId") {
                            id.toString()
                        } else {
                            notesDb.document().id
                        }
                        val notes = Notes(
                            id = myNoteId,
                            title = title.value,
                            description = description.value
                        )
                        notesDb.document(myNoteId).set(notes).addOnCompleteListener {
                            if (it.isSuccessful) {
                                navHostController.popBackStack()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Save Note")
            }
        }
    )
    { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.Black)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = "Add Note", style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = modifier.height(15.dp))
                TextField(
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                    label = {
                        Text(
                            text = "Enter title",
                            style = TextStyle(color = Color.LightGray)
                        )
                    },
                    value = title.value,
                    textStyle = TextStyle(
                        color = Color.White
                    ),
                    onValueChange = {
                        title.value = it
                    },
                    modifier = modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF252525),
                        focusedContainerColor = Color(0xFF252525),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = modifier.height(15.dp))
                TextField(
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                    label = {
                        Text(
                            text = "Enter Description",
                            style = TextStyle(color = Color.LightGray)
                        )
                    },
                    value = description.value,
                    textStyle = TextStyle(
                        color = Color.White
                    ),
                    onValueChange = {
                        description.value = it
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF252525),
                        focusedContainerColor = Color(0xFF252525),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}