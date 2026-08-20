package com.example.coffieshopapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffieshopapp.R
import com.example.coffieshopapp.ViewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBack: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: AuthViewModel
) {



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Setting & Privacy",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
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
    ) { innerPadding ->
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 30.dp)

        ) {
            Items("Setting & Privacy",R.drawable.ic_setting, Color.Black)
            Spacer(modifier = Modifier.height(5.dp))
            Items("Help & Support", R.drawable.ic_support,Color.Black)
            Spacer(modifier = Modifier.height(5.dp))
            Items("About",R.drawable.ic_about,Color.Black)
            Spacer(modifier = Modifier.height(5.dp))
            Items("Log Out", R.drawable.ic_logout,Color.Red)
        }

        }

    }


@Composable
fun Items(title : String, icon : Int, color: Color ){

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(icon), title,tint = color)
            Spacer(modifier = Modifier.width(5.dp))
        Text(title, fontSize = 18.sp, color = Color.Black)
    }
}
