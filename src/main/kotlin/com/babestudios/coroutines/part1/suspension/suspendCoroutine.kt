package com.babestudios.coroutines.part1.suspension

import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    println("Before")
    val t = suspendCoroutine<Int> { continuation ->

        continuation.resume(5)
    }
    delay(3000)
    println("After: $t")
}
