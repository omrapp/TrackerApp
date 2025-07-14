
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private interface TrackerAPI {
    fun start(tag: String)
    fun stop(tag: String, result: ((Long?) -> Unit)? = null)
    fun duration(tag: String): Long?
    fun <T> measure(tag: String, block: () -> T): T
    fun resetAll()

    var isEnabled: Boolean
}

object TrackerSDK : TrackerAPI {

    private val startTimes = mutableMapOf<String, Long>()
    private val durations = mutableMapOf<String, Long>()

    override var isEnabled: Boolean = true

    var logger: ((String, String) -> Unit) = { tag, message ->
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(tag, message)
        }

    }


    override fun start(tag: String) {

        if (!isEnabled) return

        startTimes[tag] = System.currentTimeMillis()
        logger.invoke("TrackerSDK", "Started timer for [$tag]")
    }

    override fun stop(tag: String, result: ((Long?) -> Unit)?) {
        if (!isEnabled) return

        val startTime = startTimes[tag]
        if(startTime != null) {
            val duration: Long = System.currentTimeMillis() - startTime
            durations[tag] = duration
            logger.invoke("TrackerSDK", "Stoped timer for [$tag]")
            result?.let { duration }
        } else {
            logger.invoke("TrackerSDK", "No start time found for tag: $tag")
        }
    }


    override fun duration(tag: String): Long? {
        return durations.get(tag)
    }

    override fun <T> measure(tag: String, block: () -> T): T {
        start(tag)
        val result = block()
        stop(tag)
        return result
    }

    override fun resetAll() {
        startTimes.clear()
        durations.clear()
    }

}