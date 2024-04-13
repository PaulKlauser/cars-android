package com.paulklauser.cars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.paulklauser.cars.makes.MAKES_ROUTE
import com.paulklauser.cars.makes.makes
import com.paulklauser.cars.models.MODELS_ROUTE_PATTERN
import com.paulklauser.cars.models.models
import com.paulklauser.cars.ui.theme.CarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarsTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = MAKES_ROUTE
                    ) {
                        makes(
                            onMakeSelected = { makeId ->
                                navController.navigate("$MODELS_ROUTE_PATTERN/$makeId")
                            }
                        )
                        models(onNavigateBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}