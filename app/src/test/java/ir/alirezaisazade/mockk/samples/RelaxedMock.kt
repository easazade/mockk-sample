package test.samples

import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.Test


/*
* MockKAnnotations.init should and could be called inside @Before annotated method.
* However, i called MockKAnnotations.init for each test method to explain the each sample for better understanding.
* */
class RelaxedMock {


    class Car {
        fun drive(direction: Direction): Outcome = Outcome.REAL

        fun gogo(): Unit {

        }
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

    /*
    * no need to use relaxed mockk in this method. This method completes successfully.
     */
    @Test
    fun `this is a class mocking example`() {
        val car = mockkClass(Car::class)

        every { car.drive(Direction.NORTH) } returns Outcome.OK

        car.drive(Direction.NORTH) // returns OK

        verify { car.drive(Direction.NORTH) }
    }

    /*
    The following test method fails, throws the following exception:
    io.mockk.MockKException: no answer found for: Car(#3).drive(test.samples.RelaxedMock$Direction$NORTH@7adf16aa)
     */
    @Test(expected = MockKException::class)
    fun `mocked Car without relaxed parameter, I didn't use every so it throws exception`() {
        val car = mockk<Car>()

        car.drive(Direction.NORTH) // returns null

        verify { car.drive(Direction.NORTH) }

        confirmVerified(car)
    }

    /*
    relaxed mockk usage 1
    To solve the previous problem, you can use relaxed parameter.
     */
    @Test
    fun `mocked Car but didn't use every, it does not throw error because i used relaxed parameter `() {
        val car = mockk<Car>(relaxed = true)

        car.drive(Direction.NORTH) // returns null

        verify { car.drive(Direction.NORTH) }

        confirmVerified(car)
    }


    /*
    relaxed mockk usage 2
    * another relaxed mockk example
    * */
    @RelaxedMockK
    lateinit var car2: Car

    @Test
    fun `relaxed example 2`() {
        MockKAnnotations.init(this)
        car2.drive(Direction.NORTH) // returns null

        verify { car2.drive(Direction.NORTH) }

        confirmVerified(car2)
    }


    /*
    * Mock relaxed for functions returning Unit - usage 1
    * */
    private val car3 = mockk<Car>(relaxUnitFun = true)

    @Test
    fun `relaxUnitFun sample 1`() {
        car3.gogo()
        car3.gogo()
        verify(exactly = 2) {
            car3.gogo()
        }
    }

    /*
    * Mock relaxed for functions returning Unit - usage 2
    * */
    @MockK(relaxUnitFun = true)
    lateinit var car4: Car

    @Test
    fun `relaxUnitFun sample 2`() {
        MockKAnnotations.init(this)
        car4.gogo()
        car4.gogo()
        verify(exactly = 2) {
            car4.gogo()
        }
    }

    /*
    * Mock relaxed for functions returning Unit - usage 3
    * */
    @MockK
    lateinit var car5: Car

    @Test
    fun `relaxUnitFun sample 3`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        car5.gogo()
        car5.gogo()
        verify(exactly = 2) {
            car5.gogo()
        }
    }

}