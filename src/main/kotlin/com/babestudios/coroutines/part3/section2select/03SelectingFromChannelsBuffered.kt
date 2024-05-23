package com.babestudios.coroutines.part3.section2select

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * The select function can be used with onSend to send to the first channel that has
 * space in the buffer.
 */
fun main(): Unit = runBlocking {
	val c1 = Channel<Char>(capacity = 2)
	val c2 = Channel<Char>(capacity = 2)
	// Send values
	launch {
		for (c in 'A'..'H') {
			delay(400)
			select {
				c1.onSend(c) { println("Sent $c to 1") }
				c2.onSend(c) { println("Sent $c to 2") }
			}
		}
	}

	// Receive values
	launch {
		while (true) {
			delay(1000)
			val c = select {
				c1.onReceive { "$it from 1" }
				c2.onReceive { "$it from 2" }
			}
			println("Received $c")
		}
	}
}