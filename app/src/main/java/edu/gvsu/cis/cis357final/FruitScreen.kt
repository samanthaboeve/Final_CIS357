package edu.gvsu.cis.cis357final

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import edu.gvsu.cis.cis357final.viewmodel.MyViewModel

@Composable
fun FruitScreen(
    fruit: Fruit,
    viewModel: MyViewModel,
    onBack: () -> Unit
) {
    var isStarred by remember { mutableStateOf(fruit.isStarred) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFADD8E6))
    ) {
        Text("${fruit.name}")
        Text("Family: ${fruit.family}")
        Text("Genus: ${fruit.genus}")
        Text("Order: ${fruit.order}")

        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Nutrition:")
        val nutrition = fruit.nutrition
        Text("Calories: ${nutrition.calories} cal")
        Text("Fat: ${nutrition.fat} grams")
        Text("Sugar: ${nutrition.sugar} grams")
        Text("Carbohydrates: ${nutrition.carbohydrates} grams")
        Text("Protein: ${nutrition.protein} grams")

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    val updatedFruit = fruit.copy(isStarred = !fruit.isStarred)
                    if (updatedFruit.isStarred) {
                        viewModel.addNewFruit(updatedFruit)
                    } else {
                        viewModel.removeFruit(updatedFruit)
                    }
                }
            ) {
                Text(text = if (fruit.isStarred) "Unstar" else "Star")
            }


            Button(
                onClick = onBack
            ) {
                Text("Back")
            }
        }
    }
}