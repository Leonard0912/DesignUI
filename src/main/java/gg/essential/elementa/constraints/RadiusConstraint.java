package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.utils.ExtensionsKt;

import java.util.function.Function;

public interface RadiusConstraint extends SuperConstraint<Float> {
    float getRadiusImpl(UIComponent component);

    default float getRadius(UIComponent component) {
        return XConstraint.getCachedDebuggable(this, component, ConstraintType.RADIUS, c -> ExtensionsKt.roundToRealPixels(getRadiusImpl(c)));
    }
}
