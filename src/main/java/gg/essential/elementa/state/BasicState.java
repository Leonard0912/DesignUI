package gg.essential.elementa.state;

/**
 * A simple implementation of State, containing only a
 * backing field
 */
public class BasicState<T> extends State<T> {
    protected T valueBacker;

    public BasicState(T initialValue) {
        this.valueBacker = initialValue;
    }

    @Override
    public T get() {
        return valueBacker;
    }

    @Override
    public void set(T value) {
        if (value == valueBacker || (value != null && value.equals(valueBacker))) {
            return;
        }

        valueBacker = value;
        super.set(value);
    }
}
