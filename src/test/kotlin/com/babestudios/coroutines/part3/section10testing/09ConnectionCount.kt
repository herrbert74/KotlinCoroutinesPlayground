package com.babestudios.coroutines.part3.section10testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * One of the most important functionalities of MessagesService is that it should start only one connection to the
 * source, no matter how many active observers we have.
 */
// Starts at most one connection to the source
//class MessagesService(
//	messagesSource: Flow<Message>, scope: CoroutineScope
//) {
//	private val source = messagesSource
//		.shareIn(
//			scope = scope,
//			started = SharingStarted.WhileSubscribed()
//		)
//
//	fun observeMessages(fromUserId: String) = source.filter { it.fromUserId == fromUserId }
//}
//
//// Can start multiple connections to the source
//class MessagesService(
//	messagesSource: Flow<Message>,
//) {
//	fun observeMessages(fromUserId: String) = messagesSource
//		.filter { it.fromUserId == fromUserId }
//}

/**
 * The simplest way to test this behavior is by making a flow that counts how many subscribers it has. This can be
 * done by incrementing a counter in onStart and decrementing it in onCompletion.
 */
private val infiniteFlow = flow<Nothing> {
	while (true) {
		delay(100)
	}
}

class MessagesServiceTest5 {
	@Test
	fun `should start at most one connection`() = runTest { // given
		var connectionsCounter = 0
		val source = infiniteFlow
			.onStart { connectionsCounter++ }
			.onCompletion { connectionsCounter-- }
		val service = MessagesService(
			messagesSource = source,
			scope = backgroundScope,
		)
// when
		service.observeMessages("0")
			.launchIn(backgroundScope)
		service.observeMessages("1")
			.launchIn(backgroundScope)
		service.observeMessages("0")
			.launchIn(backgroundScope)
		service.observeMessages("2")
			.launchIn(backgroundScope)
		delay(1000)
// then
		assertEquals(1, connectionsCounter)
	}
}