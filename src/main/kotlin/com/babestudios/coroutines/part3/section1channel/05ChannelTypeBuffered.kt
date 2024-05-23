package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * With a capacity of concrete size, we will first produce until the buffer is full, after which the producer will need
 * to start waiting for the receiver.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = produce(capacity = 3) {
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
