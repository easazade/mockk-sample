package test

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.Test


data class Car(var model: String) {
    fun drive(direction: Direction): Outcome = Outcome.REAL
}

sealed class Direction {
    object NORTH : Direction()
    object SOUTH : Direction()
}

sealed class Outcome {
    object OK : Outcome()
    object NOK : Outcome()
    object REAL : Outcome()
}

class ClassMocking {

    @Test
    fun `this is a class mocking example`() {
        val car = mockkClass(Car::class)

        every { car.drive(Direction.NORTH) } returns Outcome.OK

        car.drive(Direction.NORTH) // returns OK

        verify { car.drive(Direction.NORTH) }
    }

    @Test
    fun relax() {
        val car = mockk<Car>(relaxed = true)

        car.drive(Direction.NORTH) // returns null

        verify { car.drive(Direction.NORTH) }

        confirmVerified(car)
    }
}