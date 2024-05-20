package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * Using lock and unlock directly is risky, as any exception (or premature return) in between would lead to the key
 * never being given back (unlock never been called), and as a result, no other coroutines would be able to pass
 * through the lock. This is a serious problem known as a deadlock (imagine a toilet that cannot be used because
 * someone was in a hurry and forgot to give back the key). So, instead we can use the withLock function, which
 * starts with lock but calls unlock on the 'finally' block so that any exceptions thrown inside the block will
 * successfully release the lock. In use, it is similar to a synchronized block.
 */
private val mutex = Mutex()
private var counter = 0

fun main() = runBlocking {
	massiveRun {
		mutex.withLock {
			counter++
		}
	}
	println(counter) // 1000000
}

private suspend fun massiveRun(action: suspend () -> Unit) =
	withContext(Dispatchers.Default) {
		repeat(1000) {
			launch {
				repeat(1000) { action() }
			}
		}
	}
