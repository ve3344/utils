package me.lwb.utils.android

import android.app.Application
import me.lwb.context.AppContext

/**
 * Created by luowenbin on 4/1/2022.
 */
@Suppress("StaticFieldLeak")
object UtilsContext {
     val context: Application
        get() =  AppContext.context as? Application
            ?: AppContext.context.applicationContext as? Application
            ?: throw IllegalArgumentException("Context must be application")
}

