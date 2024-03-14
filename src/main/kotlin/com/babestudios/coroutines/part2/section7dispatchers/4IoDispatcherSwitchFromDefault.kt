package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 * As we mentioned, both Dispatchers.Default and Dispatchers.IO share the same pool of threads. This is an important
 * optimization. Threads are reused, and often redispatching is not needed. For instance, let’s say you are running
 * on Dispatchers.Default and then execution reaches withContext(Dispatchers.IO) { ... }.
 * Most often, you will stay on the same thread33, but what changes is that this thread counts not towards the
 * Dispatchers.Default limit but towards the Dispatchers.IO limit. Their limits are independent,
 * so they will never starve each other.
 */
suspend fun main(): Unit = coroutineScope {
	launch(Dispatchers.Default) {
		println(Thread.currentThread().name)
		withContext(Dispatchers.IO) {
			println(Thread.currentThread().name)
		}
	}
}
// DefaultDispatcher-worker-2
// DefaultDispatcher-worker-2
