package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

import java.util.List;

/**
 * Tries to expand to fill all of the remaining width/height available in this component's
 * parent.
 *
 * When useSiblings is true, this constraint will have a size equal to the difference
 * between the parent's size and the sum of the sibling's size. When useSiblings is false,
 * it will only consider the position of this component and fill the rest of the space.
 */
public class FillConstraint implements SizeConstraint {
    private float cachedValue = 0f;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    private final boolean useSiblings;

    public FillConstraint(boolean useSiblings) {
        this.useSiblings = useSiblings;
    }

    public FillConstraint() {
        this(true);
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

    @Override
    public float getWidthImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();

        if (useSiblings) {
            float sum = 0;
            List<UIComponent> children = target.getChildren();
            for (UIComponent child : children) {
                if (child != component) {
                    sum += child.getWidth();
                }
            }
            return target.getWidth() - sum;
        } else {
            return target.getRight() - component.getLeft();
        }
    }

    @Override
    public float getHeightImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();

        if (useSiblings) {
            float sum = 0;
            List<UIComponent> children = target.getChildren();
            for (UIComponent child : children) {
                if (child != component) {
                    sum += child.getHeight();
                }
            }
            return target.getHeight() - sum;
        } else {
            return target.getBottom() - component.getTop();
        }
    }

    @Override
    public float getRadiusImpl(UIComponent component) {
        UIComponent target = constrainTo != null ? constrainTo : component.getParent();

        if (useSiblings) {
            float sum = 0;
            List<UIComponent> children = target.getChildren();
            for (UIComponent child : children) {
                if (child != component) {
                    sum += child.getRadius();
                }
            }
            return target.getRadius() - sum;
        } else {
            return (target.getRadius() - component.getLeft()) / 2f;
        }
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        switch (type) {
            case WIDTH:
                visitor.visitParent(ConstraintType.WIDTH);
                if (useSiblings) {
                    int indexInParent = visitor.getComponent().getParent().getChildren().indexOf(visitor.getComponent());
                    int numParentChildren = visitor.getComponent().getParent().getChildren().size();
                    for (int i = 0; i < numParentChildren; i++) {
                        if (indexInParent != i) {
                            visitor.visitSibling(ConstraintType.WIDTH, i);
                        }
                    }
                } else {
                    visitor.visitParent(ConstraintType.X);
                    visitor.visitSelf(ConstraintType.X);
                }
                break;
            case HEIGHT:
                visitor.visitParent(ConstraintType.HEIGHT);
                if (useSiblings) {
                    int indexInParent = visitor.getComponent().getParent().getChildren().indexOf(visitor.getComponent());
                    int numParentChildren = visitor.getComponent().getParent().getChildren().size();
                    for (int i = 0; i < numParentChildren; i++) {
                        if (indexInParent != i) {
                            visitor.visitSibling(ConstraintType.HEIGHT, i);
                        }
                    }
                } else {
                    visitor.visitParent(ConstraintType.Y);
                    visitor.visitSelf(ConstraintType.Y);
                }
                break;
            case RADIUS:
                visitor.visitParent(ConstraintType.RADIUS);
                if (useSiblings) {
                    int indexInParent = visitor.getComponent().getParent().getChildren().indexOf(visitor.getComponent());
                    int numParentChildren = visitor.getComponent().getParent().getChildren().size();
                    for (int i = 0; i < numParentChildren; i++) {
                        if (indexInParent != i) {
                            visitor.visitSibling(ConstraintType.RADIUS, i);
                        }
                    }
                } else {
                    visitor.visitSelf(ConstraintType.X);
                }
                break;
            default:
                throw new IllegalArgumentException(type.getPrettyName());
        }
    }
}
