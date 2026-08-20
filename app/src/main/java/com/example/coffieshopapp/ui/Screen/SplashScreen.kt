package com.example.coffieshopapp.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.coffieshopapp.R

@Composable
fun SplashScreen(onStartedClick: () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Black.toArgb()
            window.navigationBarColor = Color.Black.toArgb()
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Background Image - Aligned to Top and cropped to fill
        Image(
            painter = painterResource(id = R.drawable.intro_pic),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopCenter
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 30.dp, bottom = 30.dp), // Lowered by reducing bottom padding
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Get Started",
                color = Color(0xFFD17842),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "find the best coffee\nfor you",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 25.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onStartedClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .height(50.dp)
                    .width(130.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD17842)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "STARTED",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
