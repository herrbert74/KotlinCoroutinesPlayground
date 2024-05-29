package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Let’s say that you need a flow of events you listen for, like user clicks or other kinds of actions.
 * The listening process should be independent from the process of handling these events, so channelFlow would be a
 * good candidate. However, there is a better one: callbackFlow.
 * For a very long time, there was no difference between channelFlow and callbackFlow. In version 1.3.4, small changes
 * were introduced to make it less error-prone when using callbacks. However, the biggest difference is in how people
 * understand these functions: callbackFlow is for wrapping callbacks.
 *
 * Inside callbackFlow, we also operate on ProducerScope<T>. Here are a few functions that might be useful for wrapping
 * callbacks:
 * • awaitClose { ... } - a function that suspends until the channel is closed. Once it is closed, it invokes its
 * argument. awaitClose is very important for callbackFlow. Take a look at the example below. Without awaitClose,
 * the coroutine will end immediately after registering a callback. This is natural for a coroutine: its body has ended
 * and it has no children to wait for, so it ends. We use awaitClose (even with an empty body) to prevent this, and
 * we listen for elements until the channel is closed in some other way.
 * • trySendBlocking(value) - similar to send, but it is blocking instead of suspending, so it can be used on
 * non-suspending functions.
 * • close()-ends this channel.
 * • cancel(throwable)-ends this channel and sends an exception to the flow.
 *
 * Here is a typical example of how callbackFlow is used:
 */
//fun flowFrom(api: CallbackBasedApi): Flow<T> = callbackFlow {
//	val callback = object : Callback {
//		override fun onNextValue(value: T) {
//			trySendBlocking(value)
//		}
//
//		override fun onApiError(cause: Throwable) {
//			cancel(CancellationException("API Error", cause))
//		}
//
//		override fun onCompleted() = channel.close()
//	}
//	api.register(callback)
//	awaitClose { api.unregister(callback) }
//}
