package com.babestudios.coroutines.part2.section7dispatchers

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 *
 * The last dispatcher we need to discuss is Dispatchers.Unconfined.
 * This dispatcher is different from the previous one as it does not change any threads.
 * When it is started, it runs on the thread on which it was started.
 * If it is resumed, it runs on the thread that resumed it.
 *
 * This is sometimes useful for unit testing. Imagine that you need to test a function that calls launch.
 * Synchronizing the time might not be easy. One solution is to use Dispatchers.Unconfined instead of all other
 * dispatchers. If it is used in all scopes, everything runs on the same thread, and we can more easily control
 * the order of operations. This trick is not needed if we use runTest from kotlinx-coroutines-test.
 * We will discuss this later in the book.
 * From the performance point of view, this dispatcher is the cheapest as it never requires thread switching.
 * So, we might choose it if we do not care at all on which thread our code is running.
 * However, in practice, it is not considered good to use it so recklessly. What if, by accident, we miss a blocking
 * call, and we are running on the Main thread? This could lead to blocking the entire application.
 */
@OptIn(DelicateCoroutinesApi::class)
suspend fun main(): Unit = withContext(newSingleThreadContext("Thread1")) {

	var continuation: Continuation<Unit>? = null

	launch(newSingleThreadContext("Thread2")) {
		delay(1000)
		continuation?.resume(Unit)
	}

	launch(Dispatchers.Unconfined) {
		println(Thread.currentThread().name) // Thread1
		suspendCancellableCoroutine {
			continuation = it
		}
		println(Thread.currentThread().name) // Thread2
		delay(1000)
		println(Thread.currentThread().name) // kotlinx.coroutines.DefaultExecutor (used by delay)
	}

}
