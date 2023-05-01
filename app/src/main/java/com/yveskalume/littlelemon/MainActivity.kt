package com.yveskalume.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yveskalume.littlelemon.ui.NavigationComposable
import com.yveskalume.littlelemon.ui.theme.LittleLemonTheme
import com.yveskalume.littlelemon.util.Constants
import com.yveskalume.littlelemon.util.Destination
import com.yveskalume.littlelemon.util.isEmail

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = this.getPreferences(Context.MODE_PRIVATE)
        var email = pref.getString(Constants.userDatekeys.email, "")
        var startDestination = if (email?.isEmail() == true) {
            Destination.Home.route
        } else {
            Destination.OnBoarding.route
        }
        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationComposable(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}