package com.example.testappkevych.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testappkevych.ui.Screens.MovieList
import com.example.testappkevych.ui.Screens.MovieScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Destaination.MovieList.route )
    {
        composable(route = Destaination.MovieList.route){
            MovieList(onMovieClick ={navController.navigate("${Destaination.MovieById.route}/$it")})
        }

        composable(route = Destaination.MovieById.routeWithArgs,
            arguments = listOf(navArgument(Destaination.MovieById.Movie_Id){
            type = NavType.StringType
        })){
            MovieScreen()
        }
    }
}