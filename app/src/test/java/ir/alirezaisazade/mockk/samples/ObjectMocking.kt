package test

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test


object MockObj {
    fun add(): String {
        throw Exception("hata")
        return "Reality"
    }
}

object MyDelegate {
    fun testObjectMock(): String {
        return MockObj.add()
    }
}

class ObjectMocks {

    @Before
    fun beforeTests() {
        mockkObject(MockObj)
        every { MockObj.add() } returns "Fake"
    }

    @After
    fun afterTests() {
        unmockkAll()
        // or unmockkObject(MockObj)
    }

    @Test
    fun `object mocking test 1`() {
        Truth.assertThat(MockObj.add()).isEqualTo("Fake")
    }

    @Test
    fun `object mocking test 2`() {
        Truth.assertThat(MyDelegate.testObjectMock()).isEqualTo("Fake")
    }
}

