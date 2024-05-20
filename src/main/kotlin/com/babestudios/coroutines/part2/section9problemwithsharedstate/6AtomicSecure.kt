package com.babestudios.coroutines.part2.section9problemwithsharedstate

import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * To secure our UserDownloader, we could use the AtomicReference wrapping around the read-only list of users.
 * We can use the getAndUpdate atomic function to update its value without conflicts.
 */
private class UserDownloader2(
	private val api: NetworkService
) {
	private val users = AtomicReference(listOf<User>())

	fun downloaded(): List<User> = users.get()

	suspend fun fetchUser(id: Int) {
		val newUser = api.fetchUser(id)
		users.getAndUpdate { it + newUser }
	}
}

suspend fun main() {
	val downloader = UserDownloader2(FakeNetworkService())
	coroutineScope {
		repeat(100_000) {
			launch {
				downloader.fetchUser(it)
			}
		}
	}
	print(downloader.downloaded().size) // 1000000
}
