package com.babestudios.coroutines.part3.section5understandingflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 *
 * It is a common mistake to extract a variable from outside a flow step into a function.
 * Such a variable is shared between all the coroutines that are collecting from the same flow.
 * It requires synchronization and is flow-specific, not flow-collection-specific.
 * Therefore, f2.last() returns 2000, not 1000, because it is a result of counting elements from two flow executions
 * in parallel.
 */
fun kotlinx.coroutines.flow.Flow<*>.counter2(): kotlinx.coroutines.flow.Flow<Int> {
	var counter = 0
	return this.map {
		counter++
		// to make it busy for a while
		List(100) { Random.nextLong() }.shuffled().sorted()
		counter
	}
}

suspend fun main(): Unit = coroutineScope {
	val f1 = List(1_000) { "$it" }.asFlow()
	val f2 = List(1_000) { "$it" }.asFlow()
		.counter2()

	launch { println(f1.counter2().last()) } // 1000
	launch { println(f1.counter2().last()) } // 1000
	launch { println(f2.last()) } // less than 2000
	launch { println(f2.last()) } // less than 2000
}