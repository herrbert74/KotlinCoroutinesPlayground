package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * The utility of atomic values is generally very limited, therefore we need to be careful:
 * just knowing a single operation will be atomic does not help us when we have a bundle of operations.
 */
private var counter = AtomicInteger()

fun main() = runBlocking {
	massiveRun {
		counter.set(counter.get() + 1)
	}
	println(counter.get()) // ~430467
}


private suspend fun massiveRun(action: suspend () -> Unit) =
	withContext(Dispatchers.Default) {
		repeat(1000) {
			launch {
				repeat(1000) { action() }
			}
		}
	}
