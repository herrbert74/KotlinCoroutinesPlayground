package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MessageService(
    private val messageRepository: MessageRepository
) {
    fun threadsSearch(
        query: Flow<String>
    ): Flow<MessageThread> = query.flatMapLatest { messageRepository.searchThreads(it)}

    fun subscribeThreads(
        threads: Flow<MessageThread>
    ): Flow<MessageThreadUpdate> = threads.flatMapMerge(concurrency = 1000) { messageRepository.subscribeThread(it.id)}

    fun sendMessages(
        messages: Flow<List<Message>>
    ): Flow<MessageSendingResponse> = messages.flatMapConcat { messageRepository.sendMessages(it) }
}

interface MessageRepository {
    fun searchThreads(
        query: String
    ): Flow<MessageThread>

    fun subscribeThread(
        threadId: String
    ): Flow<MessageThreadUpdate>

    fun sendMessages(
        messages: List<Message>
    ): Flow<MessageSendingResponse>
}

data class MessageThread(val id: String, val name: String)
data class MessageThreadUpdate(val threadId: String, val messages: List<Message>)
data class Message(val senderId: String, val text: String, val threadId: String)
data class MessageSendingResponse(val messageId: String, val success: Boolean)

class MessageServiceTests {
    @Test
    fun `should search for threads based on the last query`() = runTest {
        val repo = object : OpenMessageRepository() {
            override fun searchThreads(query: String): Flow<MessageThread> = flow {
                delay(1000)
                emit(MessageThread("Resp$query", "Name$query"))
            }
        }
        val service = MessageService(repo)
        val query = flow {
            emit("A")
            delay(500)
            emit("B")
            delay(1500)
            emit("C")
        }

        val result = service.threadsSearch(query)
            .withVirtualTime(this)
            .toList()

        assertEquals(
            listOf(
                ValueAndTime(MessageThread("RespB", "NameB"), 1500),
                ValueAndTime(MessageThread("RespC", "NameC"), 3000),
            ),
            result
        )
    }

    @Test
    fun `should search for all threads`() = runTest {
        val repo = object : OpenMessageRepository() {
            override fun searchThreads(query: String): Flow<MessageThread> = flow {
                delay(1000)
                emit(MessageThread("Resp$query", "Name$query"))
                delay(1000)
                emit(MessageThread("2Resp$query", "2Name$query"))
            }
        }
        val service = MessageService(repo)

        val query = flow {
            emit("A")
            delay(2500)
            emit("B")
            delay(1500)
            emit("C")
            delay(500)
            emit("D")
        }

        val result = service.threadsSearch(query).toList()

        assertEquals(
            listOf(
                MessageThread("RespA", "NameA"),
                MessageThread("2RespA", "2NameA"),
                MessageThread("RespB", "NameB"),
                MessageThread("RespD", "NameD"),
                MessageThread("2RespD", "2NameD"),
            ),
            result
        )
    }

    @Test
    fun `should subscribe to threads`() = runTest {
        val repo = object : OpenMessageRepository() {
            override fun subscribeThread(threadId: String): Flow<MessageThreadUpdate> = flow {
                emit(MessageThreadUpdate(threadId, listOf(Message("A", "B", threadId))))
                delay(1000)
                emit(MessageThreadUpdate(threadId, listOf(Message("C", "D", threadId))))
            }
        }
        val service = MessageService(repo)
        val threads = flow {
            emit(MessageThread("T1", "Name1"))
            delay(500)
            emit(MessageThread("T2", "Name2"))
            delay(1500)
            emit(MessageThread("T3", "Name3"))
        }

        val result = service.subscribeThreads(threads)
            .withVirtualTime(this)
            .toList()

        assertEquals(
            listOf(
                ValueAndTime(MessageThreadUpdate("T1", listOf(Message("A", "B", "T1"))), 0),
                ValueAndTime(MessageThreadUpdate("T2", listOf(Message("A", "B", "T2"))), 500),
                ValueAndTime(MessageThreadUpdate("T1", listOf(Message("C", "D", "T1"))), 1000),
                ValueAndTime(MessageThreadUpdate("T2", listOf(Message("C", "D", "T2"))), 1500),
                ValueAndTime(MessageThreadUpdate("T3", listOf(Message("A", "B", "T3"))), 2000),
                ValueAndTime(MessageThreadUpdate("T3", listOf(Message("C", "D", "T3"))), 3000),
            ),
            result
        )
    }

    @Test
    fun `should subscribe to unlimited number of threads`() = runTest {
        val repo = object : OpenMessageRepository() {
            override fun subscribeThread(threadId: String): Flow<MessageThreadUpdate> = flow {
                emit(MessageThreadUpdate(threadId, listOf(Message("A", "B", threadId))))
                delay(1000)
                emit(MessageThreadUpdate(threadId, listOf(Message("C", "D", threadId))))
            }
        }
        val service = MessageService(repo)
        val threads = flow {
            repeat(1000) {
                emit(MessageThread("T$it", "Name$it"))
                delay(1)
            }
        }

        val result = service.subscribeThreads(threads).toList()

        assertEquals(2000, result.size)
        assertEquals(999 + 1000, currentTime)
    }

    @Test
    fun `should send messages synchroniously`() = runTest {
        val repo = object : OpenMessageRepository() {
            override fun sendMessages(messages: List<Message>): Flow<MessageSendingResponse> = flow {
                messages.forEach {
                    delay(1000)
                    emit(MessageSendingResponse(it.threadId, true))
                }
            }
        }
        val service = MessageService(repo)
        val messages = channelFlow {
            send(listOf(Message("A", "B", "T1")))
            delay(500)
            send(listOf(Message("C", "D", "T2")))
            delay(1500)
            send(listOf(Message("E", "F", "T3")))
        }

        val result = service.sendMessages(messages)
            .withVirtualTime(this)
            .toList()

        assertEquals(
            listOf(
                ValueAndTime(MessageSendingResponse("T1", true), 1000),
                ValueAndTime(MessageSendingResponse("T2", true), 2000),
                ValueAndTime(MessageSendingResponse("T3", true), 3000),
            ),
            result
        )
    }
}

open class OpenMessageRepository : MessageRepository {
    override fun searchThreads(query: String): Flow<MessageThread> {
        TODO()
    }

    override fun subscribeThread(threadId: String): Flow<MessageThreadUpdate> {
        TODO()
    }

    override fun sendMessages(messages: List<Message>): Flow<MessageSendingResponse> {
        TODO()
    }
}
