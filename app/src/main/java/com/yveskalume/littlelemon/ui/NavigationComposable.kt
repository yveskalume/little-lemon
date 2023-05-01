package com.yveskalume.littlelemon.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yveskalume.littlelemon.MainActivity
import com.yveskalume.littlelemon.util.Constants
import com.yveskalume.littlelemon.util.Destination
import com.yveskalume.littlelemon.util.isEmail


@Composable
fun NavigationComposable(navController: NavHostController,startDestination: String) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Destination.OnBoarding.route) {
            OnBoarding(navController = navController)
        }
        composable(route = Destination.Home.route) {
            Home(navController = navController)
        }
        composable(route = Destination.Profile.route) {
            Profile(navController = navController)
        }
    }
}