# kotlin工具库 & Android工具库

# Installation

#### Stpe1. add repository

```groovy
//in build.gradle(Project)
allprojects {
    repositories {
        maven { url "https://gitee.com/ve3344/repo/raw/master/" }
    }
}

// or settings.gradle
dependencyResolutionManagement {
    repositories {
        maven { url "https://gitee.com/ve3344/repo/raw/master/" }
    }
}
```

#### Stpe2. add dependency

```groovy
//in build.gradle(module)
dependencies {
    implementation "me.lwb:utils-kt:1.3.0"
    implementation "me.lwb:utils-android:1.3.0"
}
```

# Example

## Kotlin extension

BooleanExt

```kotlin
    //like ?:
val result = (input == "hello").map("yes", "no")
```

FileExt

```kotlin
File("cache").ensureDir()
File("cache").createDirIfNotExists()
File("cache").createFileIfNotExists()
File("cache").length().bytes2kb
```

ProcessExt

```kotlin
 Runtime.getRuntime().exec("ls").use {
    it.waitFor()
}
```

MessageDigestExt

```kotlin
ByteArray(5).md5()
ByteArray(5).sha1()
ByteArray(5).sha256()
ByteArray(5).hex()
```

## Android extension

SystemServiceExt

```kotlin
val wm = wifiManager
val bm = bluetoothManager
val am = audioManager

```

```kotlin
doOnDestroy {
    toast("activity destroy")
}
```

ToastExt ActivityExt SystemServiceExt

```kotlin
registerReceiver(
    WifiManager.WIFI_STATE_CHANGED_ACTION,
    WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
) {
    it ?: return@registerReceiver
    val rssi = it.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -1000)
    toast("$rssi")
}
startActivity<XXActivity> {
    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    withArgs {
        "key" to "value"
    }
}
```

ApkUtils

```kotlin
ApkUtils.installApk(appContext, apkPath)
```

UriUtils

```kotlin
File("test").androidUri().targetPath()
UriUtils.fromAsset("a/b.txt")
```

SpannableUtils

```kotlin
with(findViewById<TextView>(R.id.text)) {
    text = SpannableUtils.create {
        text("已同意").foregroundColor(Color.DKGRAY)
        text("隐私政策").underline().click { toast("隐私政策") }
        text("和").absoluteSize(20.sp)
        text("用户协议").underline().click { toast("用户协议") }
    }
    movementMethod = LinkMovementMethod.getInstance()
}
```

ViewExt

```kotlin
val time = AtomicInteger()
btn.doOnClickAntiShake(1000, {
    toast("YOU CLICK TOO FAST")
}) {
    toast("REAL CLICK")
    textView2.text = "TIME " + time.getAndIncrement()
}

btn.doOnClickMultiple(onClickProgress = {
    toast("CLICK AGAIN TO EXIT")
}) {
    exitProcess(0)
}
btn.doOnClickCountdown(60, {
    textView3.text = if (it > 0) "$it S" else "SEND"
}, {
    toast("IN COOLING TIME")
}) {
    toast("SEND CODE")
}
btn.configLayoutParams<LinearLayout.LayoutParams> {
    width = LinearLayout.LayoutParams.MATCH_PARENT
}
//config LayoutParams
```