package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

import java.util.List;

/**
 * Sets this component's width or height to be the sum of its children's width or height
 */
public class ChildBasedSizeConstraint implements SizeConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    private final float padding;

    public ChildBasedSizeConstraint(float padding) {
        this.padding = padding;
    }

    public ChildBasedSizeConstraint() {
        this(0f);
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

    public float getPadding() {
        return padding;
    }

    @Override
    public float getWidthImpl(UIComponent component) {
        UIComponent holder = constrainTo != null ? constrainTo : component;
        List<UIComponent> children = holder.getChildren();
        float sum = 0f;
        for (UIComponent child : children) {
            float horizontalPadding = 0f;
            if (child.getConstraints().getX() instanceof PaddingConstraint) {
                horizontalPadding = ((PaddingConstraint) child.getConstraints().getX()).getHorizontalPadding(child);
            }
            sum += child.getWidth() + horizontalPadding;
        }
        return sum + (children.size() - 1) * padding;
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        UIComponent holder = constrainTo != null ? constrainTo : component;
        List<UIComponent> children = holder.getChildren();
        float sum = 0f;
        for (UIComponent child : children) {
            float verticalPadding = 0f;
            if (child.getConstraints().getY() instanceof PaddingConstraint) {
                verticalPadding = ((PaddingConstraint) child.getConstraints().getY()).getVerticalPadding(child);
            }
            sum += child.getHeight() + verticalPadding;
        }
        return sum + (children.size() - 1) * padding;
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        UIComponent holder = constrainTo != null ? constrainTo : component;
        List<UIComponent> children = holder.getChildren();
        float sum = 0f;
        for (UIComponent child : children) {
            sum += child.getHeight();
        }
        return sum * 2f;
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case WIDTH:
                visitor.visitChildren(ConstraintType.WIDTH);
                break;
            case HEIGHT:
                visitor.visitChildren(ConstraintType.HEIGHT);
                break;
            case RADIUS:
                visitor.visitChildren(ConstraintType.HEIGHT);
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
