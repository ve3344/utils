package me.lwb.utils_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import me.lwb.utils.android.ext.args

class SecondActivity : AppCompatActivity() {
    val name by args("name","")
    val age by args("age",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        with(findViewById<TextView>(R.id.tips)){
            text="AGE=$age NAME=$name"

        }
    }
}