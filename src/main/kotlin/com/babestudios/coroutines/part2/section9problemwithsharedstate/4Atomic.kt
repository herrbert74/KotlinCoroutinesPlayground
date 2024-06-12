package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * There is another Java solution that can help us in some simple cases. Java has a set of atomic values.
 * All their operations are fast and guaranteed to be “thread-safe”. They are called atomic.
 * Their operations are implemented at a low level without locks, so this solution is efficient and appropriate for us.
 * There are different atomic values we can use. For our case, we can use AtomicInteger.
 */
private var counter = AtomicInteger()

fun main() = runBlocking {
	massiveRun {
		counter.incrementAndGet()
	}
	println(counter.get()) // 1000000
}

private suspend fun massiveRun(action: suspend () -> Unit) =
	withContext(Dispatchers.Default) {
		repeat(1000) {
			launch {
				repeat(1000) { action() }
			}
		}

	}
