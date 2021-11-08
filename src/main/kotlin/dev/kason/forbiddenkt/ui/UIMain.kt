/*
 * Forbidden Island Project: Team Stephanie Wang
 * Code is licensed under the MIT License, which you can view here: https://opensource.org/licenses/MIT
 */

package dev.kason.forbiddenkt.ui

import javax.swing.JPanel

object UIManager {
    val ui = hashMapOf<String, JPanel>()
    var currentUI: JPanel = JPanel()
    init {
        val property = System.getProperty("ui.first") ?: "NA"
        if(property != "NA" && ui.containsKey(property)) {
            currentUI = ui[property]!!
        }
    }
}