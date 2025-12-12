package gg.essential.elementa;

import gg.essential.elementa.components.Window;

/**
 * A screen class that includes an Elementa Window.
 */
public abstract class WindowScreen {
    private final Window window;

    public WindowScreen(ElementaVersion version) {
        this.window = new Window(version);
    }

    @SuppressWarnings("deprecation")
    public WindowScreen() {
        this(ElementaVersion.V2);
    }

    public Window getWindow() {
        return window;
    }
}
