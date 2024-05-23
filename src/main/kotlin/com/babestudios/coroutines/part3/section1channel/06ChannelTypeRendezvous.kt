package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * With a channel of default (or Channel.RENDEZVOUS) capacity, the producer will always wait for a receiver.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = produce {
		// or produce(capacity = Channel.RENDEZVOUS) {
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

