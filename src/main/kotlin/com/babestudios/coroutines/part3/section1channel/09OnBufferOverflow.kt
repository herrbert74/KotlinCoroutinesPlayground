package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * To customize channels further, we can control what happens when the buffer is full (onBufferOverflow parameter). There are the following options:
 *
 * • SUSPEND(default)-when the buffer is full, suspend on the send method.
 * • DROP_OLDEST-when the buffer is full, drop the oldest element.
 * • DROP_LATEST-when the buffer is full, drop the latest element.
 *
 * As you might guess, the channel capacity Channel.CONFLATED is the same as setting the capacity to 1 and
 * onBufferOverflow to DROP_OLDEST.
 * Currently, the produce function does not allow us to set custom onBufferOverflow, so to set it we need to define a
 * channel using the function Channel.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = Channel<Int>(
		capacity = 2,
		onBufferOverflow = BufferOverflow.DROP_OLDEST
	)
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
