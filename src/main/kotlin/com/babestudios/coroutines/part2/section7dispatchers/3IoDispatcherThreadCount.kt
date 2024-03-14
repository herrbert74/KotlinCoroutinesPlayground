package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * How does it work? Imagine an unlimited pool of threads. Initially, it is empty, but as we need more threads,
 * they are created and kept active until they are not used for some time. Such a pool exists, but it wouldn’t be safe
 * to use it directly. With too many active threads, the performance degrades in a slow but unlimited manner,
 * eventually causing out-of-memory errors. This is why we create dispatchers that have a limited number of threads
 * they can use at the same time. Dispatchers.Default is limited by the number of cores in your processor.
 * The limit of Dispatchers.IO is 64 (or the number of cores if there are more).
 */
suspend fun main() = coroutineScope {
	repeat(1000) {
		launch(Dispatchers.IO) {
			Thread.sleep(200)
			val threadName = Thread.currentThread().name
			println("Running on thread: $threadName")
		}
	}
}
// Running on thread: DefaultDispatcher-worker-1
//...
// Running on thread: DefaultDispatcher-worker-53
// Running on thread: DefaultDispatcher-worker-14