package com.example.travelcompanion.data.model

sealed class Destination(val route: String) {
    object MainMenu : Destination("main_menu")
    object LuggageCalculator : Destination("luggage_calculator")
    object TravelBudget : Destination("travel_budget")
    object TouristDestinations : Destination("tourist_destinations")
    object LocationPermission : Destination("location_permission")
}
