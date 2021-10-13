package me.lwb.utils_demo

import android.net.Uri
import androidx.core.net.toUri
import androidx.core.util.rangeTo
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.lwb.utils.android.ext.targetPath
import me.lwb.utils.android.utils.ApkUtils
import me.lwb.utils.android.utils.UriUtils
import me.lwb.utils.android.utils.androidUri
import me.lwb.utils.ext.CloseableUtils.makeUse

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File

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
        assertEquals("me.lwb.utils_demo", appContext.packageName)

    }
}