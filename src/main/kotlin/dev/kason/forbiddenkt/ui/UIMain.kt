/*
 * Forbidden Island Project: Team Stephanie Wang
 * Code is licensed under the MIT License, which you can view here: https://opensource.org/licenses/MIT
 */

package dev.kason.forbiddenkt.ui

import dev.kason.forbiddenkt.Log
import java.awt.Component
import javax.swing.JFrame
import javax.swing.JPanel

class UIManager(val ui: HashMap<String, JPanel> = hashMapOf()): Map<String, JPanel> by ui{
    var currentUI: JPanel = JPanel()
    private val logger = Log.logger()
    init {
        val property = System.getProperty("ui.first") ?: "NA"
        if(property != "NA" && ui.containsKey(property)) {
            currentUI = ui[property]!!
        }
    }
    val frame = JFrame("Hello World")
    fun display(string: String) {
        if(!ui.containsKey(string)) {
            logger.warning("There is no corresponding to \"$string\".")
            return
        }
        val panel = ui[string]!!
        if(string.startsWith("n:")) {
            val newFrame = JFrame()
            newFrame.apply {
                size = panel.size
                name = string.drop(2)
                isVisible = true
                defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            }
            return
        }
        currentUI = panel
        update()
    }

    private fun update(): Component? = frame.add(currentUI)
}
val uiManager = UIManager()