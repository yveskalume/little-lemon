package com.yveskalume.littlelemon.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yveskalume.littlelemon.MainActivity
import com.yveskalume.littlelemon.R
import com.yveskalume.littlelemon.util.Destination

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current

    BackHandler {
        (context as Activity).finish()
    }

    Row(
        horizontalArrangement = Arrangement.End, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            onClick = {
                navController.navigate(Destination.Profile.route)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    MaterialTheme {
        Home(navController = rememberNavController())
    }
}