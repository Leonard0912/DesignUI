package gg.essential.elementa.constraints;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.animation.AnimationComponent;
import gg.essential.elementa.constraints.debug.ConstraintDebugger;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;
import gg.essential.elementa.utils.ExtensionsKt;

import java.awt.Color;
import java.util.function.Function;

/**
 * The "super" constraint that all other constraints inherit from.
 *
 * @param <T> is what this constraint deals with, for example Float for WidthConstraint
 *           or Color for ColorConstraint
 */
public interface SuperConstraint<T> {
    T getCachedValue();
    void setCachedValue(T value);
    boolean getRecalculate();
    void setRecalculate(boolean value);
    UIComponent getConstrainTo();
    void setConstrainTo(UIComponent component);

    /**
     * @deprecated See ElementaVersion.V8
     */
    @Deprecated
    default void animationFrame() {
        setRecalculate(true);
    }

    default SuperConstraint<T> to(UIComponent component) {
        setConstrainTo(component);
        return this;
    }

    default void pauseIfSupported() {
        if (this instanceof AnimationComponent) {
            ((AnimationComponent<?>) this).pause();
        }
    }

    default void resumeIfSupported() {
        if (this instanceof AnimationComponent) {
            ((AnimationComponent<?>) this).resume();
        }
    }

    default void stopIfSupported() {
        if (this instanceof AnimationComponent) {
            ((AnimationComponent<?>) this).stop();
        }
    }

    default void visit(ConstraintVisitor visitor, ConstraintType type, boolean setNewConstraint) {
        // TODO: Support constrainTo
        if (getConstrainTo() != null) {
            return;
        }

        if (setNewConstraint) {
            visitor.setConstraint(this, type);
        }
        visitImpl(visitor, type);
    }

    default void visit(ConstraintVisitor visitor, ConstraintType type) {
        visit(visitor, type, true);
    }

    void visitImpl(ConstraintVisitor visitor, ConstraintType type);
}
