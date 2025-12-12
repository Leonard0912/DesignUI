package gg.essential.elementa.state.v2;

/**
 * Interface for holding references to prevent garbage collection.
 */
public interface ReferenceHolder {
    void holdOnto(Object reference);
}
