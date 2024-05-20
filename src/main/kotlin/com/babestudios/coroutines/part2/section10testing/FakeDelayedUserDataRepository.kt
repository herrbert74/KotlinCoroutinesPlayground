package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.delay

class FakeUserDataRepository : UserDataRepository {

	override suspend fun getName(): String {
		delay(1000)
		return "Ben"
	}

	override suspend fun getProfile(): Profile {
		delay(1000)
		return Profile("Example description")
	}

	override suspend fun getFriends(): List<Friend> {
		delay(1000)
		return listOf(Friend("some-friend-id-1"))
	}

}

interface UserDataRepository {
	suspend fun getName(): String
	suspend fun getFriends(): List<Friend>
	suspend fun getProfile(): Profile
}

data class User(
	val name: String,
	val friends: List<Friend>, val profile: Profile
)

data class Friend(val id: String)
data class Profile(val description: String)