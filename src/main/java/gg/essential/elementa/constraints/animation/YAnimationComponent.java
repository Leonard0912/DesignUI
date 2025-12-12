package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.YConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

public class YAnimationComponent extends AnimationComponent<Float> implements YConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    
    private final YConstraint oldConstraint;
    private final YConstraint newConstraint;

    public YAnimationComponent(AnimationStrategy strategy, int totalFrames, YConstraint oldConstraint, YConstraint newConstraint, int delay) {
        super(strategy, totalFrames, delay);
        this.oldConstraint = oldConstraint;
        this.newConstraint = newConstraint;
    }

    @Override
    public Float getCachedValue() {
        return cachedValue;
    }

    @Override
    public void setCachedValue(Float value) {
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

    public YConstraint getOldConstraint() {
        return oldConstraint;
    }

    public YConstraint getNewConstraint() {
        return newConstraint;
    }

    @Override
    public float getYPositionImpl(UIComponent component) {
        update(component);

        float startY = oldConstraint.getYPosition(component);
        float finalY = newConstraint.getYPosition(component);

        return startY + ((finalY - startY) * getPercentComplete());
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
    public SuperConstraint<Float> to(UIComponent component) {
        throw new UnsupportedOperationException("Constraint.to(UIComponent) is not available in this context!");
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        oldConstraint.visit(visitor, type, false);
        newConstraint.visit(visitor, type, false);
    }
}
