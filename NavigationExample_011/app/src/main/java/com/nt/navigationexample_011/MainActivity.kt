package com.nt.navigationexample_011

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nt.navigationexample_011.ui.theme.NavigationExample_011Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExample_011Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun FirstScreen(nameParam: String = "no name", ageParam: String = "no age", navigationToSecondScreen: (String, String) -> Unit) {
    var name by remember { mutableStateOf(nameParam) }
    var age by remember { mutableStateOf(ageParam) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Screen One", fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        OutlinedTextField(value = name, onValueChange = {name = it})
        OutlinedTextField(value = age, onValueChange = {age = it})
        Button(onClick = { navigationToSecondScreen(name, age) }) {
            Text(text = "Go to Two")
        }
        Text(text = "name: $name", fontSize = 24.sp)
        Text(text = "age: $age", fontSize = 24.sp)
    }
}

@Composable
fun SecondScreen(name: String, age: String, navigationToFirstScreen: (String, String) -> Unit, navigationToThirdScreen: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Screen Two", fontSize = 24.sp)
        Text(text = "Welcome to second screen!", fontSize = 24.sp)
        Text(text = "Your name is: $name", fontSize = 24.sp)
        Text(text = "Your age is: $age", fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        Button(onClick = { navigationToFirstScreen(name, age) }) {
            Text(text = "Go to One")
        }
        /*
        Button(onClick = { navigationToThirdScreen() }) {
            Text(text = "Go to Three")
        }
         */
    }
}

@Composable
fun ThirdScreen(navigationToFirstScreen: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Screen Three", fontSize = 24.sp)
        Text(text = "Welcome to third screen!", fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        Button(onClick = { navigationToFirstScreen() }) {
            Text(text = "Go to One")
        }
    }
}

@Composable
fun MyApp() {
    val firstScreen = "firstscreen"
    val secondScreen = "secondscreen"
    val thirdScreen = "thirdscreen"

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "$firstScreen/Ethan/24") {
        composable(route = "$firstScreen/{name}/{age}") {
            val name = it.arguments?.getString("name") ?: "no name"
            val age = it.arguments?.getString("age") ?: "no age"
            FirstScreen(
                name,
                age,
                { name, age -> navController.navigate("$secondScreen/$name/$age") })
        }
        composable(route = "$secondScreen/{name}/{age}") {
            val name = it.arguments?.getString("name") ?: "no name"
            val age = it.arguments?.getString("age") ?: "no age"
            SecondScreen(
                name,
                age,
                { name, age -> navController.navigate("$firstScreen/$name/$age") },
                { navController.navigate(thirdScreen) })
        }
        /*
        composable(route = thirdScreen) { ThirdScreen(
            {navController.navigate(firstScreen)})
        }
         */
    }
}

