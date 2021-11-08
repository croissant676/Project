package dev.kason.fbi.ui

import dev.kason.fbi.Log
import dev.kason.fbi.errorPrintStream
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class ImageStorage internal constructor(private val map: MutableMap<String, BufferedImage> = hashMapOf()) :
    Map<String, BufferedImage> by map {
    private val loader: ClassLoader = javaClass.classLoader
    private val logger = Log.logger()
    override operator fun get(key: String): BufferedImage? {
        if (map.containsKey(key)) return map[key]!!
        val resource = loader.getResource("\\images\\$key")
        if (resource == null) {
            logger.info("No resource: $key. This may indicate that the resources are missing.")
            return null
        }
        kotlin.runCatching {
            logger
        }.onFailure {
            it.printStackTrace(errorPrintStream)
            return null
        }
    }

    operator fun invoke(string: String): Boolean {
        val file = loader.getResource("\\images\\$string") ?: return false
        kotlin.runCatching {
            val image: BufferedImage = ImageIO.read(file)
            map[string] = image
        }.onFailure {
            it.printStackTrace(errorPrintStream)
            return false
        }
        return true
    }
}

val imageStorage = ImageStorage()