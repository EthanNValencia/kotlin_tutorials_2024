package com.nt.shoppinglistapp_007

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.nt.shoppinglistapp_007.ui.theme.ShoppingListApp_007Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListApp_007Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListApp_007Theme {
        // ShoppingList()
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val locationUtilities = LocationUtilities(context)
    val locationViewModel: LocationViewModel = viewModel()

    NavHost(navController = navController, startDestination = "shoppinglistscreen") {
        composable(route = "shoppinglistscreen") {
            ShoppingList(
                locationUtilities = locationUtilities,
                locationViewModel = locationViewModel,
                navController = navController,
                context = context,
                address = locationViewModel.address.value.firstOrNull()?.formatted_address ?: "No address"
            )
        }
        dialog(route = "locationscreen") {backstack -> 
            locationViewModel.location.value?.let { it1 -> 
                LocationSelectionScreen(location = it1, onLocationSelected = { // This is a different it, not the above it1.
                    locationViewModel.fetchAddress(latlng = "${it.latitude},${it.longitude}")
                    navController.popBackStack()
                })
            }
        }
    }

}