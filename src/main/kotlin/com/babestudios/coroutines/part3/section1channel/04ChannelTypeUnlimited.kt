package com.babestudios.coroutines.part3.section1channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * Depending on the capacity size we set, we distinguish four types of channels:
 * • Unlimited - channel with capacity Channel.UNLIMITED that has an unlimited capacity buffer, and send never suspends.
 * • Buffered - channel with concrete capacity size or Channel.BUFFERED (which is 64 by default and can be overridden
 * by setting the kotlinx.coroutines.channels.defaultBuffer system property in JVM).
 * • Rendezvous (default) - channel with capacity 0 or Channel.RENDEZVOUS (which is equal to 0),
 * meaning that an exchange can happen only if sender and receiver meet (so it is like a book exchange spot, instead
 * of a bookshelf).
 * • Conflated-channel with capacity Channel.CONFLATED which has a buffer of size 1, and each new element replaces the
 * previous one.
 *
 * Let’s now see these capacities in action. We can set them directly on Channel, but we can also set them when we call
 * the produce function.
 * We will make our producer fast and our receiver slow. With unlimited capacity, the channel should accept all the
 * elements and then let them be received one after another.
 */
suspend fun main(): Unit = coroutineScope {
	val channel = produce(capacity = Channel.UNLIMITED) {
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
