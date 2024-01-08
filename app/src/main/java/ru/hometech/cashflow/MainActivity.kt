package ru.hometech.cashflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.hometech.cashflow.navigation.SplashScreen
import ru.hometech.cashflow.ui.theme.CashFlowTheme
import ru.hometech.common.navigation.navigate
import ru.hometech.login.core_login.navigation.LoginFeature
import ru.hometech.login.feature_login.ui.navigation.featureLoginGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashFlowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = SplashScreen.route,
                    ) {
                        composable(SplashScreen.route) {
                            LaunchedEffect(key1 = Unit) {
                                delay(1000)
                                navController.navigate(LoginFeature) {
                                    popUpTo(SplashScreen.route){
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        featureLoginGraph(navController, LoginFeature.route)
                    }
                }
            }
        }
    }
}