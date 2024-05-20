package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * The problem above can be solved using classic tools we know from Java, like synchronized block or synchronized
 * collections.
 *
 * This solution works, but there are a few problems.
 * The biggest one is, that inside synchronized block you cannot use suspending functions.
 * The second one is, that this block is blocking threads when a coroutine is waiting for its turn.
 * I hope that after the chapter about dispatchers you understand that we do not want to block threads.
 * What if it is the main thread? What if we only have a limited pool of threads? Why waste these resources?
 * We should use coroutine- specific tools instead. Ones that do not block but instead suspend or avoid conflict.
 * So, let’s set aside this solution and explore some others.
 */
private var counter = 0

fun main() = runBlocking {
	val lock = Any()
	massiveRun {
		synchronized(lock) { // We are blocking threads!
			counter++
		}
	}
	println("Counter = $counter") // 1000000
}

private suspend fun massiveRun(action: suspend () -> Unit) =
	withContext(Dispatchers.Default) {
		repeat(1000) {
			launch {
				repeat(1000) { action() }
			}
		}
	}
