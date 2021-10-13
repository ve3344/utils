package me.lwb.utils.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.lwb.utils.android.ext.startActivity
import me.lwb.utils.android.ext.withArgs

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("me.lwb.utils.android.test", appContext.packageName)
        appContext.startActivity<AppCompatActivity> {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            withArgs{
                "name" to "value"
            }
        }
    }
}