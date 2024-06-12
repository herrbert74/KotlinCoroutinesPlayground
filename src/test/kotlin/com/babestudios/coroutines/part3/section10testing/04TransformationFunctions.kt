package com.babestudios.coroutines.part3.section10testing

import java.time.Instant
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * We should also test “should not retry upon a non-API exception” and “should not retry upon an API exception with a
 * non-5XX code”, but I will skip these test cases in this book.
 * Another option is to make a flow that first throws an exception that should cause a retry, and then it throws one
 * that should not. This way, we can test not only a sample exception that should cause a retry, but also one that
 * should not.
 */
class ObserveAppointmentsService4Test {

	private val aDate1: Instant = Instant.parse("2020-08-30T18:43:00Z")
	private val anAppointment1 = Appointment("APP1", aDate1)

	@Test
	fun `should retry when API exception with the code 5XX`() = runTest {
		var retried = false
		val someException = object : Exception() {}
		val repo = FakeAppointmentRepository(flow {
			emit(AppointmentUpdate(listOf(anAppointment1)))
			if (!retried) {
				retried = true
				throw ApiException(502)
			} else {
				throw someException
			}
		})
		val service = ObserveAppointmentsService(repo)

		val result = service.observeAppointments()
			.catch<Any> { emit(it) }
			.toList()

		assertTrue(retried)
		assertEquals(
			listOf(
				listOf(anAppointment1),
				listOf(anAppointment1),
				someException,
			), result
		)
	}
}