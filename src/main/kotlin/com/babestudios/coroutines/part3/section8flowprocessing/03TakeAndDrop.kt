package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take

/**
 * We use take to pass only a certain number of elements.
 * We use drop to ignore a certain number of elements.
 */
suspend fun main() { ('A'..'Z').asFlow()
	.drop(5)
	.take(5)
	.collect { print(it) } // FGHIJ
}
