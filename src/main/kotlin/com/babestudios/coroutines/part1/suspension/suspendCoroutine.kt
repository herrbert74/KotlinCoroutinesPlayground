package com.babestudios.coroutines.part1.suspension

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    println("Before")
    suspendCoroutine<Unit> { continuation ->
        continuation.resume(Unit)
    }
    println("After")
}
