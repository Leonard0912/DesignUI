package gg.essential.elementa.state;

import java.util.function.Function;

/**
 * A state which maps another state using the provided
 * mapping function.
 *
 * This should primarily be used via the State.map method.
 */
public class MappedState<T, U> extends BasicState<U> {
    private final Function<T, U> mapper;
    private Runnable removeListener;

    public MappedState(State<T> initialState, Function<T, U> mapper) {
        super(mapper.apply(initialState.get()));
        this.mapper = mapper;
        this.removeListener = initialState.onSetValue(value -> set(mapper.apply(value)));
    }

    /**
     * Changes the state that this state maps from.
     *
     * This method calls State.set, and will trigger
     * all of its listeners.
     */
    public void rebind(State<T> newState) {
        removeListener.run();
        removeListener = newState.onSetValue(value -> set(mapper.apply(value)));
        set(mapper.apply(newState.get()));
    }
}
