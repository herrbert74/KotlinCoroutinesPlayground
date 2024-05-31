package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

/**
 * It gets more interesting when the elements from the initial flow are delayed. What happens in the example below is
 * that (after 1.2 sec) “A” starts its flow, which was created using flowFrom. This flow produces an element “1_A” in
 * 1 second, but 200 ms later “B” appears and this previous flow is closed and forgotten. “B” flow managed to produce
 * “1_B” when “C” appeared and started producing its flow. This one will finally produce elements “1_C”, “2_C”, and
 * “3_C”, with a 1-second delay in between.
 */
suspend fun main() { flowOf("A", "B", "C")
	.onEach { delay(1200) }
	.flatMapLatest { flowFrom(it) }
	.collect { println(it) }
}
