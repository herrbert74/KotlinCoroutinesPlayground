package com.babestudios.coroutines.part3.section10testing

import java.time.Instant
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Finally, consider the third functionality: “should retry when there is an API exception with the code 5XX”. If we
 * returned a flow that should not retry, we wouldn’t test the retry behavior. If we returned a flow that should retry,
 * the function being tested would retry infinitely and would produce an infinite flow. The easiest way to test an
 * infinite flow is by limiting the number of its elements using take.
 */
class ObserveAppointmentsService3Test {

	private val aDate1: Instant = Instant.parse("2020-08-30T18:43:00Z")
	private val anAppointment1 = Appointment("APP1", aDate1)

	@Test
	fun `should retry when API exception with the code 5XX`() = runTest {
		val repo = FakeAppointmentRepository(
			flow {
				emit(AppointmentUpdate(listOf(anAppointment1)))
				throw ApiException(502)
			})
		val service = ObserveAppointmentsService(repo)

		val result = service.observeAppointments().take(3)
			.toList()

		assertEquals(
			listOf(
				listOf(anAppointment1),
				listOf(anAppointment1),
				listOf(anAppointment1),
			), result
		)
	}
}