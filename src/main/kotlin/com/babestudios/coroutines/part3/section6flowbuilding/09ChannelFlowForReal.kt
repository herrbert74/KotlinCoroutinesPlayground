package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * On the other hand, we might have cases in which we want to fetch pages in advance when we are still processing the
 * elements. Doing this in the presented case could lead to more network calls, but it might also produce a faster
 * result. To achieve this, we would need independent production and consumption. Such independence is typical of
 * hot data streams, like channels. So, we need a hybrid of Channel and Flow. Yes, this is supported: we just need to
 * use the channelFlow function, which is like Flow because it implements the Flow interface. This builder is a regular
 * function, and it is started with a terminal operation (like collect). It is also like a Channel because once it is
 * started, it produces the values in a separate coroutine without waiting for the receiver. Therefore, fetching the
 * next pages and checking users happens concurrently.
 */

fun allUsersFlow2(api: UserApi): Flow<User> = channelFlow {
	var page = 0
	do {
		println("Fetching page $page")
		val users = api.takePage(page++) // suspending
		users.forEach { send(it) }
	} while (users.isNotEmpty())
}

suspend fun main() {
	val api = FakeUserApi()
	val users = allUsersFlow2(api)
	val user = users
		.first {
			println("Checking $it")
			delay(1000)
			it.name == "User3"
		}
	println(user)
}

/**
 * Inside channelFlow we operate on ProducerScope<T>. ProducerScope is the same type as used by the produce builder.
 * It implements CoroutineScope, so we can use it to start new coroutines with builders. To produce elements,
 * we use send instead of emit. We can also access the channel or control it directly with SendChannel functions.
 */

//interface ProducerScope<in E> :
//	CoroutineScope, SendChannel<E> { val channel: SendChannel<E>
//}

/**
 * A typical use case for channelFlow is when we need to independently compute values. To support this, channelFlow
 * creates a coroutine scope, so we can directly start coroutine builders like launch. The code below would not work
 * for flow because it does not create the scope needed by coroutine builders.
 */

//fun <T> Flow<T>.merge(other: Flow<T>): Flow<T> = channelFlow {
//	launch {
//		collect { send(it) }
//	}
//	other.collect { send(it) }
//}
//
//fun <T> contextualFlow(): Flow<T> = channelFlow {
//	launch(Dispatchers.IO) {
//		send(computeIoValue())
//	}
//	launch(Dispatchers.Default) {
//		send(computeCpuValue())
//	}
//}

/**
 * Just like all the other coroutines, channelFlow doesn’t finish until all its children are in a terminal state.
 */