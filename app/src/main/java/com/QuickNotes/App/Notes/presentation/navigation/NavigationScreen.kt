package com.QuickNotes.App.Notes.presentation.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.QuickNotes.App.Notes.data.AppDatabase
import com.QuickNotes.App.Notes.presentation.view.NoteAddScreen
import com.QuickNotes.App.Notes.presentation.view.NoteDetailScreen
import com.QuickNotes.App.Notes.presentation.view.NoteEditScreen
import com.QuickNotes.App.Notes.presentation.view.NotesListWithBackground

import com.QuickNotes.App.Notes.presentation.view.NotesScreen
import com.QuickNotes.App.Notes.presentation.view.SettingsScreen
import com.QuickNotes.App.Notes.presentation.view.SplashScreen
import com.QuickNotes.App.Notes.presentation.viewModel.NoteViewModel
import com.QuickNotes.App.Notes.presentation.viewModel.NoteViewModelFactory

@Composable
fun NavigationScreen(
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val dao = database.getDao()
    val viewModelFactory = NoteViewModelFactory(dao)
    val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)
    NavHost(
        navController = navHostController,
        startDestination = Destinations.SplashScreen.route,
        modifier = Modifier,

        ) {

        composable(route = Destinations.SplashScreen.route) {
            SplashScreen {

                navHostController.navigate(Destinations.NoteScreen.route) {
                    popUpTo(Destinations.SplashScreen.route) {
                        inclusive = true
                    }
                }


            }
        }



        composable(route = Destinations.SettingsScreen.route) {
            SettingsScreen(onBack = { navHostController.navigateUp() })
        }
        composable(Destinations.NoteScreen.route) {
            NotesScreen(

                viewModel = viewModel, navController = navHostController

                )
        }
        composable(
            route = Destinations.NoteDetailScreen.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            NoteDetailScreen(noteId, viewModel, onBackClick = { navHostController.navigateUp() })
        }
        composable(
            route = Destinations.NoteEditScreen.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            NoteEditScreen(noteId, viewModel, onBackClick = { navHostController.navigateUp() })
        }
        composable(Destinations.NoteAddScreen.route) {
            NoteAddScreen(viewModel = viewModel, onBackPressed = { navHostController.navigateUp() })
        }
    }
}