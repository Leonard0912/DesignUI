package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.RadiusConstraint;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

public class RadiusAnimationComponent extends AnimationComponent<Float> implements RadiusConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    
    private final RadiusConstraint oldConstraint;
    private final RadiusConstraint newConstraint;

    public RadiusAnimationComponent(AnimationStrategy strategy, int totalFrames, RadiusConstraint oldConstraint, RadiusConstraint newConstraint, int delay) {
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

    public RadiusConstraint getOldConstraint() {
        return oldConstraint;
    }

    public RadiusConstraint getNewConstraint() {
        return newConstraint;
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        update(component);

        float startRadius = oldConstraint.getRadius(component);
        float finalRadius = newConstraint.getRadius(component);

        return startRadius + ((finalRadius - startRadius) * getPercentComplete());
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
