package com.babestudios.coroutines.part3.section7lifecyclefunctions

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.retry

/**
 * An exception flows through a flow, closing each step one by one. These steps become inactive, so it is not possible
 * to send messages after an exception, but each step gives you a reference to the previous ones, and this reference
 * can be used to start this flow again. Based on this idea, Kotlin offers the retry and retryWhen functions. Here is
 * a simplified implementation of retryWhen:
 *
 * // Simplified implementation of retryWhen
 * fun <T> Flow<T>.retryWhen(
 *    predicate: suspend FlowCollector<T>.(
 *         cause: Throwable,
 *         attempt: Long, ) -> Boolean,
 * ): Flow<T> = flow {
 *    var attempt = 0L
 *    do {
 *       val shallRetry = try {
 *          collect { emit(it) }
 *          false
 *       } catch (e: Throwable) {
 *          predicate(e, attempt++)
 *          .also { if (!it) throw e }
 *       }
 *    } while (shallRetry)
 * }
 *
 * As you can see, retryWhen has a predicate which is checked whenever an exception occurs from the previous steps of
 * the flow. This predicate decides if an exception should be ignored and previous steps started again, or if it should
 * continue closing this flow. This is a universal retry function. In most cases, we want to specify that we want to
 * retry a specific number of times and/or only when a certain class of exceptions occurs (like a network connection
 * exception). For that, there is another function called retry, which uses retryWhen under the hood.
 *
 * Actual implementation of retry
 *
 * fun <T> Flow<T>.retry(
 * 	retries: Long = Long.MAX_VALUE,
 * 	predicate: suspend (cause: Throwable) -> Boolean = { true }
 * ): Flow<T> {
 * 	require(retries > 0) {
 * 		"Expected positive amount of retries, but had $retries"
 * 	}
 * 	return retryWhen { cause, attempt ->
 * 		attempt < retries && predicate(cause)
 * 	}
 * }
 *
 * This is how retry works:
 */
suspend fun main() {
	flow {
		emit(1)
		emit(2)
		error("E")
		emit(3)
	}.retry(3) {
		print(it.message)
		true
	}.collect { print(it) }
}

/**
 * Let’s see a few popular usages of these functions. I often see retries that always retry. For them, a popular
 * reason to define a predicate is to specify some logging and introduce some delay between new connection attempts.
 *
 * fun makeConnection(config: ConnectionConfig) = api .startConnection(config)
 * 	.retry { e ->
 * 		delay(1000)
 * 		log.error(e) { "Error for $config" } true
 * 	}
 *
 * There is another popular practice which involves gradually increasing the delay between subsequent connection
 * attempts. We can also implement a predicate that retries if an exception is or isn’t of a certain type.
 *
 * fun makeConnection(config: ConnectionConfig) = api.startConnection(config)
 * 	.retryWhen { e, attempt ->
 * 		delay(100 * attempt)
 * 		log.error(e) { "Error for $config" }
 * 		e is ApiException && e.code !in 400..499
 * 	}
 */
