package me.lwb.utils


object Deps {
    const val kotlinVersion="1.4.32"
    const val coroutinesVersion="1.5.2"

    const val kotlinStdlib="org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val kotlinCoroutinesCore="org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    const val kotlinCoroutinesAndroid="org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"


    const val appcompat="androidx.appcompat:appcompat:1.3.0"
    const val androidContextApi="com.github.ve3344.android-context:context-api:+"

    object Test{
        const val junit="junit:junit:4.13.2"
        const val androidxJunit="androidx.test.ext:junit:1.1.2"
        const val androidxEspresso="androidx.test.espresso:espresso-core:3.3.0"

    }
}