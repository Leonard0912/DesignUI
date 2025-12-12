package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;

import java.awt.Color;
import java.util.function.Function;

public interface ColorConstraint extends SuperConstraint<Color> {
    Color getColorImpl(UIComponent component);

    default Color getColor(UIComponent component) {
        return XConstraint.getCached(this, component, this::getColorImpl);
    }
}
