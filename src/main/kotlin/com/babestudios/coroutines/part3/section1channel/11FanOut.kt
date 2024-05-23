package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Multiple coroutines can receive from a single channel; however, to receive them properly we should use a
 * for-loop (consumeEach is not safe to use from multiple coroutines).
 *
 * The elements are distributed fairly.
 * The channel has a FIFO (first-in-first-out) queue of coroutines waiting for an element.
 * This is why in the above example you can see that the elements are received by the
 * next coroutines (0, 1, 2, 0, 1, 2, etc).
 * To better understand why, imagine kids in a kindergarten queuing for candies. Once they get some,
 * they immediately eat them and go to the last position in the queue. Such distribution is fair (assuming the
 * number of candies is a multiple of the number of kids, and assuming their parents are fine with their children
 * eating candies).
 */
fun CoroutineScope.produceNumbers() = produce() {
	repeat(10) {
		delay(100)
		send(it)
	}
}

fun CoroutineScope.launchProcessor(
	id: Int,
	channel: ReceiveChannel<Int>
) = launch {
	for (msg in channel) {
		println("#$id received $msg")
	}
}

suspend fun main(): Unit = coroutineScope {
	val channel = produceNumbers()
	repeat(3) { id ->
		delay(10)
		launchProcessor(id, channel)
	}
}
