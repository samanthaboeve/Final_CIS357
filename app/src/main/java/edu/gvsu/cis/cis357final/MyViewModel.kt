package edu.gvsu.cis.cis357final.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.gvsu.cis.cis357final.Fruit
import edu.gvsu.cis.cis357final.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    // Creating HTTP Client
    private val userClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    private val _fruits = MutableStateFlow<List<Fruit>>(emptyList())
    val fruits: StateFlow<List<Fruit>> = _fruits

    // Automatically loads list
    init {
        fetchFruits()
    }

    // Fetching from API
    fun fetchFruits() {
        viewModelScope.launch(Dispatchers.IO) {
            val apiUrl = "https://www.fruityvice.com/api/fruit/all"
            val fruitsList: List<Fruit> = userClient.get(apiUrl)
            _fruits.value = fruitsList
        }
    }


    fun sortByCalories() {
        viewModelScope.launch(Dispatchers.IO) {
            _fruits.value = _fruits.value.sortedBy { it.nutritions.calories }
        }
    }


    fun sortBySugar() {
        viewModelScope.launch(Dispatchers.IO) {
            _fruits.value = _fruits.value.sortedBy { it.nutritions.sugar }
        }
    }

    // Adding or removing from database
    fun toggleStar(fruit: Fruit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (fruit.isStarred) {
                db.fruitDao().deleteFruit(fruit)
            } else {
                db.fruitDao().insertFruit(fruit)
            }
            fetchFruits()
        }
    }

    fun sortByCalories() {
        viewModelScope.launch(Dispatchers.IO) {
            _fruits.value = _fruits.value.sortedBy { it.nutritions.calories }
        }
    }


    fun sortBySugar() {
        viewModelScope.launch(Dispatchers.IO) {
            _fruits.value = _fruits.value.sortedBy { it.nutritions.sugar }
        }
    }

    fun sortByNutrients() {
        _fruits.value = _fruits.value.sortedBy { fruit ->
            // Calculating the total nutrients
            val totalNutrients = fruit.nutrition.calories + fruit.nutrition.fat + fruit.nutrition.sugar +
                    fruit.nutrition.carbohydrates + fruit.nutrition.protein
            totalNutrients
        }
    }
}