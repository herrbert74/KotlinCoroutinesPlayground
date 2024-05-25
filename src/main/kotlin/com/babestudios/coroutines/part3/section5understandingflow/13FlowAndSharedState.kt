package com.babestudios.coroutines.part3.section5understandingflow

import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 *
 * Finally, just as suspending functions using the same variables need synchronization, a variable used in a flow needs
 * synchronization if it’s defined outside a function, on the scope of a class, or at the top-level.
 */

var counter = 0

fun kotlinx.coroutines.flow.Flow<*>.counter3(): kotlinx.coroutines.flow.Flow<Int> = this.map {
	counter++
	// to make it busy for a while
	List(100) { Random.nextLong() }.shuffled().sorted()
	counter
}

suspend fun main(): Unit = coroutineScope {
	val f1 = List(1_000) { "$it" }.asFlow()
	val f2 = List(1_000) { "$it" }.asFlow()
		.counter3()
	launch { println(f1.counter3().last()) } // less than 4000
	launch { println(f1.counter3().last()) } // less than 4000
	launch { println(f2.last()) } // less than 4000
	launch { println(f2.last()) } // less than 4000
}