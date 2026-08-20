package com.example.coffieshopapp.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.ViewModel.AuthViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun SignUPScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()
    val shape = RoundedCornerShape(8.dp)

    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is StateClass.Success) {
            onSignUpSuccess()
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAEADF))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create New Account",
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            shape = shape,
            label = { Text("Full Name", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            shape = shape,
            label = { Text("Email", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            shape = shape,
            label = { Text("Password", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.Black)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            shape = shape,
            label = { Text("Confirm Password", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.Black)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(15.dp))

        if (authState is StateClass.Loading) {
            CircularProgressIndicator(color = Color(0xFF2A150D))
        } else {
            Button(
                onClick = {if (name.isEmpty()){
                    Toast.makeText(context, " Enter your name", Toast.LENGTH_SHORT).show()
                }
                    else if (email.isEmpty()) {
                        Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
                    }
                else if (password.isEmpty()){
                    Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else if (password.length < 6) {
                    Toast.makeText(context, "Password must be > 6", Toast.LENGTH_SHORT).show()
                }
                else if (confirmPassword.isEmpty()) {
                    Toast.makeText(context, "Enter confirm password", Toast.LENGTH_SHORT).show()
                }

                  else if (password != confirmPassword) {
                      Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                    viewModel.signUp(context, name, email, password)

                }
                },
                shape = shape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A150D)),
            ) {
                Text("Sign Up", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (authState is StateClass.Error) {
            Text(
                text = (authState as StateClass.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }


        TextButton(
            onClick = onNavigateToLogin,
            shape = shape
        ) {
            Text("Already have an account? Login", color = Color(0xFF2A150D))
        }
    }
}
