package com.example.coffieshopapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About App", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF23140E))
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Welcome to Coffee Shop App",
                color = Color(0xFFD17842),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "At Coffee Shop, we are dedicated to bringing you the finest coffee experience right at your fingertips. Whether you're a fan of rich espressos or creamy lattes, our app is designed to help you discover and order your favorite blends with ease.",
                color = Color.DarkGray,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "App Features",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            FeatureItem(
                title = "Easy Ordering",
                description = "Browse through our extensive menu and order your favorite coffee in just a few taps."
            )
            FeatureItem(
                title = "Personalized Recommendations",
                description = "Receive coffee suggestions based on your taste preferences and past orders."
            )
            FeatureItem(
                title = "Real-time Tracking",
                description = "Stay updated on your order status from preparation to delivery."
            )
            FeatureItem(
                title = "Exclusive Offers",
                description = "Get access to special discounts and rewards available only to our app users."
            )
            FeatureItem(
                title = "Secure Payments",
                description = "Experience seamless and safe transactions with multiple payment options."
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Version 1.0.0",
                color = Color.DarkGray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FeatureItem(title: String, description: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "• $title",
            color = Color(0xFFD17842),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = description,
            color = Color.DarkGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}
