package com.yveskalume.littlelemon.ui

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yveskalume.littlelemon.MainActivity
import com.yveskalume.littlelemon.R
import com.yveskalume.littlelemon.ui.theme.Yellow
import com.yveskalume.littlelemon.util.Constants
import com.yveskalume.littlelemon.util.Destination

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        val activity = (context as MainActivity)
        val pref = activity.getPreferences(Context.MODE_PRIVATE)
        firstName = pref.getString(Constants.userDatekeys.firstName, "") ?: ""
        lastName = pref.getString(Constants.userDatekeys.lastName, "") ?: ""
        email = pref.getString(Constants.userDatekeys.email, "") ?: ""
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(24.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Personal Information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "First name", modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = firstName,
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Last name", modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = lastName,
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Email", modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val activity = (context as MainActivity)
                val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString(Constants.userDatekeys.firstName, "")
                    putString(Constants.userDatekeys.lastName, "")
                    putString(Constants.userDatekeys.email, "")
                    apply()
                }
                navController.navigate(Destination.OnBoarding.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Log out")
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    MaterialTheme {
        Profile(navController = rememberNavController())
    }
}