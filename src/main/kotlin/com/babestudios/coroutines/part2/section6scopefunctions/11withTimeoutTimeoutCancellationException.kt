package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * ABeware that withTimeout throws TimeoutCancellationException, which is a subtype of CancellationException (the
 * same exception that is thrown when a coroutine is cancelled).
 * So, when this exception is thrown in a coroutine builder, it only cancels it and does not affect its parent
 * (as explained in the previous chapter).
 *
 * In the below example, delay(1500) takes longer than withTimeout(1000) expects, so it throws
 * TimeoutCancellationException. The exception is caught by launch from 1, and it cancels itself and its children,
 * so launch from 2. launch started at 3 is also not affected.
 */
suspend fun main(): Unit = coroutineScope { launch { // 1
	launch { // 2, cancelled by its parent
		delay(2000)
		println("Will not be printed")
	}
	withTimeout(1000) { // we cancel launch
		delay(1500)
	} }
	launch { // 3
		delay(2000)
		println("Done")
	}
}
// (2 sec)
// Done