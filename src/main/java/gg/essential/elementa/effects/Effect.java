package gg.essential.elementa.effects;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UpdateFunc;
import gg.essential.universal.UMatrixStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic interface all effects need to follow.
 *
 * This is where you can affect any drawing done.
 */
public abstract class Effect {
    protected UIComponent boundComponent;
    private boolean boundComponentInitialized = false;

    UIComponent updateFuncParent = null;
    List<UpdateFunc> updateFuncs = null; // only allocated if used

    public void bindComponent(UIComponent component) {
        if (boundComponentInitialized && boundComponent != component) {
            throw new IllegalStateException("Attempt to bind component of a " + this.getClass().getSimpleName() +
                " which already has a bound component");
        }
        boundComponent = component;
        boundComponentInitialized = true;
    }

    protected UIComponent getBoundComponent() {
        return boundComponent;
    }

    protected void addUpdateFunc(UpdateFunc func) {
        if (updateFuncs == null) {
            updateFuncs = new ArrayList<>();
        }
        updateFuncs.add(func);

        if (updateFuncParent != null) {
            updateFuncParent.addUpdateFunc(this, updateFuncs.size() - 1, func);
        }
    }

    protected void removeUpdateFunc(UpdateFunc func) {
        if (updateFuncs == null) return;
        int index = updateFuncs.indexOf(func);
        if (index == -1) return;
        updateFuncs.remove(index);

        if (updateFuncParent != null) {
            updateFuncParent.removeUpdateFunc(this, index);
        }
    }

    /**
     * Called once inside of the component's afterInitialization function
     */
    public void setup() {}

    /**
     * Called in the component's animationFrame function
     * @deprecated See ElementaVersion.V8
     */
    @Deprecated
    public void animationFrame() {}

    /**
     * Set up all drawing, turn on shaders, etc.
     */
    public void beforeDraw(UMatrixStack matrixStack) {}

    /**
     * Called after this component draws but before it's children are drawn.
     */
    public void beforeChildrenDraw(UMatrixStack matrixStack) {}

    /**
     * Clean up all of this feature's GL states, etc.
     */
    public void afterDraw(UMatrixStack matrixStack) {}

    /**
     * @deprecated Use beforeDraw(UMatrixStack) instead
     */
    @Deprecated
    public void beforeDraw() {
        beforeDraw(UMatrixStack.Compat.get());
    }

    /**
     * @deprecated Use beforeChildrenDraw(UMatrixStack) instead
     */
    @Deprecated
    public void beforeChildrenDraw() {
        beforeChildrenDraw(UMatrixStack.Compat.get());
    }

    /**
     * @deprecated Use afterDraw(UMatrixStack) instead
     */
    @Deprecated
    public void afterDraw() {
        afterDraw(UMatrixStack.Compat.get());
    }

    @SuppressWarnings("deprecation")
    public void beforeDrawCompat(UMatrixStack matrixStack) {
        UMatrixStack.Compat.runLegacyMethod(matrixStack, this::beforeDraw);
    }

    @SuppressWarnings("deprecation")
    public void beforeChildrenDrawCompat(UMatrixStack matrixStack) {
        UMatrixStack.Compat.runLegacyMethod(matrixStack, this::beforeChildrenDraw);
    }

    @SuppressWarnings("deprecation")
    public void afterDrawCompat(UMatrixStack matrixStack) {
        UMatrixStack.Compat.runLegacyMethod(matrixStack, this::afterDraw);
    }
}
