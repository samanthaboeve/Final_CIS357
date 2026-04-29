package edu.gvsu.cis.cis357final

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object MainScreen

    @Serializable
    data class FruitScreen(val name: String)

    @Serializable
    data object SettingsScreen
}