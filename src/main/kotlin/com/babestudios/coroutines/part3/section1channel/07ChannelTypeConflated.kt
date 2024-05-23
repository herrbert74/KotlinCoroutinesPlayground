package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * Finally, we will not be storing past elements when using the Channel.CONFLATED capacity. New elements will replace
 * the previous ones, so we will be able to receive only the last one, therefore we lose elements that were sent earlier.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = produce(capacity = Channel.CONFLATED) {
		repeat(5) { index ->
			send(index * 2)
			delay(100)
			println("Sent")
		}
	}
	delay(1000)
	for (element in channel) {
		println(element)
		delay(1000)
	}
}

