package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.XConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

public class XAnimationComponent extends AnimationComponent<Float> implements XConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    
    private final XConstraint oldConstraint;
    private final XConstraint newConstraint;

    public XAnimationComponent(AnimationStrategy strategy, int totalFrames, XConstraint oldConstraint, XConstraint newConstraint, int delay) {
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

    public XConstraint getOldConstraint() {
        return oldConstraint;
    }

    public XConstraint getNewConstraint() {
        return newConstraint;
    }

    @Override
    public float getXPositionImpl(UIComponent component) {
        update(component);

        float startX = oldConstraint.getXPosition(component);
        float finalX = newConstraint.getXPosition(component);

        return startX + ((finalX - startX) * getPercentComplete());
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
