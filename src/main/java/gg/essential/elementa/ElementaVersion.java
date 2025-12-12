package gg.essential.elementa;

import gg.essential.elementa.components.UpdateFunc;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.SuperConstraint;
import gg.essential.elementa.constraints.animation.AnimationComponent;
import gg.essential.elementa.effects.Effect;

import java.util.function.Supplier;

/**
 * Sometimes it is necessary or desirable to introduce breaking behavioral changes to Elementa. In order to maintain
 * full backwards compatibility in these cases, library consumers must explicitly opt-in to such changes for their
 * {@link gg.essential.elementa.components.Window}s. This allows Elementa to evolve without breaking mods which rely on old,
 * suboptimal behavior.
 *
 * This opt-in, if supplied to {@link gg.essential.elementa.components.Window}'s constructor, will only be active during the
 * Window's draw call (or any other affected methods, if extended by a future version).
 * To opt-in to the new behavior outside of these methods, you may use {@link #enableFor}.
 */
public enum ElementaVersion {

    /**
     * The initial version of Elementa. This is the default behavior if no opt-in is active.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V0,

    /**
     * {@link gg.essential.elementa.components.UIBlock#drawBlock} and the similar methods all starting with `drawBlock` will
     * now always render the block with depth testing enabled and set to always pass.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V1,

    /**
     * This Elementa version improves the behavior of mouse input in three ways.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V2,

    /**
     * When there are multiple {@link gg.essential.elementa.effects.Effect} applied to a single component,
     * their afterDraw are now called in reverse order.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V3,

    /**
     * Mark components as initialized and call afterInitialization during beforeDraw instead of draw.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V4,

    /**
     * Change the behavior of scroll components to no longer require holding down shift when horizontal is the only possible scrolling direction.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V5,

    /**
     * ScrollComponent now has a minimum size for scrollbar grips.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V6,

    /**
     * Window now disables input events if an error has occurred during drawing.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V7,

    /**
     * The animationFrame methods are now deprecated and will no longer be called at all
     * for constraints or if your override is marked as Deprecated.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V8,

    /**
     * All Minecraft versions now use URenderPipeline instead of modifying global GL state.
     * @deprecated This version of Elementa has been deprecated.
     */
    @Deprecated
    V9,

    /**
     * All components now use BlendState.ALPHA instead of BlendState.NORMAL and variants.
     */
    V10;

    private static final String DEPRECATION_MESSAGE = "This version of Elementa has been deprecated.\n" +
            "It may still be used but its behavior has been determined to be unexpected, suboptimal or broken in some way.\n" +
            "We therefore recommend you opt-in to a newer version.\n" +
            "Be sure to read through all the changes between your current version and your new version to be able to act in case you are affected by them.";

    // Internal version references for comparison
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v0 = V0;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v1 = V1;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v2 = V2;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v3 = V3;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v4 = V4;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v5 = V5;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v6 = V6;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v7 = V7;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v8 = V8;
    @SuppressWarnings("deprecation")
    public static final ElementaVersion v9 = V9;
    public static final ElementaVersion v10 = V10;

    private static ElementaVersion active = v0;

    public static boolean atLeastV9Active() {
        return active.compareTo(v9) >= 0;
    }

    public static boolean atLeastV10Active() {
        return active.compareTo(v10) >= 0;
    }

    public static ElementaVersion getActive() {
        return active;
    }

    public static void setActive(ElementaVersion version) {
        active = version;
    }

    /**
     * Run the given block of code with the provided opt-in.
     * This method may be used to downgrade the version if required.
     *
     * The current opt-in is restored after this method returns.
     * This method is not thread-safe and may only be used from the main thread. This may change in the future.
     */
    public <T> T enableFor(Supplier<T> block) {
        ElementaVersion prevVersion = active;
        active = this;
        try {
            return block.get();
        } finally {
            active = prevVersion;
        }
    }

    /**
     * Run the given block of code with the provided opt-in.
     * This method may be used to downgrade the version if required.
     *
     * The current opt-in is restored after this method returns.
     * This method is not thread-safe and may only be used from the main thread. This may change in the future.
     */
    public void enableFor(Runnable block) {
        ElementaVersion prevVersion = active;
        active = this;
        try {
            block.run();
        } finally {
            active = prevVersion;
        }
    }
}
