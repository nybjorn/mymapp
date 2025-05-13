package com.example.mymapp


import android.Manifest
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymapp.ui.theme.MyMappTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.net.CronetProviderInstaller
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.google.maps.android.compose.widgets.ScaleBar
import com.google.net.cronet.okhttptransport.CronetInterceptor
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.chromium.net.CronetEngine


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: ParkingViewModel by lazy {
        val engine: CronetEngine = CronetEngine.Builder(this).build()
        val callFactory: Call.Factory = OkHttpClient.Builder()
            .protocols(listOf(Protocol.QUIC, Protocol.HTTP_2, Protocol.HTTP_1_1))
            .addInterceptor(CronetInterceptor.newBuilder(engine).build())
            .build()

        val app: ApplicationInfo = this.getPackageManager()
            .getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA)
        val bundle = app.metaData
        val apiKey = bundle.getString("myapp.park.API_KEY")

        val factory = ParkingViewModel.ParkingViewModelFactory(callFactory, apiKey)
        ViewModelProvider(this, factory).get(ParkingViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CronetProviderInstaller.installProvider(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        enableEdgeToEdge()
        setContent {

            MyMappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    val stockholm = LatLng(59.33, 18.059661)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(stockholm, 15f)
                    }
                    val markerState =
                        rememberUpdatedMarkerState(position = cameraPositionState.position.target)
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    var parkingLanes by remember { mutableStateOf<List<List<LatLng>>>(emptyList()) }
                    when (state) {
                        is ParkingUiState.Found -> {
                            parkingLanes = ((state as ParkingUiState.Found).coordinates).toList()
                        }

                        is ParkingUiState.Looking -> {

                        }

                        ParkingUiState.WaitingForUser -> {}
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            cameraPositionState = cameraPositionState
                        ) {


                            AdvancedMarker(
                                draggable = true,
                                title = "Bilen",
                                visible = true,
                                state = markerState,
                            )
                            if (parkingLanes.any()) {
                                parkingLanes.forEach {
                                    Polyline(
                                        points = it,
                                        color = Color.Green,
                                        width = 30f
                                    )
                                }
                            }
                        }
                        ScaleBar(
                            modifier = Modifier
                                .padding(top = 5.dp, end = 15.dp)
                                .padding(innerPadding),
                            cameraPositionState = cameraPositionState
                        )

                        ButtonRow(
                            innerPadding,
                            fusedLocationClient,
                            cameraPositionState,
                            viewModel,
                            markerState.position,
                            state
                        )
                    }

                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun ButtonRow(
        innerPadding: PaddingValues,
        fusedLocationClient: FusedLocationProviderClient,
        cameraPositionState: CameraPositionState,
        viewModel: ParkingViewModel,
        position: LatLng,
        state: ParkingUiState
    ) {
        val locationPermissionsState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )

        Row(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            if (locationPermissionsState.allPermissionsGranted) {
                Button(
                    onClick = {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                if (location != null) {
                                    val currentPos = LatLng(location.latitude, location.longitude)
                                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                        currentPos, 15f
                                    )
                                } else {
                                    val currentPos = LatLng(59.2986, 18.0459)
                                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                        currentPos, 10f
                                    )
                                }
                            }
                    },
                    content = {
                        Text("Här")
                    })
            } else {
                val allPermissionsRevoked =
                    locationPermissionsState.permissions.size ==
                            locationPermissionsState.revokedPermissions.size

                val textToShow = if (!allPermissionsRevoked) {
                    // If not all the permissions are revoked, it's because the user accepted the COARSE
                    // location permission, but not the FINE one.
                    "Yay! Thanks for letting me access your approximate location. " +
                            "But you know what would be great? If you allow me to know where you " +
                            "exactly are. Thank you!"
                } else if (locationPermissionsState.shouldShowRationale) {
                    // Both location permissions have been denied
                    "Getting your exact location is important for this app. " +
                            "Please grant us fine location. Thank you :D"
                } else {
                    // First time the user sees this feature or the user doesn't want to be asked again
                    "This feature requires location permission"
                }
                Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                    Text(textToShow)
                }
            }
            if (state is ParkingUiState.Found || state is ParkingUiState.WaitingForUser) {
                Button(
                    onClick = { viewModel.getParkingSpace(position.latitude, position.longitude) },
                    content = {
                        Text("Parkera")
                    })
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyMappTheme {
        Greeting("Android")
    }
}