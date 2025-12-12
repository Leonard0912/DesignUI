package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.utils.ExtensionsKt;

import java.util.function.Function;

public interface WidthConstraint extends SuperConstraint<Float> {
    float getWidthImpl(UIComponent component);

    default float getWidth(UIComponent component) {
        return XConstraint.getCachedDebuggable(this, component, ConstraintType.WIDTH, c -> ExtensionsKt.roundToRealPixels(getWidthImpl(c)));
    }
}
