package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * The important advantage of mutex over a synchronized block is that we suspend a coroutine instead of blocking a thread.
 * This is a safer and lighter approach. Compared to using a dispatcher with parallelism limited to a single thread,
 * mutex is lighter, and in some cases it might offer better performance. On the other hand, it is also harder to use
 * it properly. It has one important danger: a coroutine cannot get past the lock twice (maybe the key stays in
 * the door, so another door requiring the same key would be impossible to get past). Execution of the code below will
 * result in a program state called deadlock - it will be blocked forever.
 */
suspend fun main() {
	val mutex = Mutex()
	println("Started")
	mutex.withLock {
		mutex.withLock {
			println("Will never be printed")
		}
	}
}
