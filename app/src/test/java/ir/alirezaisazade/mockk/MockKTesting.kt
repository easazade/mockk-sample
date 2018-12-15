package ir.alirezaisazade.mockk

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

data class User(val name: String)

class MockKTesting {


    @Test
    fun test1() {
        val mockObject = mockk<Any>()
        val mockUser = mockk<User>()
        every { mockUser.name } returns "alireza"
        val anme = mockUser.name
        verify { mockUser.name }
    }

}