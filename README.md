# TrackerApp

A Lightweight Kotlin SDK to measure execution time of operations in Android apps.

---

## Usage

### Enable Tracking

```kotlin
TrackerSDK.isEnabled = BuildConfig.DEBUG
TrackerSDK.logger = { tag, msg -> Log.d(tag, msg) }
```

### start/stop tracking

```kotlin
TrackerSDK.start("fetch_users")
// ... some logic
TrackerSDK.stop("fetch_users")

val duration = TrackerSDK.duration("fetch_users")
Log.d("Time", "fetch_users took $duration ms")
```

### Automatic block timing

```kotlin
TrackerSDK.measure("fetch_posts") {
    fetchPosts()
}
```

### Reset all trackings

```kotlin
TimeTracker.resetAll()
```

---

## Jetpack Compose Example

```kotlin
@Composable
fun Greeting(name: String) {
    LaunchedEffect(Unit) {
        TrackerSDK.measure("greeting_draw") {
            delay(1000) 
        }
    }

    Text("Hello $name!")
}
```

---

## ViewModel Example

```kotlin
class MyViewModel : ViewModel() {
    fun loadData() {
        TrackerSDK.measure("loadData") {
            // simulate loading
            Thread.sleep(1000)
        }
    }
}
```
