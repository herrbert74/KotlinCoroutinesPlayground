package com.babestudios.coroutines.part3.section6flowbuilding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Flow is a cold data stream, so it produces values on demand when they are needed.
 * If you think of the allUsersFlow presented above, the next page of users will be requested when the receiver asks
 * for it. This is desired in some situations. For example, imagine that we are looking for a specific user.
 * If it is in the first page, we don’t need to request any more pages. To see this in practice, in the example below
 * we produce the next elements using the flow builder. Notice that the next page is requested lazily when it is needed.
 */
data class User(val name: String)

interface UserApi {
	suspend fun takePage(pageNumber: Int): List<User>
}

class FakeUserApi : UserApi {
	private val users = List(20) { User("User$it") }
	private val pageSize: Int = 3

	override suspend fun takePage(
		pageNumber: Int
	): List<User> {
		delay(1000) // suspending
		return users
			.drop(pageSize * pageNumber)
			.take(pageSize)
	}
}

fun allUsersFlow(api: UserApi): Flow<User> = flow {
	var page = 0
	do {
		println("Fetching page $page")
		val users = api.takePage(page++) // suspending
		emitAll(users.asFlow())
	} while (users.isNotEmpty())
}

suspend fun main() {
	val api = FakeUserApi()
	val users = allUsersFlow(api)
	val user = users
		.first {
			println("Checking $it")
			delay(1000)
			it.name == "User3" //suspending
		}
	println(user)
}