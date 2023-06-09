package com.yveskalume.littlelemon.ui

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
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
import com.yveskalume.littlelemon.ui.theme.Green200
import com.yveskalume.littlelemon.ui.theme.White
import com.yveskalume.littlelemon.ui.theme.Yellow
import com.yveskalume.littlelemon.util.Constants
import com.yveskalume.littlelemon.util.Destination
import com.yveskalume.littlelemon.util.isEmail

@Composable
fun OnBoarding(navController: NavController) {
    val context = LocalContext.current

    BackHandler {
        (context as Activity).finish()
    }

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var isFormValid by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(firstName, lastName, email) {
        isFormValid = firstName.isNotBlank() && lastName.isNotBlank() && email.isEmail()
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
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Green200),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Let's get to know you", fontSize = 24.sp, color = White)
        }
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
            onValueChange = { firstName = it },
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
            onValueChange = { lastName = it },
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
            onValueChange = { email = it },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            enabled = isFormValid,
            onClick = {
                val activity = (context as MainActivity)
                val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString(Constants.userDatekeys.firstName, firstName)
                    putString(Constants.userDatekeys.lastName, lastName)
                    putString(Constants.userDatekeys.email, email)
                    apply()
                }
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(Destination.Home.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Register")
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun OnBoardingPreview() {
    MaterialTheme {
        OnBoarding(navController = rememberNavController())
    }
}