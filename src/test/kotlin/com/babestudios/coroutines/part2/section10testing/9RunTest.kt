package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

/**
 * runTest is the most commonly used function from kotlinx-coroutines-test.
 * It starts a coroutine with TestScope and immediately advances it until idle.
 * Within its coroutine, the scope is of type TestScope, so we can check currentTime at any point.
 * Therefore, we can check how time flows in our coroutines, while our tests take milliseconds.
 */
class TestTest {

	@Test
	fun test1() = runTest {
		assertEquals(0, currentTime)
		delay(1000)
		assertEquals(1000, currentTime)
	}

	@Test
	fun test2() = runTest {
		assertEquals(0, currentTime)
		coroutineScope {
			launch { delay(1000) }
			launch { delay(1500) }
			launch { delay(2000) }
		}
		assertEquals(2000, currentTime)
	}

}
