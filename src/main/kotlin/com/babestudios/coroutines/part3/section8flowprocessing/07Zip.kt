package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip

/**
 * The next function is zip, which makes pairs from both flows. We also need to specify a function that decides how
 * elements are paired (transformed into one what will be emitted in the new flow). Each element can only be part of
 * one pair, so it needs to wait for its pair. Elements left without a pair are lost, so when the zipping of a flow
 * is complete, the resulting flow is also complete (as is the other flow).
 */
suspend fun main() {
	val flow1 = flowOf("A", "B", "C")
		.onEach { delay(400) }
	val flow2 = flowOf(1, 2, 3, 4)
		.onEach { delay(1000) }
	flow1.zip(flow2) { f1, f2 -> "${f1}_${f2}" }
		.collect { println(it) }
}
