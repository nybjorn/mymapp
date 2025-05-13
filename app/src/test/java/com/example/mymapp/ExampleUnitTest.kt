package com.example.mymapp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun jsonTest() {
        val json  = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": \"LTFR_P_TILLATEN.12061\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            18.059971,\n" +
                "            59.33\n" +
                "          ],\n" +
                "          [\n" +
                "            18.059661,\n" +
                "            59.330343\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"geometry_name\": \"GEOMETRY\",\n" +
                "      \"properties\": {\n" +
                "        \"FEATURE_OBJECT_ID\": 19769471,\n" +
                "        \"FEATURE_VERSION_ID\": 1,\n" +
                "        \"EXTENT_NO\": 1,\n" +
                "        \"VALID_FROM\": \"2022-06-22T22:00:00Z\",\n" +
                "        \"VEHICLE\": \"fordon\",\n" +
                "        \"START_TIME\": 600,\n" +
                "        \"END_TIME\": 0,\n" +
                "        \"START_WEEKDAY\": \"måndag\",\n" +
                "        \"MAX_HOURS\": 1,\n" +
                "        \"CITATION\": \"0180 2022-02696\",\n" +
                "        \"STREET_NAME\": \"Vasagatan\",\n" +
                "        \"CITY_DISTRICT\": \"Norrmalm\",\n" +
                "        \"PARKING_DISTRICT\": \"City\",\n" +
                "        \"OTHER_INFO\": \"Servicetid måndag 00:00-06:00\",\n" +
                "        \"VF_PLATS_TYP\": \"P-avgift endast besök\",\n" +
                "        \"ADDRESS\": \"Vasagatan 14 - 18\",\n" +
                "        \"RDT_URL\": \"https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2022&LopNr=02696\",\n" +
                "        \"PARKING_RATE\": \"taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering\",\n" +
                "        \"FID\": 12061\n" +
                "      },\n" +
                "      \"bbox\": [\n" +
                "        18.059661,\n" +
                "        59.33,\n" +
                "        18.059971,\n" +
                "        59.330343\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": \"LTFR_P_TILLATEN.12411\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            18.059725,\n" +
                "            59.329582\n" +
                "          ],\n" +
                "          [\n" +
                "            18.05972,\n" +
                "            59.329583\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"geometry_name\": \"GEOMETRY\",\n" +
                "      \"properties\": {\n" +
                "        \"FEATURE_OBJECT_ID\": 19525990,\n" +
                "        \"FEATURE_VERSION_ID\": 1,\n" +
                "        \"EXTENT_NO\": 1,\n" +
                "        \"VALID_FROM\": \"2022-04-02T22:00:00Z\",\n" +
                "        \"VEHICLE\": \"cykel\",\n" +
                "        \"CITATION\": \"0180 2022-00936\",\n" +
                "        \"STREET_NAME\": \"Centralplan\",\n" +
                "        \"CITY_DISTRICT\": \"Norrmalm\",\n" +
                "        \"PARKING_DISTRICT\": \"City\",\n" +
                "        \"VF_PLATS_TYP\": \"Reserverad p-plats cykel\",\n" +
                "        \"ADDRESS\": \"Centralplan 3\",\n" +
                "        \"RDT_URL\": \"https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2022&LopNr=00936\",\n" +
                "        \"PARKING_RATE\": \"avgiftsfri\",\n" +
                "        \"FID\": 12411\n" +
                "      },\n" +
                "      \"bbox\": [\n" +
                "        18.05972,\n" +
                "        59.329582,\n" +
                "        18.059725,\n" +
                "        59.329583\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": \"LTFR_P_TILLATEN.12492\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            18.059595,\n" +
                "            59.329452\n" +
                "          ],\n" +
                "          [\n" +
                "            18.059559,\n" +
                "            59.329456\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"geometry_name\": \"GEOMETRY\",\n" +
                "      \"properties\": {\n" +
                "        \"FEATURE_OBJECT_ID\": 20277361,\n" +
                "        \"FEATURE_VERSION_ID\": 1,\n" +
                "        \"EXTENT_NO\": 1,\n" +
                "        \"VALID_FROM\": \"2023-02-07T23:00:00Z\",\n" +
                "        \"VEHICLE\": \"fordon\",\n" +
                "        \"MAX_HOURS\": 1,\n" +
                "        \"CITATION\": \"0180 2023-00170\",\n" +
                "        \"STREET_NAME\": \"Centralplan\",\n" +
                "        \"CITY_DISTRICT\": \"Norrmalm\",\n" +
                "        \"PARKING_DISTRICT\": \"City\",\n" +
                "        \"VF_PLATS_TYP\": \"P-avgift endast besök\",\n" +
                "        \"ADDRESS\": \"Centralplan 1\",\n" +
                "        \"RDT_URL\": \"https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2023&LopNr=00170\",\n" +
                "        \"PARKING_RATE\": \"taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering\",\n" +
                "        \"FID\": 12492\n" +
                "      },\n" +
                "      \"bbox\": [\n" +
                "        18.059559,\n" +
                "        59.329452,\n" +
                "        18.059595,\n" +
                "        59.329456\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": \"LTFR_P_TILLATEN.14276\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            18.060834,\n" +
                "            59.330525\n" +
                "          ],\n" +
                "          [\n" +
                "            18.060676,\n" +
                "            59.330696\n" +
                "          ],\n" +
                "          [\n" +
                "            18.060483,\n" +
                "            59.330904\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"geometry_name\": \"GEOMETRY\",\n" +
                "      \"properties\": {\n" +
                "        \"FEATURE_OBJECT_ID\": 22423872,\n" +
                "        \"FEATURE_VERSION_ID\": 1,\n" +
                "        \"EXTENT_NO\": 1,\n" +
                "        \"VALID_FROM\": \"2024-11-27T23:00:00Z\",\n" +
                "        \"VEHICLE\": \"fordon\",\n" +
                "        \"START_TIME\": 600,\n" +
                "        \"END_TIME\": 0,\n" +
                "        \"START_WEEKDAY\": \"onsdag\",\n" +
                "        \"CITATION\": \"0180 2024-04575\",\n" +
                "        \"STREET_NAME\": \"Klara Västra Kyrkogata\",\n" +
                "        \"CITY_DISTRICT\": \"Norrmalm\",\n" +
                "        \"PARKING_DISTRICT\": \"City\",\n" +
                "        \"OTHER_INFO\": \"Servicetid onsdag 00:00-06:00\",\n" +
                "        \"VF_PLATS_TYP\": \"P-avgift endast besök\",\n" +
                "        \"ADDRESS\": \"Klara Västra Kyrkogata 18A - 13\",\n" +
                "        \"RDT_URL\": \"https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2024&LopNr=04575\",\n" +
                "        \"PARKING_RATE\": \"taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering\",\n" +
                "        \"FID\": 14276\n" +
                "      },\n" +
                "      \"bbox\": [\n" +
                "        18.060483,\n" +
                "        59.330525,\n" +
                "        18.060834,\n" +
                "        59.330904\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": \"LTFR_P_TILLATEN.14109\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            18.061419,\n" +
                "            59.329964\n" +
                "          ],\n" +
                "          [\n" +
                "            18.061167,\n" +
                "            59.33023\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"geometry_name\": \"GEOMETRY\",\n" +
                "      \"properties\": {\n" +
                "        \"FEATURE_OBJECT_ID\": 22423875,\n" +
                "        \"FEATURE_VERSION_ID\": 1,\n" +
                "        \"EXTENT_NO\": 1,\n" +
                "        \"VALID_FROM\": \"2024-11-27T23:00:00Z\",\n" +
                "        \"VEHICLE\": \"fordon\",\n" +
                "        \"START_TIME\": 600,\n" +
                "        \"END_TIME\": 0,\n" +
                "        \"START_WEEKDAY\": \"onsdag\",\n" +
                "        \"CITATION\": \"0180 2024-04573\",\n" +
                "        \"STREET_NAME\": \"Klara Västra Kyrkogata\",\n" +
                "        \"CITY_DISTRICT\": \"Norrmalm\",\n" +
                "        \"PARKING_DISTRICT\": \"City\",\n" +
                "        \"OTHER_INFO\": \"Servicetid onsdag 00:00-06:00\",\n" +
                "        \"VF_PLATS_TYP\": \"P Avgift, boende\",\n" +
                "        \"ADDRESS\": \"Klara Västra Kyrkogata 16\",\n" +
                "        \"RDT_URL\": \"https://rdt.transportstyrelsen.se/rdt/AF06_View.aspx?BeslutsMyndighetKod=0180&BeslutadAr=2024&LopNr=04573\",\n" +
                "        \"PARKING_RATE\": \"taxa 1: 55 kr/tim alla dagar 00-24 Boende: ingen boendeparkering\",\n" +
                "        \"FID\": 14109\n" +
                "      },\n" +
                "      \"bbox\": [\n" +
                "        18.061167,\n" +
                "        59.329964,\n" +
                "        18.061419,\n" +
                "        59.33023\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"totalFeatures\": 5,\n" +
                "  \"numberMatched\": 5,\n" +
                "  \"numberReturned\": 5,\n" +
                "  \"timeStamp\": \"2025-05-12T11:39:13.528Z\",\n" +
                "  \"crs\": {\n" +
                "    \"type\": \"name\",\n" +
                "    \"properties\": {\n" +
                "      \"name\": \"urn:ogc:def:crs:EPSG::4326\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"bbox\": [\n" +
                "    18.059559,\n" +
                "    59.329452,\n" +
                "    18.061419,\n" +
                "    59.330904\n" +
                "  ]\n" +
                "}"

        Json
        val test  = Json{
            ignoreUnknownKeys = true
        }.decodeFromString<FeatureCollection>(json)
        assertEquals(3, test.features.size)
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
}