package com.babestudios.coroutines.part3.section1channel.exercise

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class CoffeeType { ESPRESSO, LATTE }
class Milk
class GroundCoffee

sealed class Coffee

class Espresso(ground: GroundCoffee) : Coffee() {
	override fun toString(): String = "Espresso"
}

class Latte(milk: Milk, espresso: Espresso) : Coffee() {
	override fun toString(): String = "Latte"
}

suspend fun main() = coroutineScope {
	val orders = Channel<CoffeeType>(UNLIMITED)
	val coffees = Channel<Coffee>(UNLIMITED)

	repeat(4) { id ->
		launchProcessor(id, orders, coffees)
	}

	launch {
		for (coffee in coffees) {
			println("Coffee $coffee is ready")
		}
	}

	println("Welcome to Dream Coffee!")
	println("Press E to get espresso, L to get latte.")
	while (true) {
		val type = when (readlnOrNull()) {
			"E" -> CoffeeType.ESPRESSO
			"L" -> CoffeeType.LATTE
			else -> continue
		}
		orders.send(type)

		println("Order for $type sent")


	}
}

fun CoroutineScope.launchProcessor(
	id: Int,
	channel: ReceiveChannel<CoffeeType>,
	coffees: Channel<Coffee>
) = launch {
	val name = when (id) {
		0 -> "Alice"
		1 -> "Bob"
		2 -> "Celine"
		else -> "Dave"
	}
	for (msg in channel) {
		val coffee = makeCoffee(msg, name)
		coffees.send(coffee)
	}
}

private suspend fun makeCoffee(order: CoffeeType, baristaName: String): Coffee {
	val groundCoffee = groundCoffee(baristaName)
	val espresso = makeEspresso(groundCoffee, baristaName)
	return when (order) {
		CoffeeType.ESPRESSO -> espresso
		CoffeeType.LATTE -> {
			val milk = brewMilk(baristaName)
			Latte(milk, espresso)
		}
	}
}

suspend fun groundCoffee(baristaName: String): GroundCoffee {
	println("$baristaName: Grinding coffee...")
	delay(3000)
	return GroundCoffee()
}

suspend fun brewMilk(baristaName: String): Milk {
	println("$baristaName: Brewing milk...")
	delay(3000)
	return Milk()
}

suspend fun makeEspresso(ground: GroundCoffee, baristaName: String): Espresso {
	println("$baristaName: Making espresso...")
	delay(3000)
	return Espresso(ground)
}
