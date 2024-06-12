package com.babestudios.coroutines.part3.section10testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Testing classes that use StateFlow or SharedFlow is a bit more complicated. First, they need a scope; if we define
 * our test using runTest, this scope should be backgroundScope, not this, so our test does not await this scope’s
 * completion. Second, these flows are infinite, so they don’t complete unless their scope is cancelled. There are a
 * couple of ways to test infinite flows, which I will present by means of an example.
 * Consider the following service that can be used to observe messages from a specific user. This class also uses
 * SharedFlow so no more than one connection to the source of messages is made, even if there are multiple observers.
 * This means that observeMessages returns a flow that will never complete unless scope is cancelled.
 */
data class Message(val fromUserId: String, val text: String, val threadId: String = "")

class MessagesService(
	messagesSource: Flow<Message>, scope: CoroutineScope
) {
	private val source = messagesSource
		.shareIn(
			scope = scope,
			started = SharingStarted.WhileSubscribed()
		)

	fun observeMessages(fromUserId: String) = source.filter { it.fromUserId == fromUserId }
}

//To better understand the problem, consider the following failing test:
class MessagesServiceTest {
	// Failing test!
	@Test
	fun `should emit messages from user`() = runTest {

		val source = flowOf(
			Message(fromUserId = "0", text = "A"),
			Message(fromUserId = "1", text = "B"),
			Message(fromUserId = "0", text = "C"),
		)
		val service = MessagesService(
			messagesSource = source,
			scope = backgroundScope,
		)

		val result = service.observeMessages("0").toList() // Here we'll wait forever!

		assertEquals(
			listOf(
				Message(fromUserId = "0", text = "A"),
				Message(fromUserId = "0", text = "C"),
			), result
		)
	}
}

/**
 * The test above is suspended forever by toList. The simplest (and, sadly, most popular) solution to this problem uses
 * take with a specific number of expected elements. The test below passes, but it loses a lot of information. Consider
 * a message that should not have been emitted by observeMessages yet was, albeit in the next position. This situation
 * would not be recognized by a unit test. A bigger problem is when someone makes a change in the code that makes it run
 * forever. Finding the reason behind this is much harder than it would be if our test were implemented as in the
 * following examples.
 */