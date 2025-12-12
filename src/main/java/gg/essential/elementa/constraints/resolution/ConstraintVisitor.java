package gg.essential.elementa.constraints.resolution;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.SuperConstraint;

/**
 * Visitor for constraint resolution.
 */
public interface ConstraintVisitor {
    UIComponent getComponent();
    void setConstraint(SuperConstraint<?> constraint, ConstraintType type);
    void visitSelf(ConstraintType type);
    void visitParent(ConstraintType type);
    void visitSibling(ConstraintType type, int index);
    void visitChildren(ConstraintType type);
}
