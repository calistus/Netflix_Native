package com.grandilo.netflixnative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grandilo.netflixnative.ui.HomeScreen
import com.grandilo.netflixnative.ui.movie.MovieDetail
import com.grandilo.netflixnative.ui.theme.NetflixNativeTheme
import com.grandilo.netflixnative.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetflixNativeTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME
                ) {
                    composable(Routes.HOME) {
                        HomeScreen(navController)
                    }
                    composable(Routes.MOVIE_DETAIL) {
                        MovieDetail(imgURL = "")
                    }
                }
            }
        }
    }
}




