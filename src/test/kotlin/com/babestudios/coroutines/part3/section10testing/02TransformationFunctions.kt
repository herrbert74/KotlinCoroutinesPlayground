package com.babestudios.coroutines.part3.section10testing

import java.time.Instant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * The second test could be implemented the same way, but I would like to introduce one more element that will make our
 * test a bit more complicated and help us test what we haven’t yet tested. The problem with tests like the one above
 * is that they treat a flow just like a list; such an approach simplifies these tests, but they don’t verify if
 * elements are actually immediately transferred without any delays. Imagine that a developer added a delay to your
 * flow transformation, as in the following code snippet. Such a change would not be detected by the above test.
 *
 * I like to have a test that verifies time dependencies, and for that, we need to use runTest and some delay in our
 * source flow. The result flow needs to store information about when its elements were emitted, and we can verify the
 * result in an assertion.
 */
class ObserveAppointmentsService2Test {

	private val aDate1: Instant = Instant.parse("2020-08-30T18:43:00Z")
	private val anAppointment1 = Appointment("APP1", aDate1)
	private val aDate2: Instant = Instant.parse("2020-08-31T18:43:00Z")
	private val anAppointment2 = Appointment("APP2", aDate2)

	@Test
	fun `should eliminate elements that are identical to the previous element`() = runTest {

		val repo = FakeAppointmentRepository(
			flow {
				delay(1000)
				emit(AppointmentUpdate(listOf(anAppointment1)))
				emit(AppointmentUpdate(listOf(anAppointment1)))
				delay(1000)
				emit(AppointmentUpdate(listOf(anAppointment2)))
				delay(1000)
				emit(AppointmentUpdate(listOf(anAppointment2)))
				emit(AppointmentUpdate(listOf(anAppointment1)))
			})
		val service = ObserveAppointmentsService(repo)

		val result = service.observeAppointments().map { currentTime to it }
			.toList()

		assertEquals(
			listOf(
				1000L to listOf(anAppointment1),
				2000L to listOf(anAppointment2),
				3000L to listOf(anAppointment1),
			), result
		)
	}
}