package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

import java.util.List;

/**
 * Sets this component's width or height to be the range (max - min) of its children's positions
 */
public class ChildBasedRangeConstraint implements WidthConstraint, HeightConstraint {
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
    public float getWidthImpl(UIComponent component) {
        List<UIComponent> children = component.getChildren();
        if (children.isEmpty()) return 0f;

        float leftMost = Float.MAX_VALUE;
        float rightMost = Float.MIN_VALUE;
        
        for (UIComponent child : children) {
            float left = child.getLeft();
            float right = child.getRight();
            if (left < leftMost) leftMost = left;
            if (right > rightMost) rightMost = right;
        }
        
        return Math.max(rightMost - leftMost, 0f);
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        List<UIComponent> children = component.getChildren();
        if (children.isEmpty()) return 0f;

        float topMost = Float.MAX_VALUE;
        float bottomMost = Float.MIN_VALUE;
        
        for (UIComponent child : children) {
            float top = child.getTop();
            float bottom = child.getBottom();
            if (top < topMost) topMost = top;
            if (bottom > bottomMost) bottomMost = bottom;
        }
        
        return Math.max(bottomMost - topMost, 0f);
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case WIDTH:
                visitor.visitChildren(ConstraintType.X);
                visitor.visitChildren(ConstraintType.WIDTH);
                break;
            case HEIGHT:
                visitor.visitChildren(ConstraintType.Y);
                visitor.visitChildren(ConstraintType.HEIGHT);
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
