package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.flow.emptyFlow

/**
 * At times, we might also need a flow with no values. For this, we have the emptyFlow() function
 * (similar to the emptyList function for a list).
 */
suspend fun main() { emptyFlow<Int>()
	.collect { print(it) } // (nothing)
}