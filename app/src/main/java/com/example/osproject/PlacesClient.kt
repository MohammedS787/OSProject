package com.example.osproject

import com.example.osproject.model.Place
import com.example.osproject.model.SearchPrediction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/12/2023.
 */
interface PlacesClient {

    suspend fun search(query: String): List<SearchPrediction>

    suspend fun getPlace(placeId: String): Place
}