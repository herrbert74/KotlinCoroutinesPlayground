package com.babestudios.coroutines.part2.section10testing

import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class FetchUserDataTest {
	@Test
	fun `should load data concurrently`() = runTest {

		// given
		val userRepo = FakeUserDataRepository()
		val useCase = FetchUserUseCase(userRepo)

		// when
		useCase.fetchUserData()

		// then
		assertEquals(1000, currentTime)

	}

	@Test
	fun `should construct user`() = runTest {

		// given
		val userRepo = FakeUserDataRepository()
		val useCase = FetchUserUseCase(userRepo)

		// when
		val result = useCase.fetchUserData()

		// then
		val expectedUser = User(
			name = "Ben",
			friends = listOf(Friend("some-friend-id-1")),
			profile = Profile("Example description")
		)
		assertEquals(expectedUser, result)
	}
}
