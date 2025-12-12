package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.constraints.resolution.ConstraintVisitor;

/**
 * Base class for animation components.
 */
public abstract class AnimationComponent<T> implements SuperConstraint<T> {
    protected final AnimationStrategy strategy;
    protected final int totalFrames;
    protected final int delayFrames;
    protected int elapsedFrames = 0;
    protected boolean animationPaused = false;
    private long lastUpdateTime = -1;

    public AnimationComponent(AnimationStrategy strategy, int totalFrames, int delayFrames) {
        this.strategy = strategy;
        this.totalFrames = totalFrames;
        this.delayFrames = delayFrames;
    }

    protected void update(UIComponent component) {
        Window window = Window.ofOrNull(component);
        if (window == null) return;
        if (window.getVersion().compareTo(ElementaVersion.v8) < 0) return; // update handled by animationFrame

        long now = window.getAnimationTimeMs();
        if (lastUpdateTime == -1L) lastUpdateTime = now;
        int dtMs = (int) (now - lastUpdateTime);
        lastUpdateTime = now;

        if (!animationPaused) {
            elapsedFrames = Math.min(elapsedFrames + dtMs, totalFrames + delayFrames);
        }
    }

    /**
     * @deprecated See ElementaVersion.V8
     */
    @Deprecated
    @Override
    public void animationFrame() {
        SuperConstraint.super.animationFrame();

        if (isComplete() || animationPaused) return;

        elapsedFrames++;
    }

    public void stop() {
        elapsedFrames = totalFrames + delayFrames;
    }

    public void pause() {
        animationPaused = true;
    }

    public void resume() {
        animationPaused = false;
    }

    public boolean isComplete() {
        return elapsedFrames - delayFrames >= totalFrames;
    }

    public float getPercentComplete() {
        return strategy.getValue(Math.max(elapsedFrames - delayFrames, 0) / (float) totalFrames);
    }

    public AnimationStrategy getStrategy() {
        return strategy;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public int getDelayFrames() {
        return delayFrames;
    }

    public int getElapsedFrames() {
        return elapsedFrames;
    }
}
