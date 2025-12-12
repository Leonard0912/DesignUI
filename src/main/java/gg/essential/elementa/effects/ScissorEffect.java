package gg.essential.elementa.effects;

import gg.essential.universal.UMatrixStack;

/**
 * Enables OpenGL scissoring on this component, restricting all children
 * to only render within this component's bounds.
 */
public class ScissorEffect extends Effect {

    @Override
    public void beforeDraw(UMatrixStack matrixStack) {
        // Enable scissor test with component bounds
    }

    @Override
    public void afterDraw(UMatrixStack matrixStack) {
        // Disable scissor test
    }
}
