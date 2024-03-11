package com.babestudios.coroutines.part2.section6scopefunctions

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Unlike coroutine builders, if there is an exception in coroutineScope or any of its children,
 * it cancels all other children and rethrows it. This is why using coroutineScope would fix our
 * previous “Twitter example”.
 * To show that the same exception is rethrown, I changed a generic Error into a concrete ApiException.
 */
class ApiException(
	val code: Int,
	message: String
) : Throwable(message)

fun getFollowersNumberWithException(): Int =
	throw ApiException(500, "Service unavailable")

suspend fun getUserDetailsGood(): Details = coroutineScope {
	val userName = async { getUserName() }
	val followersNumber = async { getFollowersNumberWithException() }
	Details(userName.await(), followersNumber.await())
}

fun main() = runBlocking {
	val details = try {
		getUserDetailsGood()
	} catch (e: ApiException) {
		null
	}
	val tweets = async { getTweets() }
	println("User: $details")
	println("Tweets: ${tweets.await()}")
}
// User: null
// Tweets: [Tweet(text=Hello, world)]
