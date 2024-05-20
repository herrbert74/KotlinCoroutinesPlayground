package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent

/**
 * In the previous examples, we were using StandardTestDispatcher and wrapping it with a scope. Instead, we could use
 * TestScope, which does the same (and it collects all exceptions with CoroutineExceptionHandler). The trick is that
 * on this scope we can also use functions like advanceUntilIdle, advanceTimeBy, or the currentTime property , all of
 * which are delegated to the scheduler used by this scope. This is very convenient.
 */
fun main() {
	val scope = TestScope()

	scope.launch {
		delay(1000)
		println("First done")
		delay(1000)
		println("Coroutine done")
	}

	println("[${scope.currentTime}] Before") // [0] Before
	scope.advanceTimeBy(1000)
	scope.runCurrent() // First done
	println("[${scope.currentTime}] Middle") // [1000] Middle
	scope.advanceUntilIdle() // Coroutine done
	println("[${scope.currentTime}] After") // [2000] After
}
