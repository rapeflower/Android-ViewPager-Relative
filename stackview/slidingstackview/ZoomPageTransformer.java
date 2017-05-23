package com.j1.healthcare.patient.view.common.slidingstackview;

import android.os.Build;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * @author rape flower
 */
public class ZoomPageTransformer extends BasePageTransformer {
    private float mMinScale = 0.7f;
    private float mMinAlpha = 0.5f;

    public ZoomPageTransformer() {
    }

    public ZoomPageTransformer(float minAlpha, float minScale) {
        setMinAlpha(minAlpha);
        setMinScale(minScale);
    }

    @Override
    public void scrollInvisible(View view, float position) {
        ViewHelper.setAlpha(view, 0);
    }

    @Override
    public void scrollLeft(View view, float position) {
        float scale = Math.max(mMinScale, 1 + position);
        float vertMargin = view.getHeight() * (1 - scale) / 2;
        float horzMargin = view.getWidth() * (1 - scale) / 2;
        ViewHelper.setTranslationX(view, horzMargin - vertMargin / 2);
        ViewHelper.setScaleX(view, scale);
        ViewHelper.setScaleY(view, scale);
        ViewHelper.setAlpha(view, mMinAlpha + (scale - mMinScale) / (1 - mMinScale) * (1 - mMinAlpha));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(position > -0.25 && position < 0.25 ? 1 : 0);
        }
    }

    @Override
    public void scrollRight(View view, float position) {
        float scale = Math.max(mMinScale, 1 - position);
        float vertMargin = view.getHeight() * (1 - scale) / 2;
        float horzMargin = view.getWidth() * (1 - scale) / 2;
        ViewHelper.setTranslationX(view, -horzMargin + vertMargin / 2);
        ViewHelper.setScaleX(view, scale);
        ViewHelper.setScaleY(view, scale);
        ViewHelper.setAlpha(view, mMinAlpha + (scale - mMinScale) / (1 - mMinScale) * (1 - mMinAlpha));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(position > -0.25 && position < 0.25 ? 1 : 0);
        }
    }

    public void setMinAlpha(float minAlpha) {
        if (minAlpha >= 0.0f && minAlpha <= 1.0f) {
            mMinAlpha = minAlpha;
        }
    }

    public void setMinScale(float minScale) {
        if (minScale >= 0.0f && minScale <= 1.0f) {
            mMinScale = minScale;
        }
    }

}