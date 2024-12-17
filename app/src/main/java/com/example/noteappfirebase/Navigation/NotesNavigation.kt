package com.example.noteappfirebase.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteappfirebase.Screens.InsertNoteScreen
import com.example.noteappfirebase.Screens.NotesScreen
import com.example.noteappfirebase.Screens.SplashScreen

@Composable
fun NotesNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NotesNavigationitem.SplashScreen.route ){
        composable(route = NotesNavigationitem.SplashScreen.route){
            SplashScreen(navHostController = navController)
        }
        composable(route = NotesNavigationitem.NotesScreen.route){
            NotesScreen(navHostController = navController)
        }
        composable(route = NotesNavigationitem.InsertNoteScreen.route+"/{id}"){
            val id = it.arguments?.getString("id")
            InsertNoteScreen(navHostController = navController,id)
        }
    }
}