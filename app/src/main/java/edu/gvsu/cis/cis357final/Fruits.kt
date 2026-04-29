package edu.gvsu.cis.cis357final

import kotlinx.serialization.Serializable

@Serializable
data class Fruit(
    val name: String,
    val family: String,
    val genus: String,
    val order: String,
)

@Serializable
data class Nutritions(
    val calories: Double,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
)