package com.acmestore.play

import java.time.Year
import java.util.*
import kotlin.random.Random

fun main() {

    // Filter only cars with modelAge less than 10
    val cars = generateCars().filter { it.modelAge < 10 }.toList()
    println("Filtered cars\nTotal: ${cars.count()}")
    for (car in cars)
        print(
            "\nModel: ${car.model} / Model year: ${car.modelYear} / Model age: " +
                    "${car.modelAge} / Fuel type: ${car.fuelType} / Price: ${car.price}"
        )

    print("\n\n==========================================================================================================================\n\n")

    // Sum prices of all cars with modelAge less than 10
    val sum = generateCars().filter { it.modelAge < 10 }.map { it.price }.sum()
    print("Filtered cars - total price: $sum")

    print("\n\n==========================================================================================================================\n\n")

    //
    val result = generateCars().filter { it.modelAge < 10 }.map { it.price }.take(5)
        .reduce { acc, d -> ((acc + d) - ((acc + d) * 0.10)) }
    print("Result: $result")

    print("\n\n==========================================================================================================================\n\n")
}

enum class FuelType { ETHANOL, GASOLINE, FLEX }
data class Car(
    val model: String,
    val price: Double,
    val fuelType: FuelType,
    val modelYear: Year,
    val uniqueId: UUID = UUID.randomUUID()
)

val Car.modelAge: Int get() = Year.now().value - this.modelYear.value

fun randomFakeModel(): String = "C${Random.nextInt(100000) + 1}"
fun randomPrice(): Double = Random.nextDouble(5000.0, 50000.0)
fun randomFuelType(): FuelType = FuelType.values().random(Random)
fun randomYear(): Year = Year.of(1970).rangeTo(Year.now()).random()
fun ClosedRange<Year>.random(): Year =
    this.run {
        val startYear = start.value
        val endYear = endInclusive.value
        Random.nextInt(startYear, endYear)
    }.run(Year::of)

fun generateCars(): Sequence<Car> {
    val cars = arrayListOf<Car>()
    for (i in 1..50) cars.add(Car(randomFakeModel(), randomPrice(), randomFuelType(), randomYear()))
    return cars.iterator().asSequence()
    /*return generateSequence {
        Car(randomFakeModel(), randomPrice(), randomFuelType(), randomYear())
    }*/
}
