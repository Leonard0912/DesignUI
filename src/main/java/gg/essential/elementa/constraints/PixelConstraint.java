package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.MappedState;
import gg.essential.elementa.state.State;

import java.util.function.Function;

/**
 * Sets this component's X/Y position or width/height to be a constant
 * number of pixels.
 */
public class PixelConstraint implements MasterConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;

    private final MappedState<Float, Float> valueState;
    private final MappedState<Boolean, Boolean> alignOppositeState;
    private final MappedState<Boolean, Boolean> alignOutsideState;

    public PixelConstraint(State<Float> value, State<Boolean> alignOpposite, State<Boolean> alignOutside) {
        this.valueState = value.map(Function.identity());
        this.alignOppositeState = alignOpposite.map(Function.identity());
        this.alignOutsideState = alignOutside.map(Function.identity());
    }

    public PixelConstraint(State<Float> value, State<Boolean> alignOpposite) {
        this(value, alignOpposite, new BasicState<>(false));
    }

    public PixelConstraint(State<Float> value) {
        this(value, new BasicState<>(false), new BasicState<>(false));
    }

    public PixelConstraint(float value, boolean alignOpposite, boolean alignOutside) {
        this(new BasicState<>(value), new BasicState<>(alignOpposite), new BasicState<>(alignOutside));
    }

    public PixelConstraint(float value, boolean alignOpposite) {
        this(value, alignOpposite, false);
    }

    public PixelConstraint(float value) {
        this(value, false, false);
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

    public boolean isAlignOpposite() {
        return alignOppositeState.get();
    }

    public void setAlignOpposite(boolean value) {
        alignOppositeState.set(value);
    }

    public boolean isAlignOutside() {
        return alignOutsideState.get();
    }

    public void setAlignOutside(boolean value) {
        alignOutsideState.set(value);
    }

    public PixelConstraint bindValue(State<Float> newState) {
        valueState.rebind(newState);
        return this;
    }

    public PixelConstraint bindAlignOpposite(State<Boolean> newState) {
        alignOppositeState.rebind(newState);
        return this;
    }

    public PixelConstraint bindAlignOutside(State<Boolean> newState) {
        alignOutsideState.rebind(newState);
        return this;
    }

    @Override
    public float getXPositionImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        float value = valueState.get();

        if (alignOppositeState.get()) {
            if (alignOutsideState.get()) {
                return target.getRight() + value;
            } else {
                return target.getRight() - value - component.getWidth();
            }
        } else {
            if (alignOutsideState.get()) {
                return target.getLeft() - component.getWidth() - value;
            } else {
                return target.getLeft() + value;
            }
        }
    }

    @Override
    public float getYPositionImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();
        float value = valueState.get();

        if (alignOppositeState.get()) {
            if (alignOutsideState.get()) {
                return target.getBottom() + value;
            } else {
                return target.getBottom() - value - component.getHeight();
            }
        } else {
            if (alignOutsideState.get()) {
                return target.getTop() - component.getHeight() - value;
            } else {
                return target.getTop() + value;
            }
        }
    }

    @Override
    public float getWidthImpl(UIComponent component) {
        return valueState.get();
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        return valueState.get();
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        return valueState.get();
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case X:
                if (alignOppositeState.get()) {
                    visitor.visitParent(ConstraintType.X);
                    visitor.visitParent(ConstraintType.WIDTH);
                    if (alignOutsideState.get()) {
                        visitor.visitSelf(ConstraintType.WIDTH);
                    }
                } else {
                    visitor.visitParent(ConstraintType.X);
                    if (alignOutsideState.get()) {
                        visitor.visitSelf(ConstraintType.WIDTH);
                    }
                }
                break;
            case Y:
                if (alignOppositeState.get()) {
                    visitor.visitParent(ConstraintType.Y);
                    visitor.visitParent(ConstraintType.HEIGHT);
                    if (alignOutsideState.get()) {
                        visitor.visitSelf(ConstraintType.HEIGHT);
                    }
                } else {
                    visitor.visitParent(ConstraintType.Y);
                    if (alignOutsideState.get()) {
                        visitor.visitSelf(ConstraintType.HEIGHT);
                    }
                }
                break;
            case WIDTH:
            case HEIGHT:
            case RADIUS:
            case TEXT_SCALE:
                // No dependencies
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
