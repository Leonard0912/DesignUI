package gg.essential.elementa.constraints.animation;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.components.UpdateFunc;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.*;

/**
 * Constraints class used for animations.
 */
public class AnimatingConstraints extends UIConstraints {
    private final UIConstraints oldConstraints;
    private Runnable completeAction = () -> {};
    private int extraDelayFrames = 0;
    UpdateFunc updateFunc = null;

    public AnimatingConstraints(UIComponent component, UIConstraints oldConstraints) {
        super(component);
        this.oldConstraints = oldConstraints;
        
        this.x = oldConstraints.getX();
        this.y = oldConstraints.getY();
        this.width = oldConstraints.getWidth();
        this.height = oldConstraints.getHeight();
        this.radius = oldConstraints.getRadius();
        this.textScale = oldConstraints.getTextScale();
        this.color = oldConstraints.getColor();
        this.setFontProvider(oldConstraints.getFontProvider());
    }

    public AnimatingConstraints begin() {
        component.animateTo(this);
        return this;
    }

    public AnimatingConstraints setXAnimation(AnimationStrategy strategy, float time, XConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.x = new XAnimationComponent(strategy, totalFrames, oldConstraints.getX(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setXAnimation(AnimationStrategy strategy, float time, XConstraint newConstraint) {
        return setXAnimation(strategy, time, newConstraint, 0f);
    }

    public AnimatingConstraints setYAnimation(AnimationStrategy strategy, float time, YConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.y = new YAnimationComponent(strategy, totalFrames, oldConstraints.getY(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setYAnimation(AnimationStrategy strategy, float time, YConstraint newConstraint) {
        return setYAnimation(strategy, time, newConstraint, 0f);
    }

    public AnimatingConstraints setWidthAnimation(AnimationStrategy strategy, float time, WidthConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.width = new WidthAnimationComponent(strategy, totalFrames, oldConstraints.getWidth(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setWidthAnimation(AnimationStrategy strategy, float time, WidthConstraint newConstraint) {
        return setWidthAnimation(strategy, time, newConstraint, 0f);
    }

    public AnimatingConstraints setHeightAnimation(AnimationStrategy strategy, float time, HeightConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.height = new HeightAnimationComponent(strategy, totalFrames, oldConstraints.getHeight(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setHeightAnimation(AnimationStrategy strategy, float time, HeightConstraint newConstraint) {
        return setHeightAnimation(strategy, time, newConstraint, 0f);
    }

    public AnimatingConstraints setRadiusAnimation(AnimationStrategy strategy, float time, RadiusConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.radius = new RadiusAnimationComponent(strategy, totalFrames, oldConstraints.getRadius(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setRadiusAnimation(AnimationStrategy strategy, float time, RadiusConstraint newConstraint) {
        return setRadiusAnimation(strategy, time, newConstraint, 0f);
    }

    public AnimatingConstraints setColorAnimation(AnimationStrategy strategy, float time, ColorConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.color = new ColorAnimationComponent(strategy, totalFrames, oldConstraints.getColor(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setColorAnimation(AnimationStrategy strategy, float time, ColorConstraint newConstraint) {
        return setColorAnimation(strategy, time, newConstraint, 0f);
    }

    public AnimatingConstraints setTextScaleAnimation(AnimationStrategy strategy, float time, HeightConstraint newConstraint, float delay) {
        int totalFrames = (int) (time * Window.of(component).getAnimationFPSOr1000());
        int totalDelay = (int) (delay * Window.of(component).getAnimationFPSOr1000());
        
        this.textScale = new HeightAnimationComponent(strategy, totalFrames, oldConstraints.getTextScale(), newConstraint, totalDelay);
        return this;
    }

    public AnimatingConstraints setTextScaleAnimation(AnimationStrategy strategy, float time, HeightConstraint newConstraint) {
        return setTextScaleAnimation(strategy, time, newConstraint, 0f);
    }

    public void setExtraDelay(float delay) {
        extraDelayFrames = (int) (delay * Window.of(component).getAnimationFPSOr1000());
    }

    public AnimatingConstraints onComplete(Runnable method) {
        completeAction = method;
        return this;
    }

    public AnimatingConstraints onCompleteRunnable(Runnable method) {
        return onComplete(method);
    }

    /**
     * @deprecated See ElementaVersion.V8
     */
    @Deprecated
    @Override
    public void animationFrame() {
        super.animationFrame();
        updateCompletion(1);
    }

    public void updateCompletion(int dt) {
        boolean anyLeftAnimating = false;

        if (x instanceof XAnimationComponent) {
            XAnimationComponent xAnim = (XAnimationComponent) x;
            if (xAnim.isComplete()) this.x = xAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (y instanceof YAnimationComponent) {
            YAnimationComponent yAnim = (YAnimationComponent) y;
            if (yAnim.isComplete()) this.y = yAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (width instanceof WidthAnimationComponent) {
            WidthAnimationComponent widthAnim = (WidthAnimationComponent) width;
            if (widthAnim.isComplete()) this.width = widthAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (height instanceof HeightAnimationComponent) {
            HeightAnimationComponent heightAnim = (HeightAnimationComponent) height;
            if (heightAnim.isComplete()) this.height = heightAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (radius instanceof RadiusAnimationComponent) {
            RadiusAnimationComponent radiusAnim = (RadiusAnimationComponent) radius;
            if (radiusAnim.isComplete()) this.radius = radiusAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (textScale instanceof HeightAnimationComponent) {
            HeightAnimationComponent textScaleAnim = (HeightAnimationComponent) textScale;
            if (textScaleAnim.isComplete()) this.textScale = textScaleAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (color instanceof ColorAnimationComponent) {
            ColorAnimationComponent colorAnim = (ColorAnimationComponent) color;
            if (colorAnim.isComplete()) this.color = colorAnim.getNewConstraint();
            else anyLeftAnimating = true;
        }

        if (extraDelayFrames > 0) {
            anyLeftAnimating = true;
            extraDelayFrames -= dt;
        }

        if (!anyLeftAnimating) {
            UIConstraints newConstraints = new UIConstraints(component);
            newConstraints.setX(this.x);
            newConstraints.setY(this.y);
            newConstraints.setWidth(this.width);
            newConstraints.setHeight(this.height);
            newConstraints.setRadius(this.radius);
            newConstraints.setTextScale(this.textScale);
            newConstraints.setColor(this.color);
            newConstraints.setFontProvider(this.getFontProvider());
            component.setConstraints(newConstraints);
            completeAction.run();
        }
    }
}
