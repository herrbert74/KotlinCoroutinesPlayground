package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Multiple coroutines can send to a single channel.
 * In the below example, you can see two coroutines sending elements to the same channel.
 */
suspend fun sendString(
	channel: SendChannel<String>,
	text: String,
	time: Long
) {
	while (true) {
		delay(time)
		channel.send(text)
	}
}

fun main() = runBlocking {
	val channel = Channel<String>()
	launch { sendString(channel, "foo", 200L) }
	launch { sendString(channel, "BAR!", 500L) }
	repeat(50) {
		println(channel.receive())
	}
	coroutineContext.cancelChildren()
}
