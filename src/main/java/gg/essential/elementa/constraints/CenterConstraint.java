package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;
import gg.essential.elementa.utils.ExtensionsKt;

/**
 * Centers this box on the X or Y axis.
 */
public class CenterConstraint implements PositionConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;

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

    @Override
    public float getXPositionImpl(UIComponent component) {
        UIComponent parent = constrainTo != null ? constrainTo : component.getParent();

        if (component.isPositionCenter()) {
            return parent.getLeft() + ExtensionsKt.roundToRealPixels(parent.getWidth() / 2);
        } else {
            return parent.getLeft() + ExtensionsKt.roundToRealPixels(parent.getWidth() / 2 - component.getWidth() / 2);
        }
    }

    @Override
    public float getYPositionImpl(UIComponent component) {
        UIComponent parent = constrainTo != null ? constrainTo : component.getParent();

        if (component.isPositionCenter()) {
            return parent.getTop() + ExtensionsKt.roundToRealPixels(parent.getHeight() / 2);
        } else {
            return parent.getTop() + ExtensionsKt.roundToRealPixels(parent.getHeight() / 2 - component.getHeight() / 2);
        }
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case X:
                visitor.visitParent(ConstraintType.X);
                visitor.visitParent(ConstraintType.WIDTH);
                if (!visitor.getComponent().isPositionCenter()) {
                    visitor.visitSelf(ConstraintType.WIDTH);
                }
                break;
            case Y:
                visitor.visitParent(ConstraintType.Y);
                visitor.visitParent(ConstraintType.HEIGHT);
                if (!visitor.getComponent().isPositionCenter()) {
                    visitor.visitSelf(ConstraintType.HEIGHT);
                }
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
