package com.example.weatherapp.weather;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * 默认动态天气
 * Created by liyu on 2017/11/13.
 */

public class DefaultType extends BaseWeatherType {

    public DefaultType(Context context) {
        super(context);
        setColor(0xFF51C0F8);
    }

    @Override
    public void onDrawElements(Canvas canvas) {
        clearCanvas(canvas);
        canvas.drawColor(getDynamicColor());
    }

    @Override
    public void generateElements() {

    }

    @Override
    public void endAnimation(DynamicWeatherView dynamicWeatherView, Animator.AnimatorListener listener) {
        super.endAnimation(dynamicWeatherView, listener);
        listener.onAnimationEnd(null);
    }
}
