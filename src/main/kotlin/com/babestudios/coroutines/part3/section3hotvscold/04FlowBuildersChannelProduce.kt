package com.babestudios.coroutines.part3.section3hotvscold

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * Time to get back to coroutines. The most typical way to create a flow is by using a builder,
 * which is similar to the produce function. It is called flow.
 *
 * These builders are conceptually equivalent, but since the behavior of channel and flow is very different,
 * there are also important differences between these two functions. Take a look at the example below. Channels are
 * hot, so they immediately start calculating the values. This calculation starts in a separate coroutine.
 * This is why produce needs to be a coroutine builder that is defined as an extension function on CoroutineScope.
 * The calculation starts immediately, but since the default buffer size is 0 (rendezvous) it will soon be suspended
 * until the receiver is ready in the example below. Note that there is a difference between stopping production when
 * there is no receiver and producing on-demand. Channels, as hot data streams, produce elements independently of
 * their consumption and then keep them. They do not care how many receivers there are. Since each element can be
 * received only once, after the first receiver consumes all the elements, the second one will find a channel
 * that is empty and closed already. This is why it will receive no elements at all.
 */
private fun CoroutineScope.makeChannel() = produce {
	println("Channel started")
	for (i in 1..3) {
		delay(1000)
		send(i)
	}
}

suspend fun main() = coroutineScope {
	val channel = makeChannel()
	delay(1000)
	println("Calling channel...")
	for (value in channel) {
		println(value)
	}
	println("Consuming again...")
	for (value in channel) {
		println(value)
	}
}