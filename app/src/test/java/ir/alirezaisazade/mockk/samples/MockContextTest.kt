package test.samples

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import ir.alirezaisazade.mockk.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


//@RunWith(RobolectricTestRunner::class)
@RunWith(AndroidJUnit4::class)
class MockContextTest {

    @MockK
    lateinit var mMockContext: Context

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun setDiaByTextView() {
        val str = "Hello World !"

        every {
            mMockContext.getString(R.string.app_name)
        } returns str


        assertThat(
                mMockContext.getString(R.string.app_name)
        )
                .isEqualTo(str)
    }
}