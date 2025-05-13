package com.example.mymapp

import com.google.android.gms.maps.model.LatLng

sealed interface  ParkingUiState {
    data class Found(val coordinates: List<List<LatLng>>) : ParkingUiState
    data object Looking: ParkingUiState
    data object WaitingForUser: ParkingUiState
}
