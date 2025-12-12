package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.utils.ExtensionsKt;

import java.util.function.Function;

public interface YConstraint extends SuperConstraint<Float> {
    float getYPositionImpl(UIComponent component);

    default float getYPosition(UIComponent component) {
        return XConstraint.getCachedDebuggable(this, component, ConstraintType.Y, c -> ExtensionsKt.roundToRealPixels(getYPositionImpl(c)));
    }
}
