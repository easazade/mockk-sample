package ir.alirezaisazade.mockk

import io.mockk.*
import org.hamcrest.core.IsCollectionContaining.hasItem
import org.junit.Assert.*
import org.junit.Test

data class User(val name: String)

class MockKTesting {


    @Test
    fun simple() {
        val mockObject = mockk<Any>()
        val mockUser = mockk<User>()
        every { mockUser.name } returns "alireza"
        val anme = mockUser.name
        verify { mockUser.name }
    }

    @Test
    fun multipleAnswers() {
        val mock = mockk<User>()
        every { mock.name } returns "alireza" andThen "saman"
        assertTrue(mock.name == "alireza")
        assertTrue(mock.name == "saman")
    }

    @Test
    fun mockingStaticJavaMethods() {
        mockkStatic(FighterJet::class)
        every { FighterJet.getModel() } returns "F-35"
        assertTrue(FighterJet.getModel() == "F-35")
    }

    @Test
    fun mockingCompanionObjectMethods() {
        mockkObject(Car.Companion)
        every { Car.getModel() } returns "benz"
        assertTrue(Car.getModel() == "benz")
    }

    @Test
    fun argumentMatcherType() {
        val mock = mockk<Car>()
        every { mock.startEngine(ofType(String::class)) } returns true
        assertTrue(mock.startEngine("awdma[pw"))
        assertTrue(mock.startEngine("awdma"))
        assertTrue(mock.startEngine("awdma0-3i9ej2390je"))
    }

    @Test
    fun argumentMatcherRange() {
        val mock = mockk<Car>()
        every { mock.setSpeed(range(0, 100)) } returns 50
        every { mock.setSpeed(range(100, 200)) } returns 150
        assertEquals(mock.setSpeed(10), 50)
        assertEquals(mock.setSpeed(110), 150)
    }

    @Test
    fun verifyAll() {
        val mock = mockk<Car>()
        every { mock.setSpeed(range(0, 100)) } returns 50
        mock.setSpeed(10)
        mock.setSpeed(20)
        mock.setSpeed(80)
        //verifies that given methods are all and only all the methods that were called note more not less
        io.mockk.verifyAll {
            mock.setSpeed(10)
            mock.setSpeed(20)
            mock.setSpeed(80)
        }
    }

    @Test
    fun verifyOrder() {
        val mock = mockk<Car>()
        every { mock.setSpeed(range(0, 100)) } returns 50
        mock.setSpeed(10)
        mock.setSpeed(18)
        mock.setSpeed(20)
        mock.setSpeed(65)
        mock.setSpeed(80)
        //verifies that given methods are called with the given order
        io.mockk.verifyOrder {
            mock.setSpeed(10)
            mock.setSpeed(65)
        }
    }

    @Test
    fun verifyNoCallToMock() {
        val mock = mockk<Car>()
        verify { mock wasNot called }
    }

    @Test
    fun combiningMatchers() {
        val mock = mockk<Car>()
        every { mock.setSpeed(and(range(0, 100), not(50))) } returns 50
        every { mock.setSpeed(50) } returns 61
        every { mock.setSpeed(or(200, 300)) } returns 250
        assertEquals(mock.setSpeed(40), 50)
        assertEquals(mock.setSpeed(50), 61)
        assertEquals(mock.setSpeed(300), 250)
        assertEquals(mock.setSpeed(200), 250)
    }

    @Test
    fun combiningMatchersToMock2() {
        val mock = mockk<Car>()
        every { mock.setSpeed(less(100)) } returns 0
        val list = mutableListOf(53, 92, 16)
        //returns different items chosen from list in each call
        every { mock.setSpeed(more(100, true)) } returnsMany list
        assertThat(list, hasItem(mock.setSpeed(150)))//hamcrest matchers
        assertThat(list, hasItem(mock.setSpeed(150)))//hamcrest matchers
        assertThat(list, hasItem(mock.setSpeed(150)))//hamcrest matchers
        assertThat(list, hasItem(mock.setSpeed(150)))//hamcrest matchers
        assertThat(list, hasItem(mock.setSpeed(150)))//hamcrest matchers
        assertEquals(mock.setSpeed(45), 0)
    }

    @Test
    fun relaxedMock() {
        /* Relaxed mock is the mock that returns some simple value for all functions. This allows to skip specifying
         behavior for each case, while still allow to stub things you need. For reference types chained mocks are returned. */
        val mock = mockk<Car>(relaxed = true)
        mock.go()
        mock.setSpeed(50)
        verify {
            mock.setSpeed(50)
            mock.go()
        }
    }

    @Test
    fun relaxedMockButOnlyForFunctionsWithReturnTypeOfUnit() {
        /*In case you would like Unit returning functions to be relaxed.
        You can use relaxUnitFun = true as an argument to mockk function, @MockKannotation or MockKAnntations.init function.*/
        val mock = mockk<Car>(relaxUnitFun = true)
        mock.go()
        verify {
            mock.go()
        }
        /*
        other approaches to achieve this

        @MockK
        lateinit var mock2: MockCls
        init {
            MockKAnnotations.init(this, relaxUnitFun = true)
        }
         */
    }

    @Test
    fun mockingAndUnMockingObjects() {
        mockkObject(SimpleSingle)
        val num = 2000
        every { SimpleSingle.randomNumberBelow1000() } returns num
        assertTrue(SimpleSingle.randomNumberBelow1000() == num)
        assertTrue(SimpleSingle.randomNumberBelow1000() == num)
        assertTrue(SimpleSingle.randomNumberBelow1000() == num)
        verify { SimpleSingle.randomNumberBelow1000() }
        unmockkObject(SimpleSingle) //or use unmockkAll
        assertTrue(SimpleSingle.randomNumberBelow1000() != num)
    }

    @Test
    fun mockMethodBehaviorWithUnitReturnType() {
        val car = mockk<Car>()
        every { car.go() } just runs
        car.go()
        verify { car.go() }
    }

}