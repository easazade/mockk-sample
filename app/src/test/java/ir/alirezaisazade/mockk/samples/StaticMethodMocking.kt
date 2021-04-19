package test

import android.text.TextUtils
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test

/*
* Mocking static methods is not easy in mockito but using mockK java static methods can easily be mocked using mockStatic.
* */

class StringCheckerTest {

    @Test
    fun `Mocking static method example 1`() {
        mockkStatic(TextUtils::class)
        every {
            TextUtils.isEmpty(any())
        } returns true

        //when
        val result = TextUtils.isEmpty("test")
        //then
        Truth.assertThat(result).isEqualTo(true)
    }

    @Test
    fun `Mocking static method example 2`() {
        mockkStatic(TextUtils::class)
        every {
            TextUtils.isEmpty("omer")
        } returns true

        every {
            TextUtils.isEmpty("iyioz")
        } returns false

        Truth.assertThat(TextUtils.isEmpty("omer")).isEqualTo(true)
        Truth.assertThat(TextUtils.isEmpty("iyioz")).isEqualTo(false)
    }
}