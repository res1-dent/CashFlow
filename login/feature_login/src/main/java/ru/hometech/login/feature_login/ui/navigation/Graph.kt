package ru.hometech.login.feature_login.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.hometech.common.navigation.NavigationItem
import ru.hometech.common.navigation.navigate
import ru.hometech.common.sharedViewModel
import ru.hometech.login.feature_login.ui.create_user.CreateUserViewModel
import ru.hometech.login.feature_login.ui.create_user.content.CreateUserScreen
import ru.hometech.login.feature_login.ui.main.LoginMainViewModel
import ru.hometech.login.feature_login.ui.main.content.LoginMainScreen

enum class LoginScreen {
    Main, CreateUser
}

sealed class LoginGraph(route: String) : NavigationItem(route) {
    data object Main : LoginGraph(LoginScreen.Main.name)
    data object CreateUser : LoginGraph(LoginScreen.CreateUser.name)
}

fun NavGraphBuilder.featureLoginGraph(navController: NavController, route: String) {
    navigation(startDestination = LoginGraph.Main.route, route = route) {
        composable(route = LoginGraph.Main.route) {
            val viewModel = it.sharedViewModel<LoginMainViewModel>(navController = navController)
            LoginMainScreen(
                viewModel = viewModel,
                onNavigate = { destination ->
                    navController.navigate(destination) {
                        popUpTo(LoginGraph.Main.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = LoginGraph.CreateUser.route) {
            val viewModel = it.sharedViewModel<CreateUserViewModel>(navController = navController)
            CreateUserScreen(viewModel)
        }
    }
}