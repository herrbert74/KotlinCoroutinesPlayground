package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.flow.asFlow

/**
 * We can also convert every Iterable, Iterator or Sequence into a Flow using the asFlow function.
 *
 * These functions produce a flow of elements that are available immediately.
 * They are useful to start a flow of elements that we can then process using the flow processing functions.
 */
suspend fun main() { listOf(1, 2, 3, 4, 5)
	// or setOf(1, 2, 3, 4, 5)
	// or sequenceOf(1, 2, 3, 4, 5)
	.asFlow()
	.collect { print(it) } // 12345
}