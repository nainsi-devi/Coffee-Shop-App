package com.example.coffieshopapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.coffieshopapp.R
import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.data.Model.Coffee
import com.example.coffieshopapp.ViewModel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffieListScreen(
    viewModel: CoffeeViewModel = viewModel(),
    onBack: () -> Unit,
    onNavigateToDetails: (Int) -> Unit
) {
    val uiState by viewModel.coffeeState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Coffee List",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF23140E)
                )
            )
        },
        containerColor = Color(0xFF23140E)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when (val state = uiState) {
                is StateClass.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is StateClass.Success -> {
                    val coffees = state.data ?: emptyList()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(top = 45.dp, bottom = 16.dp), // Added top padding for overlapping image
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(coffees) { coffee ->
                            CoffeeCard(coffee, onClick = { onNavigateToDetails(coffee.id ?: 0) })
                        }
                    }
                }
                is StateClass.Error -> {
                    val message = state.message ?: "An unknown error occurred"
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = message, color = Color.White, textAlign = TextAlign.Center)
                            Button(onClick = { viewModel.fetchCoffees() }, modifier = Modifier.padding(top = 8.dp)) {
                                Text("Retry")
                            }
                        }
                    }
                }
                else -> {
                    // Idle state
                }
            }
        }
    }
}

@Composable
fun CoffeeCard(coffee: Coffee, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth().padding(10.dp, 8.dp)
                .padding(top = 40.dp), // Space for image overlapping
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 50.dp, bottom = 20.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val rating = (coffee.rating ?: 0.0).toInt()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(rating.coerceIn(0, 5)) { Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp)) }
                    repeat((5 - rating).coerceIn(0, 5)) { Icon(Icons.Default.Star, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(14.dp)) }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = coffee.name ?: "Unknown", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 15.sp, maxLines = 1)
                Text(text = coffee.description ?: "with milk", color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                Text(text = "${coffee.currency ?: "$"}${coffee.price ?: 0.0}", fontWeight = FontWeight.Bold, color = Color.DarkGray, fontSize = 14.sp)
            }
        }
        AsyncImage(
            model = coffee.imageUrl ?: "",
            contentDescription = null,
            modifier = Modifier
                .width(110.dp).height(90.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.coffee_logo)
        )
    }
}
