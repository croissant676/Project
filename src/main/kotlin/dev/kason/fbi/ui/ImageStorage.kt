// Forbidden Island Project: Team Stephanie Wang
// Code is licensed under the MIT License, which you can view here: https://opensource.org/licenses/MIT

// File is finished: Do NOT edit anymore.

@file:JvmName("ImageStorageManager")

package dev.kason.fbi.ui

import dev.kason.fbi.Log
import dev.kason.fbi.errorPrintStream
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/** A custom map that specifically holds [String] and [BufferedImage].
 * The values are retrieved using [ClassLoader.getResource], and values stored.
 * This makes it more optimized compared to retrieving from [ClassLoader] every single
 * time.
 * @author Kason G.*/
class ImageStorage internal constructor(private val map: MutableMap<String, BufferedImage> = hashMapOf()) :
    Map<String, BufferedImage> by map {
    private val loader: ClassLoader = javaClass.classLoader
    private val logger = Log.logger()

    /** Returns the [BufferedImage] corresponding to [key], then stores
     * the result in a map. When getting the same image in the future, the
     * value in the map will be returned. */
    override operator fun get(key: String): BufferedImage? {
        if (map.containsKey(key)) return map[key]!!
        val resource = loader.getResource("\\images\\$key")
        if (resource == null) {
            logger.info("No resource: $key. This may indicate that the resources are missing.")
            return null
        }
        val file = File(resource.toURI())
        var image: BufferedImage? = null
        kotlin.runCatching {
            image = ImageIO.read(file)
        }.onFailure {
            it.printStackTrace(errorPrintStream)
        }
        return image
    }

    /** Generates the corresponding [BufferedImage] if possible, then stores the result.
     * Returns whether it was generated and stored correctly. */
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

/** A [Map] between [String] and [BufferedImage].
 * The following is completely valid:
 * ```
 * Map<String, BufferedImage> image = ImageStorageManager.getImageStorage();
 * ```
 * @author Kason G. */
val imageStorage = ImageStorage()