package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test

/**
 * If you want to test whether a certain function respects structured concurrency, the easiest way is to capture the
 * context from a suspending function, then check if it contains the expected value or if its job has the appropriate
 * state. As an example, consider the mapAsync function, which implements asynchronous mapping of a list of elements.
 */
suspend fun <T, R> Iterable<T>.mapAsync(
	transformation: suspend (T) -> R
): List<R> = coroutineScope {
	this@mapAsync.map { async { transformation(it) } }
		.awaitAll()
}

// Incorrect implementation, that would make above tests fail
@Suppress("unused")
suspend fun <T, R> Iterable<T>.mapAsyncWrong(transformation: suspend (T) -> R
): List<R> = this@mapAsyncWrong
	.map { GlobalScope.async { transformation(it) } }
	.awaitAll()

class TestingCancellationAndContextPassing {

	/**
	 * This function should map elements asynchronously while preserving their order.
	 * This behavior can be verified by the following test:
	 */
	@Test
	fun `should map async and keep elements order`() = runTest {
		val transforms = listOf(
			suspend { delay(3000); "A" },
			suspend { delay(2000); "B" },
			suspend { delay(4000); "C" },
			suspend { delay(1000); "D" },
		)
		val res = transforms.mapAsync { it() }
		assertEquals(listOf("A", "B", "C", "D"), res)
		assertEquals(4000, currentTime)
	}

	/**
	 * But this is not all. We expect a properly implemented suspending function to respect structured concurrency.
	 * The easiest way to check this is by specifying some context, like CoroutineName, for the parent coroutine,
	 * then check that it is still the same in the transformation function.
	 * To capture the context of a suspending function, we can use the currentCoroutineContext function or the
	 * coroutineContext property. In lambda expressions nested in coroutine builders or scope functions,
	 * we should use the currentCoroutineContext function because the coroutineContext property from
	 * CoroutineScope has priority over the property that provides the current coroutine context.
	 */
	@Test
	fun `should support context propagation`() = runTest {
		var ctx: CoroutineContext? = null
		val name1 = CoroutineName("Name 1")
		withContext(name1) {
			listOf("A").mapAsync {
				ctx = currentCoroutineContext()
				it
			}
			assertEquals(name1, ctx?.get(CoroutineName))
		}
		val name2 = CoroutineName("Some name 2")
		withContext(name2) {
			listOf(1, 2, 3).mapAsync {
				ctx = currentCoroutineContext()
				it
			}
			assertEquals(name2, ctx?.get(CoroutineName))
		}
	}

	/**
	 * The easiest way to test cancellation is by capturing the inner function job and verifying its cancellation
	 * after the cancellation of the outer coroutine.
	 */
	@Test
	fun `should support cancellation`() = runTest {
		var job: Job? = null
		val parentJob = launch {
			listOf("A").mapAsync {
				job = currentCoroutineContext().job
				delay(Long.MAX_VALUE)
			}
		}
		delay(1000)
		parentJob.cancel()
		assertEquals(true, job?.isCancelled)
	}

	/**
	 * I don’t think such tests are required in most applications, but I find them useful in libraries.
	 * It’s not so obvious that structured concurrency is respected.
	 * Both the tests above would fail if async were started on an outer scope.
	 */

}
