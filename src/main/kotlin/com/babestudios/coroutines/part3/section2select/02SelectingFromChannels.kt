package com.babestudios.coroutines.part3.section2select

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * The select function can also be used with channels. These are the main functions that can be used inside it:
 * • onReceive - selected when this channel has a value. It receives this value (like the receive method) and uses it
 * as an argument for its lambda expression. When onReceive is selected, select returns the result of its lambda
 * expression.
 * • onReceiveCatching - selected when this channel has a value or is closed. It receives ChannelResult, which either
 * represents a value or signals that this channel is closed, and it uses this value as an argument for its lambda
 * expression. When onReceiveCatching is selected, select returns the result of its lambda expression.
 * • onSend - selected when this channel has space in the buffer. It sends a value to this channel (like the send
 * method) and invokes its lambda expression with a reference to the channel. When onSend is selected, select returns
 * Unit.
 * The select expression can be used with onReceive or onReceiveCatching to receive from multiple channels.
 *
 */
suspend fun CoroutineScope.produceString(s: String, time: Long) =
	produce {
		while (true) {
			delay(time)
			this.send(s)
		}
	}

fun main() = runBlocking {
	val fooChannel = produceString("foo", 210L)
	val barChannel = produceString("BAR", 500L)
	repeat(7) {
		select {
			fooChannel.onReceive {
				println("From fooChannel: $it")
			}
			barChannel.onReceive {
				println("From barChannel: $it")
			}
		}
	}
	coroutineContext.cancelChildren()
}
