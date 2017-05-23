package com.j1.healthcare.patient.view.common.slidingstackview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.j1.healthcare.patient.R;

/**
 * @author rape flower
 */
public class StackViewPager extends ViewPager {

    private int mMaxWidth = -1;
    private int mMaxHeight = -1;
    private int mMatchWidthChildResId;
    private boolean mNeedsMeasurePage;
    private final Point size;
    private final Point maxSize;

    private static void constrainTo(Point size, Point maxSize) {
        if (maxSize.x >= 0) {
            if (size.x > maxSize.x) {
                size.x = maxSize.x;
            }
        }
        if (maxSize.y >= 0) {
            if (size.y > maxSize.y) {
                size.y = maxSize.y;
            }
        }
    }

    public StackViewPager(Context context) {
        super(context);
        size = new Point();
        maxSize = new Point();
    }

    public StackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        size = new Point();
        maxSize = new Point();
    }

    private void init(Context context, AttributeSet attrs) {
        setClipChildren(false);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StackViewPager);
        setMaxWidth(ta.getDimensionPixelSize(R.styleable.StackViewPager_android_maxWidth, -1));
        setMaxHeight(ta.getDimensionPixelSize(R.styleable.StackViewPager_android_maxHeight, -1));
        setMatchChildWidth(ta.getResourceId(R.styleable.StackViewPager_matchChildWidth, 0));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        size.set(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
        if (mMaxWidth >= 0 || mMaxHeight >= 0) {
            maxSize.set(mMaxWidth, mMaxHeight);
            constrainTo(size, maxSize);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    size.x,
                    MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    size.y,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMeasurePage(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onMeasurePage(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mNeedsMeasurePage) {
            return;
        }
        if (mMatchWidthChildResId == 0) {
            mNeedsMeasurePage = false;
        } else if (getChildCount() > 0) {
            View child = getChildAt(0);
            child.measure(widthMeasureSpec, heightMeasureSpec);
            int pageWidth = child.getMeasuredWidth();
            View match = child.findViewById(mMatchWidthChildResId);
            if (match == null) {
                throw new NullPointerException("NullPointerException");
            }
            int childWidth = match.getMeasuredWidth();
            Log.w("onMeasurePage", "-----------> pageWidth = " + pageWidth);
            Log.w("onMeasurePage", "-----------> childWidth = " + childWidth);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int defaultDensity = 3;
            float scale = (float) ((defaultDensity / displayMetrics.density) * 0.98);
            Log.w("onMeasurePage", "-----------> scale = " + scale);
            if (childWidth > 0) {
                mNeedsMeasurePage = false;
                int difference = (int) ((pageWidth - childWidth) * 0.9);
                setPageMargin(-difference);
                int offscreen = (int) Math.ceil((float) pageWidth / (float) childWidth) + 1;
                setOffscreenPageLimit(offscreen);
                requestLayout();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mNeedsMeasurePage = true;
    }

    public void setMatchChildWidth(int matchChildWidthResId) {
        if (mMatchWidthChildResId != matchChildWidthResId) {
            mMatchWidthChildResId = matchChildWidthResId;
            mNeedsMeasurePage = true;
        }
    }

    public void setMaxWidth(int width) {
        mMaxWidth = width;
    }

    public void setMaxHeight(int height) {
        mMaxHeight = height;
    }

}