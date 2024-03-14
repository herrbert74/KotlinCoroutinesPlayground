package com.babestudios.coroutines.part2.section7dispatchers

import java.util.concurrent.Executors
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * There are many ways to solve this problem (most will be described in the 'The problem with state' chapter),
 * but one option is to use a dispatcher with just a single thread. If we use just a single thread at a time,
 * we do not need any other synchronization.
 * The classic way to do this was to create such a dispatcher using Executors.
 *
 * The problem is that this dispatcher keeps an extra thread active,
 * and it needs to be closed when it is not used anymore.
 */
private var i = 0

private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

suspend fun main(): Unit = coroutineScope {
	repeat(10_000) {
		launch(dispatcher) { // or Default
			i++
		}
	}
	delay(1000)
	println(i) // 10000
}
