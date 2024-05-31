package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.toList

/**
 * Let’s talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the
 * elements from two flows into one. No modifications are made, no matter from which flow elements originate.
 * To do this, we use the top-level merge function.
 */
suspend fun main() {
	val ints: Flow<Int> = flowOf(1, 2, 3)
	val doubles: Flow<Double> = flowOf(0.1, 0.2, 0.3)
	val together: Flow<Number> = merge(ints, doubles)
	print(together.toList())
	// [1, 0.1, 0.2, 0.3, 2, 3]
	// or [1, 0.1, 0.2, 0.3, 2, 3]
	// or [0.1, 1, 2, 3, 0.2, 0.3]
	// or any other combination
}
