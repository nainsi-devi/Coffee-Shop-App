package com.example.coffieshopapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.coffieshopapp.data.Model.CartViewModel
import com.example.coffieshopapp.ViewModel.CoffeeViewModel
import com.example.coffieshopapp.StateClass
import com.example.coffieshopapp.ViewModel.FavViewModel

@Composable
fun DetailsScreen(
    coffeeId: Int,
    onBack: () -> Unit,
    viewModel: CoffeeViewModel,
    cartViewModel: CartViewModel,
    favViewModel: FavViewModel
) {
    val context = LocalContext.current
    val uiState by viewModel.coffeeState.collectAsState()
    val coffee = remember(uiState, coffeeId) { viewModel.getCoffeeById(coffeeId) }
    var quantity by remember { mutableIntStateOf(1) }
    
    val isFavorite by (coffee?.id?.let { favViewModel.isFavorite(it).collectAsState(initial = false) } ?: remember { mutableStateOf(false) })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (coffee != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 50.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = { 
                            favViewModel.toggleFavorite(context, coffee)
                        },
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Coffee Image
                AsyncImage(
                    model = coffee.imageUrl ?: "",
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Quantity Selector pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.LightGray.copy(alpha = 0.3f),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            TextButton(
                                onClick = { if (quantity > 1) quantity-- },
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(20.dp)
                            ) {
                                Text("-", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = quantity.toString(),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            TextButton(
                                onClick = { quantity++ },
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(20.dp)
                            ) {
                                Text("+", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name and Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = coffee.name ?: "Coffee Detail",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${coffee.currency ?: "$"}${coffee.price ?: 0.0}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Rating Stars
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    val rating = (coffee.rating ?: 0.0).toInt()
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < rating) Color(0xFFFFD700) else Color.Gray.copy(alpha = 0.3f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Description
                Text(
                    text = coffee.description ?: "with milk",
                    color = Color.Black.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                Button(
                    onClick = { 
                        cartViewModel.addToCart(coffee, quantity)
                        Toast.makeText(context, "${coffee.name} added to cart", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A150D)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("ADD TO CART", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { 
                        cartViewModel.addToCart(coffee, quantity)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A150D)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("BUY", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (uiState is StateClass.Loading) {
                    CircularProgressIndicator(color = Color.Black)
                } else {
                    Text("Details not found", color = Color.Black)
                }
            }
        }
    }
}
