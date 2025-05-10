package com.example.broken_test_3.presentation

interface WebSocketListener {
    fun onConnected()
    suspend fun onMessage(message: String)
    fun onDisconnected()
}