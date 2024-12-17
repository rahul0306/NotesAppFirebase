package com.example.noteappfirebase.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.noteappfirebase.Navigation.NotesNavigationitem
import com.example.noteappfirebase.R
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        delay(2000)
        navHostController.navigate(route = NotesNavigationitem.NotesScreen.route){
            popUpTo(NotesNavigationitem.SplashScreen.route) {
                inclusive=true
            }
        }
    }

    Scaffold {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}
