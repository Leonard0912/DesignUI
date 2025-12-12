package gg.essential.elementa;

import gg.essential.elementa.constraints.*;
import gg.essential.elementa.font.FontProvider;

import java.awt.Color;

/**
 * Container class for all constraints applied to a UIComponent.
 */
public class UIConstraints {
    protected final UIComponent component;
    protected XConstraint x;
    protected YConstraint y;
    protected WidthConstraint width;
    protected HeightConstraint height;
    protected RadiusConstraint radius;
    protected HeightConstraint textScale;
    protected ColorConstraint color;
    protected FontProvider fontProvider;

    public UIConstraints(UIComponent component) {
        this.component = component;
        this.x = new PixelConstraint(0f);
        this.y = new PixelConstraint(0f);
        this.width = new PixelConstraint(0f);
        this.height = new PixelConstraint(0f);
        this.radius = new PixelConstraint(0f);
        this.textScale = new PixelConstraint(1f);
        this.color = new ConstantColorConstraint(Color.WHITE);
    }

    public UIComponent getComponent() {
        return component;
    }

    public XConstraint getX() {
        return x;
    }

    public void setX(XConstraint x) {
        this.x = x;
    }

    public YConstraint getY() {
        return y;
    }

    public void setY(YConstraint y) {
        this.y = y;
    }

    public WidthConstraint getWidth() {
        return width;
    }

    public void setWidth(WidthConstraint width) {
        this.width = width;
    }

    public HeightConstraint getHeight() {
        return height;
    }

    public void setHeight(HeightConstraint height) {
        this.height = height;
    }

    public RadiusConstraint getRadius() {
        return radius;
    }

    public void setRadius(RadiusConstraint radius) {
        this.radius = radius;
    }

    public HeightConstraint getTextScale() {
        return textScale;
    }

    public void setTextScale(HeightConstraint textScale) {
        this.textScale = textScale;
    }

    public ColorConstraint getColor() {
        return color;
    }

    public void setColor(ColorConstraint color) {
        this.color = color;
    }

    public FontProvider getFontProvider() {
        return fontProvider;
    }

    public void setFontProvider(FontProvider fontProvider) {
        this.fontProvider = fontProvider;
    }

    /**
     * @deprecated See ElementaVersion.V8
     */
    @Deprecated
    public void animationFrame() {
        x.animationFrame();
        y.animationFrame();
        width.animationFrame();
        height.animationFrame();
        radius.animationFrame();
        textScale.animationFrame();
        color.animationFrame();
    }

    /**
     * Builder interface for constraint configuration.
     */
    @FunctionalInterface
    public interface Builder {
        void apply(UIConstraints constraints);
    }
}
