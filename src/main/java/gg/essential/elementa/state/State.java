package gg.essential.elementa.state;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The base for all Elementa State objects.
 *
 * State objects are essentially just a wrapper around a value.
 * However, the ability to be deeply integrated into an
 * Elementa component allows some nice functionality.
 *
 * The primary advantage of using state is that a single state
 * object can be shared between multiple components or
 * constraints. This allows one value update to be seen
 * by multiple components or constraints.
 */
public abstract class State<T> {
    protected final List<Consumer<T>> listeners = new ArrayList<>();

    /**
     * Get the value of this State object
     */
    public abstract T get();

    /**
     * Set the value of this State object
     *
     * This method also notifies all of the listeners of this
     * State object.
     */
    public void set(T value) {
        for (Consumer<T> listener : listeners) {
            listener.accept(value);
        }
    }

    /**
     * Like set, but accepts a function which takes the
     * current value of this State object
     */
    public void set(Function<T, T> mapper) {
        set(mapper.apply(get()));
    }

    /**
     * Register a listener which will be called whenever the
     * value of this State object changes
     *
     * @return A callback which, when invoked, removes this listener
     */
    public Runnable onSetValue(Consumer<T> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public T getOrDefault(T defaultValue) {
        T value = get();
        return value != null ? value : defaultValue;
    }

    public T getOrElse(java.util.function.Supplier<T> defaultProvider) {
        T value = get();
        return value != null ? value : defaultProvider.get();
    }

    /**
     * Maps this state into a new state
     *
     * @see MappedState
     */
    public <U> MappedState<T, U> map(Function<T, U> mapper) {
        return new MappedState<>(this, mapper);
    }

    /**
     * Zips this state with another state
     *
     * @see ZippedState
     */
    public <U> ZippedState<T, U> zip(State<U> otherState) {
        return new ZippedState<>(this, otherState);
    }
}
