package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

/**
 * The number of flows that can be concurrently processed can be set using the concurrency parameter. The default value
 * of this parameter is 16, but it can be changed in the JVM using the DEFAULT_CONCURRENCY_PROPERTY_NAME property.
 * Beware of this default limitation because if you use flatMapMerge on a flow with many elements, only 16 will be
 * processed at the same time.
 */
suspend fun main() {
	flowOf("A", "B", "C")
		.flatMapMerge(concurrency = 2) { flowFrom(it) }
		.collect { println(it) }
}

/**
 * The typical use of flatMapMerge is when we need to request data for each element in a flow. For instance, we have a
 * list of categories, and you need to request offers for each of them. You already know that you can do this with the
 * async function. There are two advantages of using a flow with flatMapMerge instead:
 * • wecancontroltheconcurrencyparameteranddecidehowmanycategories we want to fetch at the same time
 * (to avoid sending hundreds of requests at the same time);
 * • we can return Flow and send the next elements as they arrive (so, on the function-use side, they can be handled
 * immediately).
 *
 * suspend fun getOffers(
 * 	categories: List<Category>
 * ): List<Offer> = coroutineScope {
 * 	categories
 * 		.map { async { api.requestOffers(it) } }
 * 		.flatMap { it.await() }
 * }
 *
 * // A better solution
 * suspend fun getOffers(
 * 	categories: List<Category>
 * ): Flow<Offer> = categories
 * 	.asFlow()
 * 	.flatMapMerge(concurrency = 20) {
 * 		flow { emit(api.requestOffers(it)) }
 * 		// or suspend { api.requestOffers(it) }.asFlow()
 * 	}
 *
 * Notice that in the above example I needed to wrap api.requestOffers(it) with flow builder. You cannot use flowOf
 * instead, because then suspension would happen during the creation of the flow, not during its collection.
 * flatMapMerge introduces asynchrony only for the collection of the flows, not for their creation.
 * flatMapMerge has a special behavior for the concurrency parameter set to 1. In this case, it behaves like
 * flatMapConcat. That is not consistent with the other values of flatMapMerge, because flatMapConcat operates on one
 * coroutine only (for producing and collecting its flows), and flatMapMerge operates on one corou- tine for producing
 * and the number of coroutines for collecting constrained by the concurrency parameter (so for concurrently set to 1
 * it should operate on 2 coroutines, not 1).
 */

