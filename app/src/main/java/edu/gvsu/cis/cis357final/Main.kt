package edu.gvsu.cis.cis357final

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.gvsu.cis.cis357final.viewmodel.MyViewModel

@Composable
fun MainScreen(
    viewModel: MyViewModel,
    goToSettingsScreen: () -> Unit,
    goToFruitScreen: (Fruit) -> Unit,
    fruits: List<Fruit>,
    showNutrients: Boolean
) {
    var minCalories by remember { mutableStateOf(TextFieldValue("")) }
    var maxCalories by remember { mutableStateOf(TextFieldValue("")) }
    var sortByCalories by remember { mutableStateOf(true) }

    // Apply calorie range filter
    val filteredFruits = fruits.filter {
        val minCal = minCalories.text.toDoubleOrNull() ?: 0.0
        val maxCal = maxCalories.text.toDoubleOrNull() ?: Double.MAX_VALUE
        it.nutrition.calories in minCal..maxCal
    }

    // Apply sorting by calories or sugar
    val sortedFruits = when {
        sortByCalories -> filteredFruits.sortedBy { it.nutrition.calories }
        else -> filteredFruits.sortedBy { it.nutrition.sugar }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6))
            .padding(16.dp)
    ) {
        Button(
            onClick = goToSettingsScreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Range Filter
        Text("Calorie Filter:")
        Row(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = minCalories,
                onValueChange = { minCalories = it },
                modifier = Modifier.weight(1f).padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(Modifier.background(Color.LightGray).padding(8.dp)) { innerTextField() }
                }
            )
            Text("-", modifier = Modifier.align(Alignment.CenterVertically))
            BasicTextField(
                value = maxCalories,
                onValueChange = { maxCalories = it },
                modifier = Modifier.weight(1f).padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(Modifier.background(Color.LightGray).padding(8.dp)) { innerTextField() }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sortedFruits) { fruit ->
                val backgroundColor = if (fruit.isStarred) Color.Green else Color.White

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(backgroundColor)
                        .clickable {
                            goToFruitScreen(fruit)
                        }
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = fruit.name)
                        Text(text = fruit.family)
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (showNutrients) {
                            val totalNutrients = fruit.nutrition.calories + fruit.nutrition.fat + fruit.nutrition.sugar +
                                    fruit.nutrition.carbohydrates + fruit.nutrition.protein
                            Text(text = "Nutrients: ${totalNutrients} grams")
                        } else {
                            Text(text = "${fruit.nutrition.calories} cal")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { sortByCalories = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sort by Calories")
            }
            Button(
                onClick = { sortByCalories = false },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sort by Sugar")
            }
        }
    }
}