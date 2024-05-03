package com.paulklauser.cars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.paulklauser.cars.makes.MAKES_ROUTE
import com.paulklauser.cars.makes.makes
import com.paulklauser.cars.models.models
import com.paulklauser.cars.models.navigateToModel
import com.paulklauser.cars.trimdetail.navigateToTrimDetail
import com.paulklauser.cars.trimdetail.trimDetail
import com.paulklauser.cars.ui.theme.CarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                                navController.navigateToModel(makeId)
                            }
                        )
                        models(
                            onNavigateBack = { navController.popBackStack() },
                            onTrimSelected = { trimId ->
                                navController.navigateToTrimDetail(trimId)
                            }
                        )
                        trimDetail(onNavigateBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}