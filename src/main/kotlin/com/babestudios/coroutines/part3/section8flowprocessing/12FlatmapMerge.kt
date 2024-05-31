package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

/**
 * The second mentioned function, flatMapMerge, introduces asynchrony. Each flow is started on a new coroutine, so they
 * can be processed concurrently. The following example is the same as the previous one, but with flatMapMerge instead
 * of flatMapConcat.
 */
suspend fun main() {
	flowOf("A", "B", "C")
		.onEach { println("Thread1: ${Thread.currentThread()}") }
		.flatMapMerge { flowFrom(it) }
		.collect { println(it) }
}
