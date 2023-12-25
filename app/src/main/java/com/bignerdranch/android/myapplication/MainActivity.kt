package com.bignerdranch.android.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import api.ApiInterface
import api.RetrofitClient
import com.bignerdranch.android.myapplication.ui.theme.MyApplicationTheme
import models.User
import models.UserResponse

class MainActivity : ComponentActivity() {
    private val retrofit = RetrofitClient.getInstance()
    val apiInterface = retrofit.create(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var users by remember { mutableStateOf<List<User>>(emptyList()) }
            LaunchedEffect(Unit) {
                val userResponse: UserResponse = apiInterface.getAllUsers()
                users = userResponse.data
            }
            MyApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(users = users)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(users: List<User>) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val user = users.find { it.user_name == username }
            if (user != null && user.user_password == password) {
                val intent = Intent(context, ScheduleActivity::class.java)
                startActivity(context, intent, null)
            } else {
                errorMessage = "Неверный логин или пароль"
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
    }
}