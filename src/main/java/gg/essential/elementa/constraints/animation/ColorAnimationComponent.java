package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ColorConstraint;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

import java.awt.Color;

public class ColorAnimationComponent extends AnimationComponent<Color> implements ColorConstraint {
    private Color cachedValue = Color.WHITE;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    
    private final ColorConstraint oldConstraint;
    private final ColorConstraint newConstraint;

    public ColorAnimationComponent(AnimationStrategy strategy, int totalFrames, ColorConstraint oldConstraint, ColorConstraint newConstraint, int delay) {
        super(strategy, totalFrames, delay);
        this.oldConstraint = oldConstraint;
        this.newConstraint = newConstraint;
    }

    @Override
    public Color getCachedValue() {
        return cachedValue;
    }

    @Override
    public void setCachedValue(Color value) {
        this.cachedValue = value;
    }

    @Override
    public boolean getRecalculate() {
        return recalculate;
    }

    @Override
    public void setRecalculate(boolean value) {
        this.recalculate = value;
    }

    @Override
    public UIComponent getConstrainTo() {
        return constrainTo;
    }

    @Override
    public void setConstrainTo(UIComponent component) {
        this.constrainTo = component;
    }

    public ColorConstraint getOldConstraint() {
        return oldConstraint;
    }

    public ColorConstraint getNewConstraint() {
        return newConstraint;
    }

    @Override
    public Color getColorImpl(UIComponent component) {
        update(component);

        Color startColor = oldConstraint.getColor(component);
        Color endColor = newConstraint.getColor(component);
        float percentComplete = getPercentComplete();

        int newR = Math.round(startColor.getRed() + ((endColor.getRed() - startColor.getRed()) * percentComplete));
        int newG = Math.round(startColor.getGreen() + ((endColor.getGreen() - startColor.getGreen()) * percentComplete));
        int newB = Math.round(startColor.getBlue() + ((endColor.getBlue() - startColor.getBlue()) * percentComplete));
        int newA = Math.round(startColor.getAlpha() + ((endColor.getAlpha() - startColor.getAlpha()) * percentComplete));

        return new Color(newR, newG, newB, newA);
    }

    /**
     * @deprecated See ElementaVersion.V8
     */
    @Deprecated
    @Override
    @SuppressWarnings("deprecation")
    public void animationFrame() {
        super.animationFrame();
        oldConstraint.animationFrame();
        newConstraint.animationFrame();
    }

    @Override
    public SuperConstraint<Color> to(UIComponent component) {
        throw new UnsupportedOperationException("Constraint.to(UIComponent) is not available in this context!");
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        oldConstraint.visit(visitor, type, false);
        newConstraint.visit(visitor, type, false);
    }
}
