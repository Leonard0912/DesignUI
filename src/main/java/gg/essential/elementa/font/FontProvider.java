package gg.essential.elementa.font;

/**
 * Interface for providing font rendering capabilities.
 */
public interface FontProvider {
    float getStringWidth(String text, float pointSize);
    float getBaseLineHeight();
    float getBelowLineHeight();
    void drawString(Object matrixStack, String text, float x, float y, int color, boolean shadow);
}
