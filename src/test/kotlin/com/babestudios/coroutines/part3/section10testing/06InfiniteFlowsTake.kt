package com.babestudios.coroutines.part3.section10testing

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * The test above is suspended forever by toList. The simplest (and, sadly, most popular) solution to this problem uses
 * take with a specific number of expected elements. The test below passes, but it loses a lot of information. Consider
 * a message that should not have been emitted by observeMessages yet was, albeit in the next position. This situation
 * would not be recognized by a unit test. A bigger problem is when someone makes a change in the code that makes it run
 * forever. Finding the reason behind this is much harder than it would be if our test were implemented as in the
 * following examples.
 */
class MessagesServiceTest2 {
	@Test
	fun `should emit messages from user`() = runTest { // given
		val source = flowOf(
			Message(fromUserId = "0", text = "A"),
			Message(fromUserId = "1", text = "B"),
			Message(fromUserId = "0", text = "C"),
		)
		val service = MessagesService(
			messagesSource = source,
			scope = backgroundScope,
		)
// when
		val result = service.observeMessages("0").take(2)
			.toList()

		assertEquals(
			listOf(
				Message(fromUserId = "0", text = "A"),
				Message(fromUserId = "0", text = "C"),
			), result
		)
	}
}

