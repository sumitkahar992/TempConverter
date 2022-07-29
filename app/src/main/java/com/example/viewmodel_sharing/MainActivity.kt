package com.example.viewmodel_sharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viewmodel_sharing.ui.theme.ViewmodelsharinGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSystem()
        }
    }
}


@Composable
fun NavigationSystem() {
    val navController = rememberNavController()
    val viewModel: ConversionViewModel = viewModel()

    NavHost(
        navController = navController, startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("result") { ResultScreen(navController, viewModel) }
    }
}


@Composable
fun HomeScreen(navController: NavController, viewModel: ConversionViewModel) {
    var temp by remember { mutableStateOf("") }
    val fahrenheit = temp.toIntOrNull() ?: 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.65f)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = temp,
            onValueChange = { temp = it },
            label = { Text(text = "Fahrenheit") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(.75f)
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(.75f),
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            onClick = {
                if (fahrenheit !in 1..160) return@Button

                viewModel.onCalculate(fahrenheit)
                navController.navigate("result")

            }) {
            Text(text = "Calculate")
        }

    }
}


@Composable
fun ResultScreen(navController: NavController, viewModel: ConversionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${viewModel.fahrenheit.toString()} °F = ${viewModel.celsiusText} °C",
            style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(onClick = {
            navController.navigate("home")
        }) {
            Text(text = "Calculate Again")

        }

    }
}





























