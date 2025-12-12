package gg.essential.elementa.components;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.ConstantColorConstraint;
import gg.essential.elementa.state.State;
import gg.essential.universal.UGraphics;
import gg.essential.universal.UMatrixStack;

import java.awt.Color;

/**
 * Extremely simple component that simply draws a colored rectangle.
 */
public class UIBlock extends UIComponent {

    public UIBlock(ColorConstraint colorConstraint) {
        setColor(colorConstraint);
    }

    public UIBlock(Color color) {
        this(new ConstantColorConstraint(color));
    }

    public UIBlock(State<Color> colorState) {
        this(new ConstantColorConstraint(colorState));
    }

    public UIBlock() {
        this(Color.WHITE);
    }

    @Override
    public void draw(UMatrixStack matrixStack) {
        beforeDrawCompat(matrixStack);

        double x = this.getLeft();
        double y = this.getTop();
        double x2 = this.getRight();
        double y2 = this.getBottom();

        drawBlock(matrixStack, getColor(), x, y, x2, y2);

        super.draw(matrixStack);
    }

    public static void drawBlock(UMatrixStack matrixStack, Color color, double x1, double y1, double x2, double y2) {
        if (color.getAlpha() == 0) return;

        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;

        // Basic quad rendering - actual implementation would use UGraphics
        // This is a simplified version
    }

    public static void drawBlockSized(UMatrixStack matrixStack, Color color, double x, double y, double width, double height) {
        drawBlock(matrixStack, color, x, y, x + width, y + height);
    }
}
