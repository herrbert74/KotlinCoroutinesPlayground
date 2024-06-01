package com.babestudios.coroutines.part3.section9sharedflow

/**
 * It is also possible to define a custom strategy by implementing the SharingStarted interface.
 *
 * Using shareIn is very convenient when multiple services are interested in the same changes. Let’s say that you need
 * to observe how stored locations change over time. This is how a DTO (Data Transfer Object) could be implemented on
 * Android using the Room library (BUT IT IS A DAO):
 */
//@Dao
//interface LocationDao {
//	@Insert(onConflict = OnConflictStrategy.IGNORE)
//	suspend fun insertLocation(location: Location)
//
//	@Query("DELETE FROM location_table")
//	suspend fun deleteLocations()
//
//	@Query("SELECT * FROM location_table ORDER BY time")
//	fun observeLocations(): Flow<List<Location>>
//}
/**
 * The problem is that if multiple services need to depend on these locations, then it would not be optimal for each of
 * them to observe the database separately. Instead, we could make a service that listens to these changes and shares
 * them into SharedFlow. This is where we will use shareIn. But how should we configure it? You need to decide for
 * yourself. Do you want your subscribers to immediately receive the last list of locations? If so, set replay to 1.
 * If you only want to react to change, set it to 0. How about started? WhileSubscribed() sounds best for this use case.
 */
//class LocationService(
//	locationDao: LocationDao, scope: CoroutineScope
//) {
//	private val locations = locationDao.observeLocations()
//		.shareIn(
//			scope = scope,
//			started = SharingStarted.WhileSubscribed(),
//		)
//
//	fun observeLocations(): Flow<List<Location>> = locations
//}
/**
 * Beware! Do not create a new SharedFlow for each call. Create one, and store it in a property.
 */


