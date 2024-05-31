package com.babestudios.coroutines.part3.section8flowprocessing

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Another well-known function for collections is flatMap. In the case of collections, it is similar to a map, but the
 * transformation function needs to return a collection that is then flattened. For example, if you have a list of
 * departments, each of which has a list of employees, you can use flatMap to make a list of all employees in all
 * departments.
 *
 * val allEmployees: List<Employee> = departments
 * 	.flatMap { department -> department.employees }
 *
 * // If we had used map, we would have a list of lists instead
 * val listOfListsOfEmployee: List<List<Employee>> = departments
 * 	.map { department -> department.employees }
 *
 * How should flatMap look on a flow? It seems intuitive that we might expect its transformation function to return a
 * flow that should then be flattened. The problem is that flow elements can be spread in time. So, should the flow
 * produced from the second element wait for the one produced from the first one, or should it process them
 * concurrently? Since there is no clear answer, there is no flatMap function for Flow, but instead there are
 * flatMapConcat, flatMapMerge and flatMapLatest.
 *
 * The flatMapConcat function is a sequential option, that do not introduce any asynchrony. Only after the first flow
 * is done, the second one can start. In the following example, we make a flow from the characters “A”, “B”, and “C”.
 * The flow produced by each of them includes these characters and the numbers 1, 2, and 3, with a 1-second delay in
 * between.
 */
fun flowFrom(elem: String) = flowOf(1, 2, 3)
	.onEach {
		println("Thread: ${Thread.currentThread()}")
		delay(1000)
	}
	.map { "${it}_${elem} " }

suspend fun main() {
	flowOf("A", "B", "C")
		.flatMapConcat { flowFrom(it) }
		.collect { println(it) }
}
