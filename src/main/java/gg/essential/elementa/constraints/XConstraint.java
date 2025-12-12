package gg.essential.elementa.constraints;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.debug.ConstraintDebugger;
import gg.essential.elementa.utils.ExtensionsKt;

import java.util.function.Function;

public interface XConstraint extends SuperConstraint<Float> {
    float getXPositionImpl(UIComponent component);

    default float getXPosition(UIComponent component) {
        return getCachedDebuggable(component, ConstraintType.X, c -> ExtensionsKt.roundToRealPixels(getXPositionImpl(c)));
    }

    static float getCachedDebuggable(SuperConstraint<Float> constraint, UIComponent component, ConstraintType type, Function<UIComponent, Float> getImpl) {
        ConstraintDebugger debugger = ConstraintDebugger.getConstraintDebugger();
        if (debugger != null) {
            return debugger.evaluate(constraint, type, component);
        }
        return getCached(constraint, component, getImpl);
    }

    default float getCachedDebuggable(UIComponent component, ConstraintType type, Function<UIComponent, Float> getImpl) {
        return getCachedDebuggable(this, component, type, getImpl);
    }

    static <T> T getCached(SuperConstraint<T> constraint, UIComponent component, Function<UIComponent, T> getImpl) {
        if (constraint.getRecalculate()) {
            constraint.setCachedValue(getImpl.apply(component));
            Window window = Window.ofOrNull(component);
            if (window != null) {
                if (window.getVersion().compareTo(ElementaVersion.v8) >= 0) {
                    window.getCachedConstraints().add(constraint);
                }
                constraint.setRecalculate(false);
            }
        }
        return constraint.getCachedValue();
    }
}
