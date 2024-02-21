package com.example.testappkevych.ui.navigation

interface NavigationDestination {
    val route: String
}

sealed class Destaination {

    object MovieList : NavigationDestination {
        override val route = "Movies"
    }

    object MovieById: NavigationDestination{
        override val route: String
            get() = "Movie Detail"
        const val Movie_Id ="id"
        val routeWithArgs = "$route/{$Movie_Id}"
    }

}