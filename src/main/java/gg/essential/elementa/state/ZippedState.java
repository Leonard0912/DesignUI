package gg.essential.elementa.state;

/**
 * A state which combines two other states into a Pair.
 *
 * This should primarily be used via the State.zip method.
 */
public class ZippedState<T, U> extends BasicState<Pair<T, U>> {
    private Runnable removeFirstListener;
    private Runnable removeSecondListener;

    public ZippedState(State<T> firstState, State<U> secondState) {
        super(new Pair<>(firstState.get(), secondState.get()));
        
        this.removeFirstListener = firstState.onSetValue(value -> 
            set(new Pair<>(value, get().getSecond()))
        );
        this.removeSecondListener = secondState.onSetValue(value -> 
            set(new Pair<>(get().getFirst(), value))
        );
    }

    /**
     * Changes the first state that this state uses.
     *
     * This method calls State.set, and will trigger
     * all of its listeners.
     */
    public void rebindFirst(State<T> newState) {
        removeFirstListener.run();
        removeFirstListener = newState.onSetValue(value -> 
            set(new Pair<>(value, get().getSecond()))
        );
        set(new Pair<>(newState.get(), get().getSecond()));
    }

    /**
     * Changes the second state that this state uses.
     *
     * This method calls State.set, and will trigger
     * all of its listeners.
     */
    public void rebindSecond(State<U> newState) {
        removeSecondListener.run();
        removeSecondListener = newState.onSetValue(value -> 
            set(new Pair<>(get().getFirst(), value))
        );
        set(new Pair<>(get().getFirst(), newState.get()));
    }
}
