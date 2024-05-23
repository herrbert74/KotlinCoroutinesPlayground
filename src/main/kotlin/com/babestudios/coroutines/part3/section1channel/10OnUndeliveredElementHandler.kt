package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * One more Channel function parameter which we should know about is onUndeliveredElement.
 * It is called when an element couldn’t be handled for some reason. Most often this means that a channel was closed
 * or cancelled, but it might also happen when send, receive, receiveOrNull, or hasNext throw an error.
 * We generally use it to close resources that are sent by this channel.
 *
 * NO WORKING EXAMPLE
 */
suspend fun main(): Unit = coroutineScope {
	val channel = Channel<Int>(
		capacity = 2,
		onBufferOverflow = BufferOverflow.DROP_OLDEST,
	)
	// or
	// val channel = Channel<Resource>(
	//		capacity,
	//		onUndeliveredElement = { resource ->
	//			resource.close()
	//		}
	// )
	launch {
		repeat(5) { index ->
			channel.send(index * 2)
			delay(100)
			println("Sent")
		}
		channel.close()
	}
	delay(1000)
	for (element in channel) {
		println(element)
		delay(1000)
	}
}
