package com.example.coffieshopapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coffieshopapp.ViewModel.AuthViewModel
import com.example.coffieshopapp.data.Model.CartViewModel
import com.example.coffieshopapp.ViewModel.CoffeeViewModel
import com.example.coffieshopapp.ViewModel.FavViewModel
import com.example.coffieshopapp.ui.screen.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    
    val authViewModel: AuthViewModel = viewModel()
    val coffeeViewModel: CoffeeViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val favViewModel: FavViewModel = viewModel()

    val startDirection = Screen.Splash.route

    val showBottomBar = when (currentRoute) {
        Screen.Home.route, Screen.Cart.route, Screen.Profile.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDirection,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(onStartedClick = {
                    val destination = authViewModel.getStartDirection(context)
                    navController.navigate(destination) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignUp = {
                        navController.navigate(Screen.SignUp.route)
                    }
                )
            }
            composable(Screen.SignUp.route) {
                SignUPScreen(
                    onSignUpSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            composable(Screen.Home.route) { 
                HomeScreen(
                    viewModel = coffeeViewModel,
                    onNavigateCoffeeList = {
                        navController.navigate(Screen.CoffeeList.route)
                    },
                    onNavigateToDetails = { id: Int ->
                        navController.navigate(Screen.Details.createRoute(id))
                    },
                    onNavigateToFav = {
                        navController.navigate(Screen.Fav.route)
                    }
                ) 
            }
            composable(Screen.Cart.route) { 
                CartScreen(
                    viewModel = cartViewModel,
                    onBack = { navController.popBackStack() }
                ) 
            }
            composable(Screen.Profile.route) { 
                ProfileScreen(
                    onBackClick = { navController.popBackStack() }, 

                    onHelpClick = {
                        navController.navigate(Screen.HelpSupport.route)
                    },
                    onAboutClick = {
                        navController.navigate(Screen.About.route)
                    },
                    onLogoutClick = { 
                        authViewModel.logout(context)
                        navController.navigate(Screen.Login.route) { 
                            popUpTo(0) { inclusive = true } 
                        } 
                    },
                    onNavigateUpdatePass = {
                        navController.navigate(Screen.UpdatePassword.route)
                    }
                ) 
            }
            composable(Screen.Fav.route) {
                FavScreen(
                    viewModel = favViewModel,
                    onNavigateToDetails = { id ->
                        navController.navigate(Screen.Details.createRoute(id))
                    },
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }
            composable(Screen.Settings.route) { 
                SettingScreen(
                    onBack = { navController.popBackStack() },
                    onLogoutClick = {
                        authViewModel.logout(context)
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    viewModel = authViewModel
                ) 
            }
            composable(Screen.UpdatePassword.route) {
                UpdatePasswordScreen(
                    onBack = { navController.popBackStack() },
                    viewModel = authViewModel
                )
            }
            composable(Screen.About.route) {
                AboutScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.HelpSupport.route) {
                HelpSupportScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.CoffeeList.route) { 
                CoffieListScreen(
                    viewModel = coffeeViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigateToDetails = { id: Int ->
                        navController.navigate(Screen.Details.createRoute(id))
                    }
                ) 
            }
            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument("coffeeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val coffeeId = backStackEntry.arguments?.getInt("coffeeId") ?: 0
                DetailsScreen(
                    coffeeId = coffeeId,
                    onBack = { navController.popBackStack() },
                    viewModel = coffeeViewModel,
                    cartViewModel = cartViewModel,
                    favViewModel = favViewModel
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Cart,
        Screen.Profile
    )
    NavigationBar(
        modifier = Modifier.shadow(elevation = 15.dp),
        containerColor = Color.White, 
        tonalElevation = 0.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val selectedColor = Color(0xFF6F4E37) // Coffee Brown
        val unselectedColor = Color.DarkGray

        items.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    val iconImage = if (screen is Screen.Fav) {
                        if (isSelected) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                    } else {
                        null
                    }
                    
                    if (iconImage != null) {
                        Icon(
                            imageVector = iconImage,
                            contentDescription = screen.title,
                            modifier = Modifier.size(if (isSelected) 30.dp else 24.dp),
                            tint = if (isSelected) selectedColor else unselectedColor
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.title,
                            modifier = Modifier.size(if (isSelected) 30.dp else 24.dp),
                            tint = if (isSelected) selectedColor else unselectedColor
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (isSelected) selectedColor else unselectedColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
