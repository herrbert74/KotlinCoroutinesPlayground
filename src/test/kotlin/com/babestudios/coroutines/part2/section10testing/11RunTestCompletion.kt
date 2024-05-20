package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

/**
 * The runTest function creates a scope; like all such functions, it awaits the completion of its children.
 * This means that if you start a process that never ends, your test will never stop.
 */
class RunTestCompletionTest {

	@Test
	fun `should increment counter`() = runTest { var i = 0
		launch {
			while (true) {
				delay(1000)
				i++ }
		}
		println("1")
		delay(1001)
		println("2")
		assertEquals(1, i)
		println("3")
		delay(1000)
		println("4")
		assertEquals(2, i)
		println("5")
		// Test would pass if we added
		// coroutineContext.job.cancelChildren()
	}

}