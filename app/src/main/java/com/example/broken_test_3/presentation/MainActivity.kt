/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.broken_test_3.presentation

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Square
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.broken_test_3.R
import com.example.broken_test_3.presentation.theme.Broken_Test_3Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlinx.coroutines.launch
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import android.content.Context
import androidx.compose.foundation.layout.size

import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

import com.samsung.android.service.health.tracking.ConnectionListener
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.HealthTrackerCapability
import com.samsung.android.service.health.tracking.HealthTrackerException
import com.samsung.android.service.health.tracking.HealthTrackingService
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.HealthTrackerType


class MainActivity : ComponentActivity() {

    // Prepare a connection listener to connect with Health Platform
    private val connectionListener = object : ConnectionListener {
        override fun onConnectionSuccess() {
            // Process connection activities here
        }

        override fun onConnectionEnded() {
            // Process disconnection activities here
        }

        override fun onConnectionFailed(e: HealthTrackerException) {
            if (e.errorCode == HealthTrackerException.OLD_PLATFORM_VERSION ||
                e.errorCode == HealthTrackerException.PACKAGE_NOT_INSTALLED) {
                Toast.makeText(
                    applicationContext,
                    "Health Platform version is outdated or not installed",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (e.hasResolution()) {
                e.resolve(this@MainActivity)
            }
        }
    }

    companion object {
        private const val APP_TAG = "app tag"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        // Connect to WiFi
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val callback = object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network) {
//                super.onAvailable(network)
//                // The Wi-Fi network has been acquired. Bind it to use this network by default.
//                connectivityManager.bindProcessToNetwork(network)
//                print("Connected to WiFi")
//            }
//
//            override fun onLost(network: Network) {
//                super.onLost(network)
//                // Called when a network disconnects or otherwise no longer satisfies this request or callback.
//                print("Disconnected from WiFi")
//            }
//        }
//        connectivityManager.requestNetwork(
//            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build(),
//            callback
//        )
//


//        // Check for Activity Recognition permission
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            Log.d(APP_TAG, "Permission not granted")
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                0
            )
        } else {
            // Permission already granted, proceed
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
            //Log.d(APP_TAG, "Permission already granted")
        }

        // Connect to Health Platform
        val healthTrackingService = HealthTrackingService(connectionListener, applicationContext)
        healthTrackingService.connectService()


        // Get a list of supported trackers
        // The list returned by the function includes all the available health tracker types from the watch
        try {
            if (healthTrackingService.trackingCapability == null){
                Log.d(APP_TAG, "Null")
            }
//            val availableTrackers: List<HealthTrackerType> =
            val trackingCapability: HealthTrackerCapability = healthTrackingService.getTrackingCapability() //.getSupportedHealthTrackerTypes()
            Log.d(APP_TAG, "Tracking capability: $trackingCapability")
            Toast.makeText(applicationContext, "Good", Toast.LENGTH_SHORT).show()


        } catch (e: Exception){
            Toast.makeText(applicationContext, "Bad", Toast.LENGTH_SHORT).show()
            Log.d(APP_TAG, "Exception: $e")
            Log.d(APP_TAG, "Service state: $healthTrackingService")
            Log.d(APP_TAG, "Geeet capabilities: "+healthTrackingService.trackingCapability)
            Log.d(APP_TAG, "Size tracker types: "+healthTrackingService.trackingCapability.supportHealthTrackerTypes.size)


        }
//        val availableTrackers: List<HealthTrackerType> =
//            healthTrackingService.trackingCapability.supportHealthTrackerTypes

        // Capability check for accelerometer
//        if (!availableTrackers.contains(HealthTrackerType.ACCELEROMETER_CONTINUOUS)) {
//            Toast.makeText(applicationContext,
//                "Accelerometer Tracking not supported on device",
//                Toast.LENGTH_LONG
//            ).show()
//            Log.e(APP_TAG, "This watch does not support accelerometer tracking.")
//        } else {
//            Toast.makeText(applicationContext,
//                "Accelerometer Tracking supported on device",
//                Toast.LENGTH_LONG
//            ).show()
//            Log.i(APP_TAG, "This watch supports accelerometer tracking.")
//        }

//
//        // Set up a listener for accelerometer data
//        val accelerometerListener = object : HealthTracker.TrackerEventListener {
//            override fun onDataReceived(list: List<DataPoint>) {
//                // Process your data
//            }
//
//            override fun onFlushCompleted() {
//                // Process flush completion
//            }
//
//            override fun onError(trackerError: HealthTracker.TrackerError) {
//                Log.i(APP_TAG, "onError called")
//                when (trackerError) {
//                    HealthTracker.TrackerError.PERMISSION_ERROR -> {
//                        runOnUiThread {
//                            Toast.makeText(applicationContext,
//                                "Permissions Check Failed", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                    HealthTracker.TrackerError.SDK_POLICY_ERROR -> {
//                        runOnUiThread {
//                            Toast.makeText(applicationContext,
//                                "SDK Policy denied", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                    else -> {
//                        // Handle other errors if necessary
//                    }
//                }
//            }
//        }
//
//        // Note:
//        // If you would like to get another kind of sensor data
//        // create a new HealthTracker instance and add a separate
//        // listener to it.
//
//        // Get a handle to the accelerometer tracker
//        val tracker: HealthTracker =
//            healthTrackingService.getHealthTracker(HealthTrackerType.ACCELEROMETER_CONTINUOUS)
//        tracker.setEventListener(accelerometerListener)
//




        setContent {
//            val viewModel = viewModel<StopWatchViewModel>()
//            val timerState by viewModel.timerState.collectAsStateWithLifecycle()
//            val stopWatchText by viewModel.stopWatchText.collectAsStateWithLifecycle()
//            StopWatch(
//                state = timerState,
//                text = stopWatchText,
//                onToggleRunning = viewModel::toggleIsRunning,
//                onReset = viewModel::resetTimer,
//                modifier = Modifier.fillMaxSize()
//            )
            VibrationButtonScreen()
        }
    }
}


@Composable
fun VibrationButtonScreen() {
    val context = LocalContext.current
    // State for background color
    var backgroundColor by remember { mutableStateOf(Color.Cyan) }
    // Remember coroutine scope
    val scope = rememberCoroutineScope()

    // UI layout
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Button(
            onClick = {
                triggerVibration(context)
                scope.launch {
                    backgroundColor = Color.Magenta
                    delay(500) // Wait for .5 seconds
                    backgroundColor = Color.Yellow // Revert to original color
                }
            },
            modifier = Modifier.size(200.dp, 80.dp)
        ) {
            Text("Click to Vibrate")
        }
    }
}

//@RequiresApi(api = Build.VERSION_CODES.31)
private fun triggerVibration(context: Context) {


    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (vibrator.hasVibrator()) { // Check if the device has a vibrator
        val vibrationEffect = VibrationEffect.createOneShot(
            500, // Duration in milliseconds
            VibrationEffect.DEFAULT_AMPLITUDE // Default amplitude
        )
        vibrator.vibrate(vibrationEffect)
    }
}




//@Composable
//private fun StopWatch(
//    state: TimerState,
//    text: String,
//    onToggleRunning: () -> Unit,
//    onReset: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = text,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.SemiBold,
//            textAlign = TextAlign.Center
//        )
////
//        Spacer(modifier = Modifier.height(8.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ){
//            Button(onClick = onToggleRunning){
//                Icon(
//                    imageVector = if(state == TimerState.RUNNING) {
//                        Icons.Default.Pause
//                    } else {Icons.Default.PlayArrow},
//                    contentDescription = null
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = onReset,
//                enabled = state != TimerState.RESET,
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MaterialTheme.colors.surface
//
//                )
//
//            ){
//                Icon(
//                    imageVector = Icons.Default.Stop,
//                    contentDescription = null
//                )
//            }
//        }
//
//    }
//}


//@Composable
//fun WearApp(greetingName: String) {
//    Broken_Test_3Theme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colors.background),
//            contentAlignment = Alignment.Center
//        ) {
//            TimeText()
//            Greeting(greetingName = greetingName)
//        }
//    }
//}
//
//@Composable
//fun Greeting(greetingName: String) {
//    Text(
//        modifier = Modifier.fillMaxWidth(),
//        textAlign = TextAlign.Center,
//        color = MaterialTheme.colors.primary,
//        text = stringResource(R.string.hello_world, greetingName)
//    )
//}
//
//@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    WearApp("Preview Android")
//}