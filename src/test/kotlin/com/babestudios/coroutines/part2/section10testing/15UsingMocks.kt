package com.babestudios.coroutines.part2.section10testing

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

/**
 * Using delay in fakes is easy but not very explicit. Many developers prefer to call delay in the test function.
 * One way to do this is using mocks:
 */
class UsingMocks {
	@Test
	fun `should load data concurrently`() = runTest {

		// given
		val userRepo = mockk<UserDataRepository>()
		coEvery { userRepo.getName() } coAnswers {
			delay(600)
			"aName"
		}
		coEvery { userRepo.getFriends() } coAnswers {
			delay(700)
			listOf(Friend("someFriends"))
		}
		coEvery { userRepo.getProfile() } coAnswers {
			delay(800)
			Profile("aProfile")
		}
		val useCase = FetchUserUseCase(userRepo)

		// when
		useCase.fetchUserData()

		// then
		assertEquals(800, currentTime)
	}

}
