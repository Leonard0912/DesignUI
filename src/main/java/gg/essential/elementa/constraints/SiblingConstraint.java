package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

import java.util.List;

/**
 * Positions this component to be directly after its previous sibling.
 *
 * Intended for use in either the x or y direction but not both at the same time.
 * If you would like for components to try and fit inline, use CramSiblingConstraint.
 */
public class SiblingConstraint implements PositionConstraint, PaddingConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    
    private final float padding;
    private final boolean alignOpposite;

    public SiblingConstraint(float padding, boolean alignOpposite) {
        this.padding = padding;
        this.alignOpposite = alignOpposite;
    }

    public SiblingConstraint(float padding) {
        this(padding, false);
    }

    public SiblingConstraint() {
        this(0f, false);
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

    public boolean isAlignOpposite() {
        return alignOpposite;
    }

    @Override
    public float getXPositionImpl(UIComponent component) {
        if (constrainTo != null) {
            if (alignOpposite) {
                return constrainTo.getLeft() - component.getWidth() - padding;
            } else {
                return constrainTo.getRight() + padding;
            }
        }

        List<UIComponent> children = component.getParent().getChildren();
        int index = children.indexOf(component);

        if (alignOpposite) {
            if (index == 0) return component.getParent().getRight() - component.getWidth();
            UIComponent sibling = children.get(index - 1);
            return getLeftmostPoint(sibling, component.getParent(), index) - component.getWidth() - padding;
        } else {
            if (index == 0) return component.getParent().getLeft();
            UIComponent sibling = children.get(index - 1);
            return getRightmostPoint(sibling, component.getParent(), index) + padding;
        }
    }

    @Override
    public float getYPositionImpl(UIComponent component) {
        if (constrainTo != null) {
            if (alignOpposite) {
                return constrainTo.getTop() - component.getHeight() - padding;
            } else {
                return constrainTo.getBottom() + padding;
            }
        }

        List<UIComponent> children = component.getParent().getChildren();
        int index = children.indexOf(component);

        if (alignOpposite) {
            if (index == 0) return component.getParent().getBottom() - component.getHeight();
            UIComponent sibling = children.get(index - 1);
            return getHighestPoint(sibling, component.getParent(), index) - component.getHeight() - padding;
        } else {
            if (index == 0) return component.getParent().getTop();
            UIComponent sibling = children.get(index - 1);
            return getLowestPoint(sibling, component.getParent(), index) + padding;
        }
    }

    protected float getLowestPoint(UIComponent sibling, UIComponent parent, int index) {
        float lowestPoint = sibling.getBottom();
        List<UIComponent> children = parent.getChildren();

        for (int n = index - 1; n >= 0; n--) {
            UIComponent child = children.get(n);
            if (child.getTop() != sibling.getTop()) break;
            if (child.getBottom() > lowestPoint) lowestPoint = child.getBottom();
        }

        return lowestPoint;
    }

    protected float getHighestPoint(UIComponent sibling, UIComponent parent, int index) {
        float highestPoint = sibling.getTop();
        List<UIComponent> children = parent.getChildren();

        for (int n = index - 1; n >= 0; n--) {
            UIComponent child = children.get(n);
            if (child.getBottom() != sibling.getBottom()) break;
            if (child.getTop() < highestPoint) highestPoint = child.getTop();
        }

        return highestPoint;
    }

    protected float getRightmostPoint(UIComponent sibling, UIComponent parent, int index) {
        float rightmostPoint = sibling.getRight();
        List<UIComponent> children = parent.getChildren();

        for (int n = index - 1; n >= 0; n--) {
            UIComponent child = children.get(n);
            if (child.getLeft() != sibling.getLeft()) break;
            if (child.getRight() > rightmostPoint) rightmostPoint = child.getRight();
        }

        return rightmostPoint;
    }

    protected float getLeftmostPoint(UIComponent sibling, UIComponent parent, int index) {
        float leftmostPoint = sibling.getLeft();
        List<UIComponent> children = parent.getChildren();

        for (int n = index - 1; n >= 0; n--) {
            UIComponent child = children.get(n);
            if (child.getRight() != sibling.getRight()) break;
            if (child.getLeft() < leftmostPoint) leftmostPoint = child.getLeft();
        }

        return leftmostPoint;
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        int indexInParent = visitor.getComponent().getParent().getChildren().indexOf(visitor.getComponent());

        switch (type) {
            case X:
                if (alignOpposite) {
                    visitor.visitSelf(ConstraintType.WIDTH);
                    if (indexInParent <= 0) {
                        visitor.visitParent(ConstraintType.X);
                        visitor.visitParent(ConstraintType.WIDTH);
                        return;
                    }
                    for (int n = indexInParent - 1; n >= 0; n--) {
                        visitor.visitSibling(ConstraintType.X, n);
                        visitor.visitSibling(ConstraintType.WIDTH, n);
                    }
                } else {
                    if (indexInParent <= 0) {
                        visitor.visitParent(ConstraintType.X);
                        return;
                    }
                    for (int n = indexInParent - 1; n >= 0; n--) {
                        visitor.visitSibling(ConstraintType.X, n);
                        visitor.visitSibling(ConstraintType.WIDTH, n);
                    }
                }
                break;
            case Y:
                if (alignOpposite) {
                    visitor.visitSelf(ConstraintType.HEIGHT);
                    if (indexInParent <= 0) {
                        visitor.visitParent(ConstraintType.Y);
                        visitor.visitParent(ConstraintType.HEIGHT);
                        return;
                    }
                    for (int n = indexInParent - 1; n >= 0; n--) {
                        visitor.visitSibling(ConstraintType.Y, n);
                        visitor.visitSibling(ConstraintType.HEIGHT, n);
                    }
                } else {
                    if (indexInParent <= 0) {
                        visitor.visitParent(ConstraintType.Y);
                        return;
                    }
                    for (int n = indexInParent - 1; n >= 0; n--) {
                        visitor.visitSibling(ConstraintType.Y, n);
                        visitor.visitSibling(ConstraintType.HEIGHT, n);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }

    @Override
    public float getVerticalPadding(UIComponent component) {
        int index = component.getParent().getChildren().indexOf(component);
        return (index == 0 && constrainTo == null) ? 0f : padding;
    }

    @Override
    public float getHorizontalPadding(UIComponent component) {
        int index = component.getParent().getChildren().indexOf(component);
        return (index == 0 && constrainTo == null) ? 0f : padding;
    }
}
