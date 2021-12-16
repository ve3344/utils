# 轻量 kotlin工具库 & Android工具库
[![](https://jitpack.io/v/ve3344/utils.svg)](https://jitpack.io/#ve3344/utils)

# 安装

#### 添加仓库

```groovy
//in build.gradle(Project)
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

// 新版方式 settings.gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

#### 添加依赖

```groovy
//in build.gradle(module)
dependencies {
    //kotlin utils
    implementation "com.github.ve3344.utils:utils-kt:1.7.1"
    //android utils
    implementation "com.github.ve3344.utils:utils-android:1.7.1"
}
```

# 示例

## Kotlin 工具

- Boolean 三目运算

```kotlin
    //like ?:
val result = (input == "hello").map("yes", "no")
```

- File 拓展

```kotlin
File("cache").ensureDir()
File("cache").createDirIfNotExists()
File("cache").createFileIfNotExists()
File("cache").length().bytes2kb
```

- Process 拓展

```kotlin
 Runtime.getRuntime().exec("ls").use {
    it.waitFor()
}
```

- 消息摘要工具

```kotlin
ByteArray(5).md5()
ByteArray(5).sha1()
ByteArray(5).sha256()
ByteArray(5).hex()
```

...

## Android 工具

- 快速获取 SystemService

```kotlin
val wm = wifiManager
val bm = bluetoothManager
val am = audioManager

```

- 快速监听activity等的destroy事件
- 快速toast工具
```kotlin
doOnDestroy {
    toast("activity destroy")
}
```


- registerReceiver拓展

- startActivity拓展

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

- Apk 安装

```kotlin
ApkUtils.installApk(appContext, apkPath)
```

- Uri和path相互转换

```kotlin
File("test").androidUri().toPath()
UriUtils.fromAsset("a/b.txt")
```

- 快速创建 SpannableString

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

- 点击事件消抖 

- 双击退出

- 验证码倒计时

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



# License

``` license
 Copyright 2021, luowenbin 
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```