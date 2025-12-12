package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

import java.util.List;

/**
 * Sets this component's width or height to be the max of its children's width or height
 */
public class ChildBasedMaxSizeConstraint implements SizeConstraint {
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
        UIComponent holder = constrainTo != null ? constrainTo : component;
        List<UIComponent> children = holder.getChildren();
        float max = 0f;
        for (UIComponent child : children) {
            float horizontalPadding = 0f;
            if (child.getConstraints().getX() instanceof PaddingConstraint) {
                horizontalPadding = ((PaddingConstraint) child.getConstraints().getX()).getHorizontalPadding(child);
            }
            float total = child.getWidth() + horizontalPadding;
            if (total > max) {
                max = child.getWidth();
            }
        }
        return max;
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        UIComponent holder = constrainTo != null ? constrainTo : component;
        List<UIComponent> children = holder.getChildren();
        float max = 0f;
        for (UIComponent child : children) {
            float verticalPadding = 0f;
            if (child.getConstraints().getY() instanceof PaddingConstraint) {
                verticalPadding = ((PaddingConstraint) child.getConstraints().getY()).getVerticalPadding(child);
            }
            float total = child.getHeight() + verticalPadding;
            if (total > max) {
                max = child.getHeight();
            }
        }
        return max;
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        UIComponent holder = constrainTo != null ? constrainTo : component;
        List<UIComponent> children = holder.getChildren();
        float max = 0f;
        for (UIComponent child : children) {
            if (child.getHeight() > max) {
                max = child.getHeight();
            }
        }
        return max * 2f;
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
