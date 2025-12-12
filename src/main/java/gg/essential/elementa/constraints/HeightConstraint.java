package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.utils.ExtensionsKt;

import java.util.function.Function;

public interface HeightConstraint extends SuperConstraint<Float> {
    float getHeightImpl(UIComponent component);

    default float getHeight(UIComponent component) {
        return XConstraint.getCachedDebuggable(this, component, ConstraintType.HEIGHT, c -> ExtensionsKt.roundToRealPixels(getHeightImpl(c)));
    }

    default float getTextScale(UIComponent component) {
        return getHeight(component);
    }
}
