package com.nt.shoppinglistapp_007

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import java.util.Locale

data class LocationData(val latitude: Double, val longitude: Double)
data class GeocodingResponse(val results: List<GeocodingResult>, val status: String)
data class GeocodingResult(val formatted_address: String)

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationData?>((null))
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(location: LocationData) {
        _location.value = location
    }

    fun fetchAddress(latlng: String) {
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng = latlng,
                    apikey = ""
                )
                _address.value = result.results
                Log.d("Results", "$result")
            }
        } catch (e: Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }
}

@Composable
fun LocationSelectionScreen(location: LocationData, onLocationSelected: (LocationData) -> Unit) {
    val userLocation = remember { mutableStateOf(LatLng(location.latitude, location.longitude)) }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(modifier = Modifier
            .weight(1f)
            .padding(top = 16.dp), cameraPositionState = cameraPositionState, onMapClick = { userLocation.value = it }) {
            Marker(state = MarkerState(position = userLocation.value))
        }
        Button(onClick = {
            var newLocation = LocationData(userLocation.value.latitude, userLocation.value.longitude)
            onLocationSelected(newLocation)
        }) {
            Text(text = "Change Location")
        }
    }
}

class LocationUtilities(val context: Context) {
    private val _fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate(locationViewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val locationData = LocationData(latitude = it.latitude, longitude = it.longitude)
                    locationViewModel.updateLocation(locationData)
                }
            }
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper()) // @SuppressLint("MissingPermission")
    }

    fun convertGeocodeToAddress(location: LocationData): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val coordinates = LatLng(location.latitude, location.longitude)
        val address: MutableList<Address>? = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)
        if(address?.isNotEmpty() == true) {
            return address[0].getAddressLine(0)
        } else {
            return "No address found."
        }
    }

}