package gg.essential.elementa.components;

/**
 * Functional interface for update functions called each frame.
 */
@FunctionalInterface
public interface UpdateFunc {
    void invoke(float dt, int dtMs);
}
