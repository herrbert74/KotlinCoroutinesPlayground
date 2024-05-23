package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The common problem with this way of sending elements is that it is easy to forget to close the channel,
 * especially in the case of exceptions. If one coroutine stops producing because of an exception, the other will wait
 * for elements forever. It is much more convenient to use the produce function, which is a coroutine builder that
 * returns ReceiveChannel.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = Channel<Int>()
	launch {
		repeat(5) { index ->
			println("Producing next one")
			delay(1000)
			channel.send(index * 2)
		}
		channel.close()
	}

	launch {
		for (element in channel) {

			println(element)
		}
		// or
		// channel.consumeEach { element ->
		//     println(element)
		// }
	}
}
