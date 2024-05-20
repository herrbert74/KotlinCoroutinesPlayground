package com.babestudios.coroutines.part2.section9problemwithsharedstate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The second approach is known as fine-grained thread confinement.
 * In this approach, we wrap only those statements which modify the state. In our example,
 * these are all the lines where users is used. This approach is more demanding, but it offers us better performance
 * if the functions excluded from our critical section (like fetchUser in our example) are blocking or CPU-intensive.
 * If they are just plain suspending functions, the performance improvement is unlikely to be seen.
 * As a result, this function execution could slow down when we invoke functions that are blocking or CPU-intensive.
 */
class UserDownloader4(
	private val api: NetworkService
) {
	private val users = mutableListOf<User>()
	private val dispatcher = Dispatchers.IO
		.limitedParallelism(1)

	suspend fun downloaded(): List<User> =
		withContext(dispatcher) {
			users.toList()
		}

	suspend fun fetchUser(id: Int) {
		val newUser = api.fetchUser(id)
		withContext(dispatcher) {
			users += newUser
		}
	}
}

suspend fun main() {
	val downloader = UserDownloader4(FakeNetworkService())
	coroutineScope {
		repeat(1_000_000) {
			launch {
				downloader.fetchUser(it)
			}
		}
	}
	print(downloader.downloaded().size) // ~1000000
}
