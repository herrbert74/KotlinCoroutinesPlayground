package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserDownloader(
	private val api: NetworkService
) {
	private val users = mutableListOf<User>()

	fun downloaded(): List<User> = users.toList()

	suspend fun fetchUser(id: Int) {
		val newUser = api.fetchUser(id)
		users += newUser
	}
}

class User(val name: String)

interface NetworkService {
	suspend fun fetchUser(id: Int): User
}

class FakeNetworkService : NetworkService {
	override suspend fun fetchUser(id: Int): User {
		delay(2)
		return User("User$id")
	}
}

suspend fun main() {
	val downloader = UserDownloader(FakeNetworkService())
	coroutineScope {
		repeat(1_000_000) {
			launch {
				downloader.fetchUser(it)
			}
		}
	}
	print(downloader.downloaded().size) // ~998242
}