package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * The produce function closes the channel whenever the builder coroutine ends in any way (finished, stopped,
 * cancelled). Thanks to this, we will never forget to call close. The produce builder is a very popular way to
 * create a channel, and for good reason: it offers a lot of safety and convenience.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = produce {
		repeat(5) { index ->
			println("Producing next one")
			delay(1000)
			send(index * 2)
		}
	}
	for (element in channel) {
		println(element)
	}
}