package com.babestudios.coroutines.part3.section10testing

import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Most of the functions that return Flow call other functions that return Flow. This is the most common and simplest
 * case, so we’ll start from learning how to test such functions. Consider the following class:
 */
class ObserveAppointmentsService(
	private val appointmentRepository: AppointmentRepository
) {
	fun observeAppointments(): Flow<List<Appointment>> =

		appointmentRepository
			.observeAppointments()
			.filterIsInstance<AppointmentUpdate>()
			.map { it.appointments }
			.distinctUntilChanged()
			.retry {
				it is ApiException && it.code in 500..599
			}
}

/**
 * The observeAppointments method decorates observeAppointments from AppointmentRepository with a couple of operations,
 * including element filtering, mapping, elimination of repeating elements, and retrying in the case of certain kinds
 * of exceptions. If you asked me to explain what this function does but with a separate sentence for each
 * functionality, you would have a list of the unit tests this function should have:
 * • should keep only appointments from updates,
 * • should eliminate elements that are identical to the previous element,
 * • should retry if there is an API exception with the code 5XX.
 *
 * To implement these tests, we need to fake or mock AppointmentRepository. For these tests, we could make a fake object
 * whose observeAppointments function returns a constant Flow that will be used as a source. The simplest way to test a
 * function like observeAppointments is by defining its source flow using flowOf, which creates a finite Flow in which
 * time does not play a role. If time does not play a role in the function being tested, we can just transform its
 * result into a list using the toList function, then compare it with the expected result in the assertions.
 */
class FakeAppointmentRepository(
	private val flow: Flow<AppointmentEvent>
) : AppointmentRepository {
	override fun observeAppointments() = flow
}
class ObserveAppointmentsServiceTest {
	private val aDate1: Instant = Instant.parse("2020-08-30T18:43:00Z")
	private val anAppointment1 = Appointment("APP1", aDate1)
	private val aDate2: Instant = Instant.parse("2020-08-31T18:43:00Z")
	private val anAppointment2 = Appointment("APP2", aDate2)

	@Test
	fun `should keep only appointments from updates`() = runTest {

		val repo = FakeAppointmentRepository(
			flowOf(
				AppointmentConfirmed,
				AppointmentUpdate(listOf(anAppointment1)),
				AppointmentUpdate(listOf(anAppointment2)),
				AppointmentConfirmed,
			)
		)
		val service = ObserveAppointmentsService(repo)

		val result = service.observeAppointments().toList()

		assertEquals(
			listOf(
				listOf(anAppointment1),
				listOf(anAppointment2),
			),
			result
		)
	}
}


sealed class AppointmentEvent
data class AppointmentUpdate(
	val appointments: List<Appointment>
) : AppointmentEvent()

data object AppointmentConfirmed : AppointmentEvent()
data class Appointment(val title: String, val time: Instant)
data class ApiException(val code: Int) : Throwable()


interface AppointmentRepository {
	fun observeAppointments(): Flow<AppointmentEvent>
}