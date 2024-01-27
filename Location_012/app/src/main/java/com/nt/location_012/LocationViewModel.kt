package com.nt.location_012

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class LocationData(val latitude: Double, val longitude: Double)

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null) // null default
    var location: State<LocationData?> = _location

    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

}