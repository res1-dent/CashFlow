package ru.hometech.common.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

abstract class NavigationItem(open val route: String)

fun NavController.navigate(navItem: NavigationItem) {
    Log.d("Navigation", "Navigating to ${navItem.route}")
    navigate(navItem.route)
}



fun NavController.navigate(navItem: NavigationItem, builder: NavOptionsBuilder.() -> Unit) {
    Log.d("Navigation", "Navigating to ${navItem.route}")
    navigate(navItem.route, builder)
}