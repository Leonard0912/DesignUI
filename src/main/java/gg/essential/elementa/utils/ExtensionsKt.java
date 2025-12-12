package gg.essential.elementa.utils;

import gg.essential.universal.UResolution;

/**
 * Utility functions for Elementa.
 */
public class ExtensionsKt {
    /**
     * Rounds a value to the nearest real pixel.
     */
    public static float roundToRealPixels(float value) {
        float scaleFactor = (float) UResolution.getScaleFactor();
        return Math.round(value * scaleFactor) / scaleFactor;
    }
}
