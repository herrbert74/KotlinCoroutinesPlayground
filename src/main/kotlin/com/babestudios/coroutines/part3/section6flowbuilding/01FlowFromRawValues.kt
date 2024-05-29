package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.flow.flowOf

/**
 * The simplest way to create a flow is by using the flowOf function, where we just define what values this flow should
 * have(similar to the listOf function for a list).
 */
suspend fun main() { flowOf(1, 2, 3, 4, 5)
	.collect { print(it) } // 12345
}
