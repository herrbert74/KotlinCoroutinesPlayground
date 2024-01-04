package com.babestudios.coroutines.part1.suspension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/*

Delay is using a single thread for all calling coroutines, but still can do it parallel

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

suspend fun delay(timeMillis: Long): Unit =
    suspendCoroutine { cont ->
        executor.schedule(
            {
                cont.resume(Unit)
            }, timeMillis, TimeUnit.MILLISECONDS)
    }
 */

fun main() {
    val m = CoroutineScope(Dispatchers.Unconfined)
    val m2 = CoroutineScope(Dispatchers.Unconfined)
    val m3 = CoroutineScope(Dispatchers.Unconfined)
    m.launch { main1() }
    m2.launch { main2() }
    m3.launch { main3() }
    Thread.sleep(3020)
}

suspend fun main1() {
    println("Before 1")
    delay(3000)
    println("After 1")
}

suspend fun main2() {
    println("Before 2")
    delay(3000)
    println("After 2")
}

suspend fun main3() {
    println("Before 3")
    delay(3000)
    println("After 3")
}
