package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FetchUserUseCase(
	private val repo: UserDataRepository,
) {
	suspend fun fetchUserData(): User = coroutineScope {
		val name = async { repo.getName() }
		val friends = async { repo.getFriends() }
		val profile = async { repo.getProfile() }
		User(
			name = name.await(),
			friends = friends.await(),
			profile = profile.await()
		)
	}
}