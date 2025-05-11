package com.example.broken_test_3.presentation

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class AccelerometerDP(
    val name: String,
    val timestamp: Long,
    val x: Int,
    val y: Int,
    val z: Int,
    val unit: String
)

