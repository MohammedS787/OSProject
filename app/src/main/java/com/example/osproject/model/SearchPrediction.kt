package com.example.osproject.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/12/2023.
 */
data class SearchPrediction(
    val placeId: String,
    val primaryText: String,
    val secondaryText: String
)
