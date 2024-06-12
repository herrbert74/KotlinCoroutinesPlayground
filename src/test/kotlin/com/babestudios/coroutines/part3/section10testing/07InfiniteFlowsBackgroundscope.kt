package com.babestudios.coroutines.part3.section10testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * The next approach is to start our flow in backgroundScope and store all the elements it emits in a collection. This
 * approach not only better shows us “what is” and “what should be” in failing cases; it also offers us much more
 * flexibility with testing time. In the example below, I’ve added some delays to verify when messages are sent.
 */
class MessagesServiceTest3 {
	@Test
	fun `should emit messages from user`() = runTest {

		val source = flow {
			emit(Message(fromUserId = "0", text = "A"))
			delay(1000)
			emit(Message(fromUserId = "1", text = "B"))
			emit(Message(fromUserId = "0", text = "C"))
		}
		val service = MessagesService(
			messagesSource = source,
			scope = backgroundScope,
		)

		val emittedMessages = mutableListOf<Message>()
		service.observeMessages("0")
			.onEach { emittedMessages.add(it) }
			.launchIn(backgroundScope)
		delay(1)

		assertEquals(
			listOf(
				Message(fromUserId = "0", text = "A"),
			), emittedMessages
		)

		delay(1000)

		assertEquals(
			listOf(
				Message(fromUserId = "0", text = "A"),
				Message(fromUserId = "0", text = "C"),
			), emittedMessages
		)
	}
}

