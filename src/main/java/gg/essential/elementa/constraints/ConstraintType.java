package gg.essential.elementa.constraints;

public enum ConstraintType {
    X("X"),
    Y("Y"),
    WIDTH("Width"),
    HEIGHT("Height"),
    RADIUS("Radius"),
    COLOR("Color"),
    FONT_PROVIDER("Font Provider"),
    TEXT_SCALE("TextScale");

    private final String prettyName;

    ConstraintType(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPrettyName() {
        return prettyName;
    }
}
