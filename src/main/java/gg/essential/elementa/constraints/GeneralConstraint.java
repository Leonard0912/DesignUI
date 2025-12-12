package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;

public interface GeneralConstraint extends PositionConstraint, SizeConstraint {
    float getXValue(UIComponent component);
    float getYValue(UIComponent component);

    @Override
    default float getXPositionImpl(UIComponent component) {
        return getXValue(component);
    }

    @Override
    default float getYPositionImpl(UIComponent component) {
        return getYValue(component);
    }

    @Override
    default float getWidthImpl(UIComponent component) {
        return getXValue(component);
    }

    @Override
    default float getHeightImpl(UIComponent component) {
        return getYValue(component);
    }

    @Override
    default float getRadiusImpl(UIComponent component) {
        return getXValue(component);
    }
}
