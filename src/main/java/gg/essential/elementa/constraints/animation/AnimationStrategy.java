package gg.essential.elementa.constraints.animation;

/**
 * Provides a mapping from 0f - 1f to 0f - 1f (however, this output value can technically go past those bounds).
 */
public interface AnimationStrategy {
    float getValue(float percentComplete);
}
