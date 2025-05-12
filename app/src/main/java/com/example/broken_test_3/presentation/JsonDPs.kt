package com.example.broken_test_3.presentation

import kotlinx.serialization.Serializable

@Serializable
data class AccelerometerDP(
    val name: String,
    val timestamp: Long,
    val x: Int,
    val y: Int,
    val z: Int,
    val unit: String
)

@Serializable
data class HeartRateDP(
    val name: String,
    val timestamp: Long,
    val hr: Int,
    val hrStatus: Int,
    val unit: String
)

@Serializable
data class SkinTempDP(
    val name: String,
    val timestamp: Long,
    val ambientTemp: Int,
    val objTemp: Int,
    val unit: String
)
