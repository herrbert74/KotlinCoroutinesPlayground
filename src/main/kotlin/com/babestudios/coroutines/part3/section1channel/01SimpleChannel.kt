package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * To see the simplest example of a channel, we need to have a producer (sender) and a consumer (receiver) in separate
 * coroutines. The producer will send elements, and the consumer will receive them. This is how it can be implemented:
 *
 * Such an implementation is far from perfect.
 * First, the receiver needs to know how many elements will be sent;
 * however, this is rarely the case, so we would prefer to listen for as long as the sender is willing to send.
 * To receive elements on the channel until it is closed, we could use a for-loop or consumeEach function.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = Channel<Int>()
	launch {
		repeat(5) { index ->
			delay(1000)
			println("Producing next one")
			channel.send(index * 2)
		}
	}
	launch {
		repeat(5) {
			val received = channel.receive()
			println(received)
		}
	}
}
