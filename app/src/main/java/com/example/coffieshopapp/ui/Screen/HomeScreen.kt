package com.example.coffieshopapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.coffieshopapp.R
import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.data.Model.Coffee
import com.example.coffieshopapp.ViewModel.CoffeeViewModel
import com.example.coffieshopapp.data.Model.Offer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: CoffeeViewModel = viewModel(), 
    onNavigateCoffeeList: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    onNavigateToFav: () -> Unit
) {
    val coffeeState by viewModel.coffeeState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    
    val categories = listOf("Espresso", "Cappuccino", "Americano", "Hot Coffee", "Cold Coffee")
    
    val offers = remember {
        listOf(
            Offer(1, "buy 2 and get 20% off", "$50.0", R.drawable.c1),
            Offer(2, "buy 3 and get 25% off", "$70.0", R.drawable.c2),
            Offer(3, "Holiday Special: 15% off", "$45.0", R.drawable.c3),
            Offer(4, "Morning Deal: 10% off", "$30.0", R.drawable.c4),
            Offer(5, "Weekend Treat: 30% off", "$100.0", R.drawable.c5),
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.coffee_logo),
                            contentDescription = "Coffee Logo",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Coffee Shop", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToFav) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF23140E)
                )
            )
        },
        bottomBar = {
            },
        containerColor = Color(0xFFECECEC)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        placeholder = { Text("Searching anything...", color = Color.White.copy(alpha = 0.7f)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text("Good Morning", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Grab your first coffee in this morning", color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(categories) { category ->
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable { searchQuery = category }
                            ) {
                                Text(category, color = Color.White, fontSize = 14.sp)
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(y = 80.dp) 
                ) {
                    val sectionTitle = if (searchQuery.isEmpty()) "Popular" else "Search Results"
                    SectionHeader(
                        title = sectionTitle, 
                        color = Color.White, 
                        onClick = onNavigateCoffeeList, 
                        showSeeAll = searchQuery.isEmpty()
                    )
                    
                    when (val state = coffeeState) {
                        is StateClass.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = Color.DarkGray)
                            }
                        }
                        is StateClass.Success -> {
                            val filteredCoffees = if (searchQuery.isEmpty()) {
                                state.data.take(6)
                            } else {
                                state.data.filter { coffee ->
                                    coffee.name?.contains(searchQuery, ignoreCase = true) == true ||
                                    coffee.category?.contains(searchQuery, ignoreCase = true) == true
                                }
                            }

                            if (filteredCoffees.isEmpty()) {
                                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                                    Text("No coffee found", color = Color.White)
                                }
                            } else {
                                LazyRow(
                                    contentPadding = PaddingValues(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(filteredCoffees) { coffee ->
                                        PopularCoffeeCard(coffee, onClick = { coffee.id?.let { onNavigateToDetails(it) } })
                                    }
                                }
                            }
                        }
                        is StateClass.Error -> {
                            Text(text = state.message, color = Color.Red, modifier = Modifier.padding(16.dp))
                        }
                        else -> {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            SectionHeader(title = "Available offers", color = Color.Black, showSeeAll = false)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(offers) { offer ->
                    OfferCard(offer)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SectionHeader(title: String, color: Color = Color.Black, showSeeAll: Boolean = true, onClick : () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = color, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        if (showSeeAll) {
            TextButton(onClick = onClick) {
                Text("see all", color = color.copy(alpha = 0.7f), fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun PopularCoffeeCard(coffee: Coffee, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 50.dp, bottom = 12.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val rating = (coffee.rating ?: 0.0).toInt()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(rating.coerceIn(0, 5)) { Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(14.dp)) }
                    repeat((5 - rating).coerceIn(0, 5)) { Icon(Icons.Default.Star, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(14.dp)) }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = coffee.name ?: "Unknown", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 16.sp, maxLines = 1)
                Text(text = coffee.description ?: "with milk", color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                Text(text = "${coffee.currency ?: "$"}${coffee.price ?: 0.0}", fontWeight = FontWeight.Bold, color = Color.DarkGray, fontSize = 14.sp)
            }
        }
        AsyncImage(
            model = coffee.imageUrl ?: "",
            contentDescription = null,
            modifier = Modifier
                .width(110.dp).height(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )
    }
}

@Composable
fun OfferCard(offer: Offer) {
    Card(
        modifier = Modifier.width(210.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF23140E))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = offer.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = offer.title, color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = offer.price,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}
