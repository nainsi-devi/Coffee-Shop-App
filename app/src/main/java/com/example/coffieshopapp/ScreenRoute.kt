package com.example.coffieshopapp

sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int
) {

    object Splash : Screen(
        "splash",
        "Splash",
        R.drawable.home
    )

    object Login : Screen(
        "login",
        "Login",
        R.drawable.home
    )

    object SignUp : Screen(
        "signup",
        "Sign Up",
        R.drawable.home
    )

    object Home : Screen(
        "home",
        "Home",
        R.drawable.home
    )

    object Cart : Screen(
        "cart",
        "Cart",
        R.drawable.ic_cart
    )
    object Profile : Screen(
        "profile",
        "Profile",
        R.drawable.ic_profile
    )

    object Fav : Screen(
        "fav",
        "Favorite",
        R.drawable.home
    )

    object Settings : Screen(
        "settings",
        "Settings",
        R.drawable.home
    )

    object UpdatePassword : Screen(
        "update_password",
        "Update Password",
        R.drawable.home
    )

    object About : Screen(
        "about",
        "About",
        R.drawable.ic_about
    )

    object HelpSupport : Screen(
        "help_support",
        "Help & Support",
        R.drawable.ic_support
    )

    object MyOrder : Screen(
        "myOrder", "MyOrder", R.drawable.ic_mu_order
    )

    object CoffeeList : Screen(
        "coffeeList",
        "CoffeeList",
        R.drawable.home
    )

    object Details : Screen(
        "details/{coffeeId}",
        "Details",
        R.drawable.home
    ) {
        fun createRoute(coffeeId: Int) = "details/$coffeeId"
    }
}
