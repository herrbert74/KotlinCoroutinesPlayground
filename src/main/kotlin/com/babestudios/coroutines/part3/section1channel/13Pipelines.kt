package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Sometimes we set two channels such that one produces elements based on those received from another.
 * In such cases, we call it a pipeline.
 */
// A channel of number from 1 to 3
fun CoroutineScope.numbers(): ReceiveChannel<Int> = produce {
	repeat(3) { num ->
		send(num + 1)
	}
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>) = produce {
	for (num in numbers) {
		send(num * num)
	}
}

suspend fun main() = coroutineScope {
	val numbers = numbers()
	val squared = square(numbers)
	for (num in squared) {
		println(num)
	}
}
