package com.example.broken_test_3.presentation


import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.engine.okhttp.*
import io.ktor.http.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.broken_test_3.R
import com.example.broken_test_3.presentation.theme.Broken_Test_3Theme


object WebSocketClient {
    private val client = HttpClient(OkHttp) { //CIO or OkHttp
        install(WebSockets)
        engine {
            config{
                connectTimeout(10, TimeUnit.SECONDS)    // Set connection timeout
                readTimeout(22, TimeUnit.SECONDS)       // Set read timeout
            }
        }
    }

    // Channel to send outgoing messages from outside
    private val outgoingMessages = Channel<String>(capacity = Channel.UNLIMITED)
    private val host_ip: String = "192.168.50.111"
    private var sessionJob: Job? = null


    suspend fun connect() {
        sessionJob = CoroutineScope(Dispatchers.IO).launch {

            Log.d("app tag","Attempting to connect to WebSocket...")
            try {


                client.webSocket(method = HttpMethod.Get, host = host_ip, port = 8765, path = "",) {
                    Log.d("test_tag", "WebSocket connection success!")
                    send("I am the watch client")
                    // Sender coroutine
                    val sender = launch {
                        for (msg in outgoingMessages) {
                            send(Frame.Text(msg))
                        }
                    }

                    // Receiver coroutine
//                    val receiver = launch {
//                        incoming.consumeAsFlow().collect { frame ->
//                            if (frame is Frame.Text) {
//                                Log.d("test_tag", frame.readText())
//                            }
//                        }
//                    }

                    sender.join() // Wait for sender to finish (e.g. when channel is closed)
                    //receiver.cancelAndJoin() // Cancel receiver when done
                }
            } catch (e: Exception) {
                Log.e("test_tag", "Exception: $e")
            }
        }

//        runBlocking {
//            Log.d("test_tag","Attempting to connect to WebSocket...")
//            try{
//                client.webSocket(method = HttpMethod.Get, host = host_ip, port = 8765, path="") { //method = HttpMethod.Get, host = "ws://localhost", port = 7890, path = "/echo"
//                    Log.d("test_tag","Connection success!")
//                    send("I am the watch client")
//
//
//                }
//            }catch(e:Exception){
//                Log.e("test_tag","Exception: $e")
//            }
//        }
//        client.close()
    }

    // Send a message to the server
    suspend fun send(message: String) {
        outgoingMessages.send(message)
    }

    // Close everything gracefully
    suspend fun disconnect() {
        outgoingMessages.close()
        sessionJob?.cancelAndJoin()
        client.close()
    }


}