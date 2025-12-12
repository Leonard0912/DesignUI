package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

/**
 * For size:
 * Sets the width/height to be value multiple of its own height/width respectively.
 *
 * For position:
 * Sets the x/y position to be value multiple of its own y/x position respectively.
 */
public class AspectConstraint implements PositionConstraint, SizeConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    private final float value;

    public AspectConstraint(float value) {
        this.value = value;
    }

    public AspectConstraint() {
        this(1f);
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

    public float getValue() {
        return value;
    }

    @Override
    public float getXPositionImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component;
        return target.getTop() * value;
    }

    @Override
    public float getYPositionImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component;
        return target.getLeft() * value;
    }

    @Override
    public float getWidthImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component;
        return target.getHeight() * value;
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component;
        return target.getWidth() * value;
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component;
        return target.getRadius() * value;
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case X:
                visitor.visitSelf(ConstraintType.Y);
                break;
            case Y:
                visitor.visitSelf(ConstraintType.X);
                break;
            case WIDTH:
                visitor.visitSelf(ConstraintType.HEIGHT);
                break;
            case HEIGHT:
                visitor.visitSelf(ConstraintType.WIDTH);
                break;
            case RADIUS:
                // TODO: ???
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
