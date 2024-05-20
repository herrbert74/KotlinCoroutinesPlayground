package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

/**
 * For such situations, runTest offers backgroundScope. This is a scope that also operates on virtual time from the
 * same scheduler, but runTest will not treat it as its children and await its completion.
 * This is why the test below passes without any problems. We use backgroundScope to start all the processes we
 * don’t want our test to wait for.
 */
class RunTestCompletionBackgroundScopeTest {

	@Test
	fun `should increment counter`() = runTest {
		var i = 0
		backgroundScope.launch { while (true) {
			delay(1000)
			i++ }
		}
		delay(1001)

		assertEquals(1, i)
		delay(1000)
		assertEquals(2, i)
	}
}