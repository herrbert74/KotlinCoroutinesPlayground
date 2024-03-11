package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

/**
 * Another function that behaves a lot like coroutineScope is withTimeout.
 * It also creates a scope and returns a value. Actually, withTimeout with a very big timeout behaves just like
 * coroutineScope. The difference is that withTimeout additionally sets a time limit for its body execution.
 * If it takes too long, it cancels this body and throws TimeoutCancellationException
 * (a subtype of CancellationException).
 */
suspend fun test(): Int = withTimeout(1500) {
	delay(1000)
	println("Still thinking")
	delay(1000)
	println("Done!")
	42
}

suspend fun main(): Unit = coroutineScope {
	try {
		test()
	} catch (e: TimeoutCancellationException) {
		println("Cancelled")
	}
	delay(1000) // Extra timeout does not help,
	// `test` body was cancelled
}
// (1 sec)
// Still thinking
// (0.5 sec)
// Cancelled
