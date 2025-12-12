package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.MappedState;
import gg.essential.elementa.state.State;

import java.util.function.Function;

/**
 * Sets this component's X/Y position or width/height to be some
 * multiple of its parents.
 */
public class RelativeConstraint implements PositionConstraint, SizeConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    private final MappedState<Float, Float> valueState;

    public RelativeConstraint(State<Float> value) {
        this.valueState = value.map(Function.identity());
    }

    public RelativeConstraint(float value) {
        this(new BasicState<>(value));
    }

    public RelativeConstraint() {
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
        return valueState.get();
    }

    public void setValue(float value) {
        valueState.set(value);
    }

    public RelativeConstraint bindValue(State<Float> newState) {
        valueState.rebind(newState);
        return this;
    }

    @Override
    public float getXPositionImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        return target.getLeft() + getWidth(component);
    }

    @Override
    public float getYPositionImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        return target.getTop() + getHeight(component);
    }

    @Override
    public float getWidthImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        return target.getWidth() * valueState.get();
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        return target.getHeight() * valueState.get();
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        return (target.getWidth() * valueState.get()) / 2f;
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case X:
                visitor.visitParent(ConstraintType.X);
                visitor.visitParent(ConstraintType.WIDTH);
                break;
            case Y:
                visitor.visitParent(ConstraintType.Y);
                visitor.visitParent(ConstraintType.HEIGHT);
                break;
            case WIDTH:
                visitor.visitParent(ConstraintType.WIDTH);
                break;
            case HEIGHT:
                visitor.visitParent(ConstraintType.HEIGHT);
                break;
            case RADIUS:
                visitor.visitParent(ConstraintType.WIDTH);
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
