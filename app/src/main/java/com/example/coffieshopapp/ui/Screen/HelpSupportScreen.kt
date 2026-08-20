package com.example.coffieshopapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.google.type.ColorOrBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(onBack: () -> Unit) {
    val faqs = listOf(
        HelpFAQ(
            "How to order coffee?",
            "Browse through our popular coffee list or use the search bar. Click on a coffee card to see details, choose your quantity, and click 'Add to Cart'. Finally, go to your cart and click 'Proceed to Checkout'."
        ),
        HelpFAQ(
            "What payment methods are supported?",
            "Currently, we support multiple secure payment options including Credit/Debit cards, Digital Wallets, and Cash on Delivery for select locations."
        ),
        HelpFAQ(
            "How can I track my order?",
            "Once an order is placed, you can monitor its progress in real-time. Head over to the 'My Orders' section from your profile to see the current status of your delivery."
        ),
        HelpFAQ(
            "I forgot my password. What should I do?",
            "If you can still log in but want to change your password, go to 'Profile' -> 'Settings' -> 'Update Password'. If you cannot log in, use the 'Forgot Password' link on the login screen (if available) or contact our support team."
        ),
        HelpFAQ(
            "How to contact customer support?",
            "You can reach out to our dedicated support team via email at support@coffeeshop.com or call us at +1 234 567 890 between 9 AM and 6 PM."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support", color = Color.White) },
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Common Problems & Solutions",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth(), textAlign = TextAlign.Center
                )
            }
            items(faqs) { faq ->
                FAQItem(faq)
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF311A12)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Still need help?",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Our support team is always here to assist you. Send us a detailed message about your issue.",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* Implement contact logic */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD17842)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Contact Us", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

data class HelpFAQ(val question: String, val answer: String)

@Composable
fun FAQItem(faq: HelpFAQ) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF311A12)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Q: ${faq.question}",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "A: ${faq.answer}",
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}
