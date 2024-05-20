package com.babestudios.coroutines.part2.section10testing

import com.babestudios.coroutines.part2.section8constructingacoroutinescope.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * Except for StandardTestDispatcher we also have UnconfinedTestDispatcher.
 * The biggest difference is that StandardTestDispatcher does not invoke any operations until we use its scheduler.
 * UnconfinedTestDispatcher immediately invokes all the operations before the first delay on started coroutines,
 * which is why the code below prints “C”.
 */
fun main() {
	CoroutineScope(StandardTestDispatcher()).launch {
		print("A")
		delay(1)
		print("B")
	}

	CoroutineScope(UnconfinedTestDispatcher()).launch {
		print("C")
		delay(1)
		print("D")
	}
}

/**
 * The runTest function was introduced in version 1.6 of kotlinx-coroutines-test. Previously, we used runBlockingTest,
 * whose behavior is much closer to runTest using UnconfinedTestDispatcher. So, if we want to directly migrate from
 * runBlockingTest to runTest, this is how our tests might look:
 *
 * @Test
 * fun testName() = runTest(UnconfinedTestDispatcher()) {
 * //...
 * }
 *
 * If you do not need that for backward compatibility, I recommend using StandardTestDispatcher instead of
 * UnconfinedTestDispatcher, as it is considered the new standard.
 */
