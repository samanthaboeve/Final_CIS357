package edu.gvsu.cis.cis357final

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onCaloriesClick: () -> Unit,
    onNutrientsClick: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            modifier = Modifier.padding(bottom = 24.dp),
        )

        Button(
            onClick = onCaloriesClick,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Calories")
        }

        Button(
            onClick = onNutrientsClick,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Nutrients")
        }

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}