package com.nt.location_012

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nt.location_012.ui.theme.Location_012Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val locationViewModel: LocationViewModel = viewModel() // used the ide to add the dependency for import androidx.lifecycle.viewmodel.compose.viewModel
            Location_012Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(locationViewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(locationViewModel: LocationViewModel) {
    val context = LocalContext.current
    val locationUtilities = LocationUtilities(context)

    LocationDisplay(locationViewModel = locationViewModel, locationUtilities = locationUtilities, context = context)
}

@Composable
fun LocationDisplay(locationViewModel: LocationViewModel, locationUtilities: LocationUtilities, context: Context) {
    val location = locationViewModel.location.value
    val address = location?.let { locationUtilities.convertGeocodeToAddress(location) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                // everything is good
                Toast.makeText(context, "You have the correct permissions granted for the application.", Toast.LENGTH_LONG).show()
                locationUtilities.requestLocationUpdate(locationViewModel)
            } else {
                // ask for permissions
                val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) ||  ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if(rationalRequired) {
                    // If rational is required provide a reason to the user
                    Toast.makeText(context, "Location permission is required for this application to function.", Toast.LENGTH_LONG).show()
                } else {
                    // Go to android settings to make change
                    Toast.makeText(context, "Go to the android settings and give the application location permissions.", Toast.LENGTH_LONG).show()
                }
            }
        })

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if(location != null) {
            Text(text = "latitude, longitude")
            Text(text = "${location.latitude}, ${location.longitude}")
        } else {
            Text(text = "Location not available.")
        }
        if(address != null) {
            Text(text = "Address")
            Text(text = "$address")
        } else {
            Text(text = "Address could not be found.")
        }
        Button(onClick = {
            if(locationUtilities.hasLocationPermission(context)) {
                locationUtilities.requestLocationUpdate(locationViewModel)
            } else {
                // request permission for a list of permissions
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }) {
            Text(text = "Get location")
        }
    }
}