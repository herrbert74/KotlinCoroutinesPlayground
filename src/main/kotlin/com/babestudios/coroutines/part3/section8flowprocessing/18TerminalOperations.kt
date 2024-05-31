package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce

/**
 * Finally, we have operations that end flow processing. These are called terminal operations. Until now, we have only
 * used collect, but there are also others that are similar to those offered by collections and Sequence: count (counts
 * the number of elements in the flow), first and firstOrNull (to get the first element emitted by the flow), fold and
 * reduce (to accumulate elements into an object). Terminal operations are suspended, and they return the value once the
 * flow is complete (or they complete the flow themselves).
 */
suspend fun main() {
	val flow = flowOf(1, 2, 3, 4) // [1, 2, 3, 4]
		.map { it * it } // [1, 4, 9, 16]
	println(flow.first()) // 1
	println(flow.count()) // 4
	println(flow.reduce { acc, value -> acc * value }) // 576
	println(flow.fold(0) { acc, value -> acc + value }) // 30
}

/**
 * There are currently not many more terminal operations for flow, but if you need something different you can always
 * implement it yourself. This is, for instance, how you can implement sum for a flow of Int:
 */
suspend fun Flow<Int>.sum(): Int {
	var sum = 0
	collect { value ->
		sum += value
	}
	return sum
}
/**
 * Similarly, you can implement nearly any terminal operation with just the collect method.
 */