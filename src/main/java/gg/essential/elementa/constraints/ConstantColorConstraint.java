package gg.essential.elementa.constraints;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.MappedState;
import gg.essential.elementa.state.State;

import java.awt.Color;
import java.util.function.Function;

/**
 * Sets the color to be a constant, determined color.
 */
public class ConstantColorConstraint implements ColorConstraint {
    private Color cachedValue = Color.WHITE;
    private boolean recalculate = true;
    private UIComponent constrainTo = null;
    private final MappedState<Color, Color> colorState;

    public ConstantColorConstraint(State<Color> color) {
        this.colorState = color.map(Function.identity());
    }

    public ConstantColorConstraint(Color color) {
        this(new BasicState<>(color));
    }

    public ConstantColorConstraint() {
        this(Color.WHITE);
    }

    @Override
    public Color getCachedValue() {
        return cachedValue;
    }

    @Override
    public void setCachedValue(Color value) {
        this.cachedValue = value;
    }

    @Override
    public boolean getRecalculate() {
        return recalculate;
    }

    @Override
    public void setRecalculate(boolean value) {
        this.recalculate = value;
    }

    @Override
    public UIComponent getConstrainTo() {
        return constrainTo;
    }

    @Override
    public void setConstrainTo(UIComponent component) {
        this.constrainTo = component;
    }

    public Color getColor() {
        return colorState.get();
    }

    public void setColor(Color value) {
        colorState.set(value);
    }

    public ConstantColorConstraint bindColor(State<Color> newState) {
        colorState.rebind(newState);
        return this;
    }

    @Override
    public Color getColorImpl(UIComponent component) {
        return colorState.get();
    }

    @Override
    public SuperConstraint<Color> to(UIComponent component) {
        throw new UnsupportedOperationException("Constraint.to(UIComponent) is not available in this context!");
    }

    @Override
    public void visitImpl(ConstraintVisitor visitor, ConstraintType type) {
        // Color constraints will only ever have parent dependencies, so there is no possibility
        // of an invalid constraint here
    }
}
