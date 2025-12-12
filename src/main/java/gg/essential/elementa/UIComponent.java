package gg.essential.elementa;

import gg.essential.elementa.components.UpdateFunc;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.effects.Effect;
import gg.essential.elementa.state.v2.ReferenceHolder;
import gg.essential.universal.UMatrixStack;

import java.awt.Color;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * UIComponent is the base of all drawing, meaning
 * everything visible on the screen is a UIComponent.
 */
public abstract class UIComponent extends Observable implements ReferenceHolder {

    private String componentName = null;
    private final CopyOnWriteArrayList<UIComponent> children = new CopyOnWriteArrayList<>();
    private UIComponent parent;
    private boolean hasParentSet = false;
    private UIConstraints constraints;
    protected boolean isInitialized = false;
    public Window cachedWindow = null;

    public UIComponent() {
        this.constraints = new UIConstraints(this);
    }

    public String getComponentName() {
        if (componentName == null) {
            componentName = this.getClass().getSimpleName();
        }
        return componentName;
    }

    public void setComponentName(String name) {
        this.componentName = name;
    }

    public List<UIComponent> getChildren() {
        return children;
    }

    public UIComponent getParent() {
        return parent;
    }

    public void setParent(UIComponent parent) {
        this.parent = parent;
        this.hasParentSet = true;
    }

    public boolean hasParent() {
        return hasParentSet;
    }

    public UIConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(UIConstraints constraints) {
        this.constraints = constraints;
    }

    public UIComponent addChild(UIComponent component) {
        component.setParent(this);
        children.add(component);
        return this;
    }

    public UIComponent insertChildAt(UIComponent component, int index) {
        if (index < 0 || index > children.size()) {
            System.out.println("Bad index given to insertChildAt (index: " + index + ", children size: " + children.size());
            return this;
        }
        component.setParent(this);
        children.add(index, component);
        return this;
    }

    public UIComponent insertChildBefore(UIComponent newComponent, UIComponent targetComponent) {
        int indexOfExisting = children.indexOf(targetComponent);
        if (indexOfExisting == -1) {
            System.out.println("targetComponent given to insertChildBefore is not a child of this component");
            return this;
        }
        newComponent.setParent(this);
        children.add(indexOfExisting, newComponent);
        return this;
    }

    public UIComponent removeChild(UIComponent component) {
        children.remove(component);
        return this;
    }

    public void clearChildren() {
        children.clear();
    }

    public UIComponent setChildOf(UIComponent parent) {
        parent.addChild(this);
        return this;
    }

    public UIComponent constrain(UIConstraints.Builder builder) {
        builder.apply(constraints);
        return this;
    }

    public UIComponent setX(XConstraint constraint) {
        constraints.setX(constraint);
        return this;
    }

    public UIComponent setY(YConstraint constraint) {
        constraints.setY(constraint);
        return this;
    }

    public UIComponent setWidth(WidthConstraint constraint) {
        constraints.setWidth(constraint);
        return this;
    }

    public UIComponent setHeight(HeightConstraint constraint) {
        constraints.setHeight(constraint);
        return this;
    }

    public UIComponent setRadius(RadiusConstraint constraint) {
        constraints.setRadius(constraint);
        return this;
    }

    public UIComponent setColor(ColorConstraint constraint) {
        constraints.setColor(constraint);
        return this;
    }

    public float getLeft() {
        return constraints.getX().getXPosition(this);
    }

    public float getTop() {
        return constraints.getY().getYPosition(this);
    }

    public float getRight() {
        return getLeft() + getWidth();
    }

    public float getBottom() {
        return getTop() + getHeight();
    }

    public float getWidth() {
        return constraints.getWidth().getWidth(this);
    }

    public float getHeight() {
        return constraints.getHeight().getHeight(this);
    }

    public float getRadius() {
        return constraints.getRadius().getRadius(this);
    }

    public Color getColor() {
        return constraints.getColor().getColor(this);
    }

    public boolean isPositionCenter() {
        return false; // Override in subclasses if needed
    }

    public UIComponent enableEffect(Effect effect) {
        effect.bindComponent(this);
        // Add to effects list
        return this;
    }

    public AnimatingConstraints makeAnimation() {
        return new AnimatingConstraints(this, constraints);
    }

    public UIComponent animateTo(AnimatingConstraints constraints) {
        this.constraints = constraints;
        return this;
    }

    public void draw(UMatrixStack matrixStack) {
        // Override in subclasses
        for (UIComponent child : children) {
            child.draw(matrixStack);
        }
    }

    protected void beforeDrawCompat(UMatrixStack matrixStack) {
        // Override in subclasses
    }

    public void addUpdateFunc(UpdateFunc func) {
        // Implementation
    }

    public void removeUpdateFunc(UpdateFunc func) {
        // Implementation
    }

    public void addUpdateFunc(Effect effect, int index, UpdateFunc func) {
        // Implementation for effect update funcs
    }

    public void removeUpdateFunc(Effect effect, int index) {
        // Implementation for effect update funcs
    }

    // Mouse event methods
    public UIComponent onMouseEnterRunnable(Runnable listener) {
        return this;
    }

    public UIComponent onMouseLeaveRunnable(Runnable listener) {
        return this;
    }

    public UIComponent onMouseClickRunnable(Runnable listener) {
        return this;
    }

    @Override
    public void holdOnto(Object reference) {
        // Implementation
    }
}
