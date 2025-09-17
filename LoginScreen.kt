package com.example.viewmodelhw

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen (modifier: Modifier = Modifier.Companion, viewModel: AuthViewModel) {
    @Composable
    fun LoginScreen(modifier: Modifier = Modifier.Companion, viewModel: AuthViewModel) {
        var isShaking by remember { mutableStateOf(false) }
        val offsetX by animateFloatAsState(
            targetValue = if (isShaking) 16f else 0f,
            animationSpec = repeatable(
                iterations = 3,
                animation = tween(durationMillis = 50, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            finishedListener = { isShaking = false })
        val isAuthenticated by viewModel.isAuthenticated.collectAsState()
        val email by viewModel.userEmail.collectAsState("me@test.com")
        var password by remember { mutableStateOf("abctraxy123") }
        LaunchedEffect(isAuthenticated) {
            isShaking = !(isAuthenticated ?: true)
        }
        Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Welcome to",
                modifier = Modifier.Companion.padding(top = 16.dp),
                fontSize = 24.sp, fontWeight = FontWeight.Companion.Bold
            )
            Image(
                modifier = modifier.fillMaxWidth(0.5f),
                painter = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Companion.FillWidth,
                colorFilter = if (isAuthenticated ?: false) ColorFilter.tint(Color.Green) else null,
                contentDescription = null,
            )
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.setUserEmail(it) },
                placeholder = {
                    Text("Enter email")
                })
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text("Enter password")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            ElevatedButton(onClick = {
                viewModel.authenticate(email, password)
            }, modifier = Modifier.offset(x = offsetX.dp)) {
                Text("Signin")
            }
        }
    }
}