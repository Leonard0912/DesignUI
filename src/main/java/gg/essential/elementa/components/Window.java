package gg.essential.elementa.components;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.universal.UMatrixStack;
import gg.essential.universal.UResolution;

import java.util.HashSet;
import java.util.Set;

/**
 * The root component of an Elementa hierarchy.
 * All components must have a Window at the top of their hierarchy.
 */
public class Window extends UIComponent {
    private final ElementaVersion version;
    private long animationTimeMs = 0;
    private long animationTimeNs = 0;
    private int animationFPS = 244;
    final Set<SuperConstraint<?>> cachedConstraints = new HashSet<>();
    Float prevDraggedMouseX = null;
    Float prevDraggedMouseY = null;

    public Window(ElementaVersion version) {
        this.version = version;
    }

    @SuppressWarnings("deprecation")
    public Window() {
        this(ElementaVersion.V0);
    }

    public ElementaVersion getVersion() {
        return version;
    }

    public long getAnimationTimeMs() {
        return animationTimeMs;
    }

    public long getAnimationTimeNs() {
        return animationTimeNs;
    }

    public int getAnimationFPS() {
        return animationFPS;
    }

    public int getAnimationFPSOr1000() {
        if (version.compareTo(ElementaVersion.v8) >= 0) {
            return 1000;
        }
        return animationFPS;
    }

    public Set<SuperConstraint<?>> getCachedConstraints() {
        return cachedConstraints;
    }

    public void invalidateCachedConstraints() {
        for (SuperConstraint<?> constraint : cachedConstraints) {
            constraint.setRecalculate(true);
        }
        cachedConstraints.clear();
    }

    @Override
    public float getLeft() {
        return 0;
    }

    @Override
    public float getTop() {
        return 0;
    }

    @Override
    public float getWidth() {
        return (float) UResolution.getScaledWidth();
    }

    @Override
    public float getHeight() {
        return (float) UResolution.getScaledHeight();
    }

    @Override
    public float getRight() {
        return getWidth();
    }

    @Override
    public float getBottom() {
        return getHeight();
    }

    @Override
    public void draw(UMatrixStack matrixStack) {
        ElementaVersion prevVersion = ElementaVersion.getActive();
        ElementaVersion.setActive(version);
        try {
            super.draw(matrixStack);
        } finally {
            ElementaVersion.setActive(prevVersion);
        }
    }

    public void mouseClick(double mouseX, double mouseY, int button) {
        // Handle mouse click
    }

    public void mouseScroll(double delta) {
        // Handle mouse scroll
    }

    public void keyType(char typedChar, int keyCode) {
        // Handle key type
    }

    public static Window of(UIComponent component) {
        Window window = ofOrNull(component);
        if (window == null) {
            throw new IllegalStateException("Component is not attached to a Window");
        }
        return window;
    }

    public static Window ofOrNull(UIComponent component) {
        if (component == null) return null;
        if (component instanceof Window) return (Window) component;
        if (component.cachedWindow != null) return component.cachedWindow;
        if (!component.hasParent()) return null;
        return ofOrNull(component.getParent());
    }
}
