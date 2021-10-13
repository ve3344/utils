package me.lwb.utils_demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
//import me.lwb.context.ContextHolder
//import me.lwb.utils.android.ext.*
//import me.lwb.utils.android.utils.SpannableUtils
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        ContextHolder.contextInternal = this
//        val time = AtomicInteger()
//        val textView2 = findViewById<TextView>(R.id.text2)
//        textView2.doOnClickAntiShake(1000, {
//            toast("YOU CLICK TOO FAST")
//        }) {
//            toast("REAL CLICK")
//            textView2.text = "TIME " + time.getAndIncrement()
//        }
//        textView2.configLayoutParams<LinearLayout.LayoutParams> {
//            width = LinearLayout.LayoutParams.MATCH_PARENT
//        }
//
//        val textView3 = findViewById<TextView>(R.id.text3)
//        textView3.doOnClickMultiple(onProgressClick = {
//            toast("CLICK AGAIN TO NEXT")
//        }) {
//            startActivity<SecondActivity>(){
//                withArgs {
//                    "name" to "hello"
//                    "age" to 3
//                }
//
//            }
////            exitProcess(0)
//        }
//        textView3.doOnClickCountdown(8, {
//            textView3.text = if (it > 0) "$it s" else "send"
//        }, {
//            toast("In cooling time")
//        }) {
//            toast("Send code")
//        }
//
//        with(findViewById<TextView>(R.id.text)) {
//            text = SpannableUtils.create {
//                text("red bg").backgroundColor(Color.RED)
//                text("blue fg").underline().foregroundColor(Color.BLUE).strikethrough()
//                text("20sp").absoluteSize(20.sp)
//                text("click me").click { toast("ok") }
//            }
//            movementMethod = LinkMovementMethod.getInstance()
//
//        }
//        with(findViewById<TextView>(R.id.text)) {
//            text = SpannableUtils.create {
//                text("已同意").foregroundColor(Color.DKGRAY)
//                text("隐私政策").underline().click { toast("隐私政策") }
//                text("和").absoluteSize(20.sp)
//                text("用户协议").underline().click { toast("用户协议") }
//            }
//            movementMethod = LinkMovementMethod.getInstance()
//        }

    }

    val TAG = this.javaClass.simpleName

}