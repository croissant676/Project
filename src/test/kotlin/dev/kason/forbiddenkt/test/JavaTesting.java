
/*
 * Forbidden Island Project: Team Stephanie Wang
 * Code is licensed under the MIT License, which you can view here: https://opensource.org/licenses/MIT
 */

package dev.kason.forbiddenkt.test;

import dev.kason.forbiddenkt.Log;
import dev.kason.forbiddenkt.ui.ImageStorageManager;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.logging.Logger;

public class JavaTesting {
    public static void main(String[] args) {
        final Map<String, BufferedImage> images = ImageStorageManager.getImageStorage();
        Logger logger = Log.logger();
    }
}
