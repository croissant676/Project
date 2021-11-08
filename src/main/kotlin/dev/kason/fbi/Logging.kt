// Forbidden Island Project: Team Stephanie Wang
// Code is licensed under the MIT License, which you can view here: https://opensource.org/licenses/MIT

@file:JvmName("LogUtils")

package dev.kason.fbi

import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.*
import java.util.logging.Formatter

object Log {
    private val handler = generateHandler()
    internal val format = SimpleDateFormat("hh:mm:ss.SSS aa MM/dd/yyyy")
    private val file: File? by lazy {
        val resource = javaClass.classLoader.getResource("run.log") ?: return@lazy run {
            println("\u001B[33m[${format.format(Date())}][WARNING](d.k.f.Log): Could not load resource folder, which may indicate that resources are missing.")
            null
        }
        File(resource.file)
    }
    private val writer: BufferedWriter? = if (file != null) BufferedWriter(FileWriter(file!!)) else null
    private fun generateHandler(): Handler {
        LogManager.getLogManager().reset()
        val handler = object : Handler() {
            override fun publish(record: LogRecord?) {
                if (record == null) return
                val colorPrefix = when (record.level) {
                    Level.WARNING -> "\u001B[33m"
                    Level.CONFIG -> "\u001B[36m"
                    Level.INFO -> "\u001B[34m"
                    else -> "\u001B[0m"
                }
                val string = formatter.format(record)
                if (record.level == Level.SEVERE) {
                    return System.err.println(colorPrefix + string)
                }
                println(colorPrefix + string)
                if (file != null) {
                    writer!!.write("Hello World")
                    println(writer)
                }
            }

            override fun flush() = Unit
            override fun close() = Unit
        }
        handler.formatter = object : Formatter() {
            override fun format(record: LogRecord?): String {
                if (record == null) return ""
                return record.run {
                    "[${format.format(Date.from(instant))}][${level.name.uppercase()}]($loggerName): $message"
                }
            }
        }
        return handler
    }

    fun logger(): Logger {
        val element = Thread.currentThread().stackTrace[2]
        val className = element.className
        val builder = StringBuilder()
        val tokens = className.split(".")
        for (index in tokens.indices) {
            if (index == tokens.lastIndex) {
                builder.append(tokens[index])
            } else {
                builder.append(tokens[index].first()).append('.')
            }
        }
        val logger = Logger.getLogger(builder.toString())
        logger.useParentHandlers = false
        logger.info("Created logger for $builder")
        logger.addHandler(handler)
        return logger
    }
}

fun Logger.info(any: Any?) = info(any.toString())
fun Logger.warning(any: Any?) = warning(any.toString())
fun Logger.severe(any: Any?) = severe(any.toString())
fun Logger.config(any: Any?) = config(any.toString())
inline fun Logger.log(block: Logger.() -> Unit) = this.block()

@JvmField
val errorPrintStream = PrintStream(object : OutputStream() {
    override fun write(b: ByteArray) = write(b, 0, b.size)
    override fun write(b: ByteArray, off: Int, len: Int) {
        val builder = StringBuilder(len)
        for (index in off until (off + len).coerceAtMost(b.size)) {
            builder.append(b[index].toInt().toChar())
        }
        println("[${Log.format.format(Date())}][SEVERE](d.k.f.ErrorPrintStream): $builder")
    }

    override fun write(b: Int) = Unit
})