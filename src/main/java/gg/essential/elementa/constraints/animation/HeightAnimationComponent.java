package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.HeightConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.debug.ConstraintDebugger;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

public class HeightAnimationComponent extends AnimationComponent<Float> implements HeightConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    
    private final HeightConstraint oldConstraint;
    private final HeightConstraint newConstraint;

    public HeightAnimationComponent(AnimationStrategy strategy, int totalFrames, HeightConstraint oldConstraint, HeightConstraint newConstraint, int delay) {
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

    public HeightConstraint getOldConstraint() {
        return oldConstraint;
    }

    public HeightConstraint getNewConstraint() {
        return newConstraint;
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        update(component);

        float startHeight = oldConstraint.getHeight(component);
        float finalHeight = newConstraint.getHeight(component);

        return startHeight + ((finalHeight - startHeight) * getPercentComplete());
    }

    @Override
    public float getTextScale(UIComponent component) {
        ConstraintDebugger debugger = ConstraintDebugger.getConstraintDebugger();
        if (debugger != null) {
            return debugger.evaluate(this, ConstraintType.HEIGHT, component);
        }

        if (recalculate) {
            // Left deliberately un-rounded during an animation
            cachedValue = getHeightImpl(component);
            recalculate = false;
        }

        return cachedValue;
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
