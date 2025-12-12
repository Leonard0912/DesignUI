package gg.essential.elementa.constraints.debug;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.ConstraintType;
import gg.essential.elementa.constraints.SuperConstraint;

/**
 * Interface for debugging constraints.
 */
public interface ConstraintDebugger {
    float evaluate(SuperConstraint<Float> constraint, ConstraintType type, UIComponent component);

    class Holder {
        private static ConstraintDebugger constraintDebugger = null;
    }

    static ConstraintDebugger getConstraintDebugger() {
        return Holder.constraintDebugger;
    }

    static void setConstraintDebugger(ConstraintDebugger debugger) {
        Holder.constraintDebugger = debugger;
    }
}
