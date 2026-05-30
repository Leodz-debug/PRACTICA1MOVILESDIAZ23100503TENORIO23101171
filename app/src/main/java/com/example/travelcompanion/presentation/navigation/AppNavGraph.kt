package com.example.travelcompanion.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelcompanion.data.model.Destination
import com.example.travelcompanion.presentation.budget.TravelBudgetScreen
import com.example.travelcompanion.presentation.destinations.TouristDestinationsScreen
import com.example.travelcompanion.presentation.home.MainMenuScreen
import com.example.travelcompanion.presentation.luggage.LuggageCalculatorScreen
import com.example.travelcompanion.presentation.permissions.LocationPermissionScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.MainMenu.route
    ) {
        composable(Destination.MainMenu.route) {
            MainMenuScreen(onNavigate = { destination ->
                navController.navigate(destination.route)
            })
        }
        composable(Destination.LuggageCalculator.route) {
            LuggageCalculatorScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.TravelBudget.route) {
            TravelBudgetScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.TouristDestinations.route) {
            TouristDestinationsScreen(onBack = { navController.popBackStack() })
        }
        composable(Destination.LocationPermission.route) {
            LocationPermissionScreen(onBack = { navController.popBackStack() })
        }
    }
}
