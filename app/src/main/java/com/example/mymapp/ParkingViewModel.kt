package com.example.mymapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call

class ParkingViewModel(
    private val callFactory: Call.Factory,
    private val apiKey: String?
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class ParkingViewModelFactory(private val repository: Call.Factory, private val apiKey: String?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ParkingViewModel::class.java)) {
                return ParkingViewModel(repository, apiKey) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val _state = MutableStateFlow<ParkingUiState>(ParkingUiState.WaitingForUser)
    val state: StateFlow<ParkingUiState> = _state

    fun getParkingSpace(latitude: Double, longitude: Double) {
        val request = Request.Builder()
            .url("https://openparking.stockholm.se/LTF-Tolken/v1/ptillaten/within?radius=100&lat=${latitude}&lng=${longitude}&outputFormat=json&apiKey=${apiKey}")
            .build()
        _state.update { ParkingUiState.Looking }

        viewModelScope.runCatching {
            callFactory.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        for ((name, value) in response.headers) {
                            println("$name: $value")
                        }

                        val body = response.body!!.string()

                        if (body.isNotEmpty()) {
                            val list = mutableListOf<List<LatLng>>()
                            val json = Json {
                                ignoreUnknownKeys = true
                            }
                            val featureCollection = json.decodeFromString<FeatureCollection>(body)
                            featureCollection.features.filter { it.geometry.type == "LineString" }
                                .forEach { it ->
                                    val featureList = mutableListOf<LatLng>()
                                    it.geometry.coordinates.chunked(2)
                                        .map {
                                            featureList.add(
                                                LatLng(
                                                    it.get(0).get(1),
                                                    it.get(0).get(0)
                                                )
                                            )
                                        }
                                    list.add(featureList)
                                }
                            _state.update { ParkingUiState.Found(list) }
                        }
                    }
                }
            })
        }
    }

}


@Serializable
data class FeatureCollection(
    val type: String,
    val features: List<Feature>
)

@Serializable
data class Feature(
    val type: String,
    val id: String,
    val geometry: Geometry,
    val geometry_name: String,
    val properties: Properties,
    val bbox: List<Double>
)

@Serializable
data class Geometry(
    val type: String,
    val coordinates: List<List<Double>>
)

@Serializable
data class Properties(
    val FEATURE_OBJECT_ID: Int,
    val FEATURE_VERSION_ID: Int,
    val EXTENT_NO: Int,
    val VALID_FROM: String,
    val VEHICLE: String,
    val START_TIME: Int? = null,
    val END_TIME: Int? = null,
    val START_WEEKDAY: String? = null,
    val MAX_HOURS: Int? = null,
    val CITATION: String,
    val STREET_NAME: String,
    val CITY_DISTRICT: String,
    val PARKING_DISTRICT: String,
    val OTHER_INFO: String? = null,
    val VF_PLATS_TYP: String,
    val ADDRESS: String,
    val RDT_URL: String,
    val PARKING_RATE: String,
    val FID: Int
)

// Sample
/* {
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "id": "LTFR_P_TILLATEN.12061",
      "geometry": {
        "type": "LineString",
        "coordinates": [
          [
            18.059971,
            59.33
          ],
          [
            18.059661,
            59.330343
          ]
        ]
      },
      "geometry_name": "GEOMETRY",
      "properties": {
        "FEATURE_OBJECT_ID": 19769471,
        "FEATURE_VERSION_ID": 1,
        "EXTENT_NO": 1,
        "VALID_FROM": "2022-06-22T22:00:00Z",
        "VEHICLE": "fordon",
        "START_TIME": 600,
        "END_TIME": 0,
        "START_WEEKDAY": "måndag",
        "MAX_HOURS": 1,
        "CITATION": "0180 2022-02696",
        "STREET_NAME": "Vasagatan",
        "CITY_DISTRICT": "Norrmalm",
        "PARKING_DISTRICT": "City",
        "OTHER_INFO": "Servicetid måndag 00:00-06:00",
        "VF_PLATS_TYP": "P-avgift endast besök",
        "ADDRESS": "Vasagatan 14 - 18",
        "RDT_URL": "https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2022&LopNr=02696",
        "PARKING_RATE": "taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering",
        "FID": 12061
      },
      "bbox": [
        18.059661,
        59.33,
        18.059971,
        59.330343
      ]
    },
    {
      "type": "Feature",
      "id": "LTFR_P_TILLATEN.12411",
      "geometry": {
        "type": "LineString",
        "coordinates": [
          [
            18.059725,
            59.329582
          ],
          [
            18.05972,
            59.329583
          ]
        ]
      },
      "geometry_name": "GEOMETRY",
      "properties": {
        "FEATURE_OBJECT_ID": 19525990,
        "FEATURE_VERSION_ID": 1,
        "EXTENT_NO": 1,
        "VALID_FROM": "2022-04-02T22:00:00Z",
        "VEHICLE": "cykel",
        "CITATION": "0180 2022-00936",
        "STREET_NAME": "Centralplan",
        "CITY_DISTRICT": "Norrmalm",
        "PARKING_DISTRICT": "City",
        "VF_PLATS_TYP": "Reserverad p-plats cykel",
        "ADDRESS": "Centralplan 3",
        "RDT_URL": "https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2022&LopNr=00936",
        "PARKING_RATE": "avgiftsfri",
        "FID": 12411
      },
      "bbox": [
        18.05972,
        59.329582,
        18.059725,
        59.329583
      ]
    },
    {
      "type": "Feature",
      "id": "LTFR_P_TILLATEN.12492",
      "geometry": {
        "type": "LineString",
        "coordinates": [
          [
            18.059595,
            59.329452
          ],
          [
            18.059559,
            59.329456
          ]
        ]
      },
      "geometry_name": "GEOMETRY",
      "properties": {
        "FEATURE_OBJECT_ID": 20277361,
        "FEATURE_VERSION_ID": 1,
        "EXTENT_NO": 1,
        "VALID_FROM": "2023-02-07T23:00:00Z",
        "VEHICLE": "fordon",
        "MAX_HOURS": 1,
        "CITATION": "0180 2023-00170",
        "STREET_NAME": "Centralplan",
        "CITY_DISTRICT": "Norrmalm",
        "PARKING_DISTRICT": "City",
        "VF_PLATS_TYP": "P-avgift endast besök",
        "ADDRESS": "Centralplan 1",
        "RDT_URL": "https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2023&LopNr=00170",
        "PARKING_RATE": "taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering",
        "FID": 12492
      },
      "bbox": [
        18.059559,
        59.329452,
        18.059595,
        59.329456
      ]
    },
    {
      "type": "Feature",
      "id": "LTFR_P_TILLATEN.14276",
      "geometry": {
        "type": "LineString",
        "coordinates": [
          [
            18.060834,
            59.330525
          ],
          [
            18.060676,
            59.330696
          ],
          [
            18.060483,
            59.330904
          ]
        ]
      },
      "geometry_name": "GEOMETRY",
      "properties": {
        "FEATURE_OBJECT_ID": 22423872,
        "FEATURE_VERSION_ID": 1,
        "EXTENT_NO": 1,
        "VALID_FROM": "2024-11-27T23:00:00Z",
        "VEHICLE": "fordon",
        "START_TIME": 600,
        "END_TIME": 0,
        "START_WEEKDAY": "onsdag",
        "CITATION": "0180 2024-04575",
        "STREET_NAME": "Klara Västra Kyrkogata",
        "CITY_DISTRICT": "Norrmalm",
        "PARKING_DISTRICT": "City",
        "OTHER_INFO": "Servicetid onsdag 00:00-06:00",
        "VF_PLATS_TYP": "P-avgift endast besök",
        "ADDRESS": "Klara Västra Kyrkogata 18A - 13",
        "RDT_URL": "https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2024&LopNr=04575",
        "PARKING_RATE": "taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering",
        "FID": 14276
      },
      "bbox": [
        18.060483,
        59.330525,
        18.060834,
        59.330904
      ]
    }
  ]
}

*/
//Implement data classes for sample data above
