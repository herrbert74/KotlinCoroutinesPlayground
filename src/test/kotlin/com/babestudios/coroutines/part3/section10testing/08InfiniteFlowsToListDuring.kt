package com.babestudios.coroutines.part3.section10testing

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * A good alternative is the toList function, which is only observed for some duration. It offers less flexibility, but
 * I like using it as it is simple and readable. This is how I implement and use such a function:
 */
suspend fun <T> Flow<T>.toListDuring(
	duration: Duration
): List<T> = coroutineScope {
	val result = mutableListOf<T>()
	val job = launch {
		this@toListDuring.collect(result::add)
	}
	delay(duration)
	job.cancel()
	return@coroutineScope result
}

class MessagesServiceTest4 {

	@Test
	fun `should emit messages from user`() = runTest {
		val source = flow {
			emit(Message(fromUserId = "0", text = "A"))
			emit(Message(fromUserId = "1", text = "B"))
			emit(Message(fromUserId = "0", text = "C"))
		}
		val service = MessagesService(
			messagesSource = source,
			scope = backgroundScope,
		)

		val emittedMessages = service.observeMessages("0").toListDuring(1.milliseconds)

		assertEquals(
			listOf(
				Message(fromUserId = "0", text = "A"),
				Message(fromUserId = "0", text = "C"),
			), emittedMessages
		)
	}

}

