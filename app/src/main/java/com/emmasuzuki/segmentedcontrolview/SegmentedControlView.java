/*
 * Copyright (C) 2014 emmasuzuki <emma11suzuki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.emmasuzuki.segmentedcontrolview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * SegmentedControlView
 *
 * This class implements unswipable button style control views.
 * However, I adjust looks to Android action pager tab.
 * This view has built-in handling of unselected state by dimming text colors.
 */
public class SegmentedControlView extends LinearLayout implements View.OnClickListener {

    private List<TextView> mSegmentedViews;

    private LayerDrawable mSelectedBackgroundDrawable;

    private int mUnselectedBackgroundColor = 0xFFFFFFFF;
    private int mSelectedTextColor = 0xFFFFFFFF;
    private int mBorderColor = Color.TRANSPARENT;
    private int mUnselectedTextColor = Color.TRANSPARENT;
    private int mIndicatorColor = Color.TRANSPARENT;

    private int mBorderWidth = 2;
    private int mSelectedIndex;

    private OnSegmentSelectListener mListener;

    public SegmentedControlView(Context context) {
        super(context);
    }

    public SegmentedControlView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(attrs);
    }

    public SegmentedControlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize(attrs);
    }

    // ------------------------- PUBLIC API ------------------------- //

    public void setSelectedIndex(int selectedIndex) {
        mSelectedIndex = selectedIndex;

        for (int i = 0; i < mSegmentedViews.size(); i++) {
            if (i == mSelectedIndex) {
                mSegmentedViews.get(i).setBackground(mSelectedBackgroundDrawable);
                mSegmentedViews.get(i).setTextColor(mSelectedTextColor);
            } else {
                mSegmentedViews.get(i).setBackgroundColor(mUnselectedBackgroundColor);
                mSegmentedViews.get(i).setTextColor(mUnselectedTextColor);
            }
        }
    }

    public void setOnSegmentSelectListener(OnSegmentSelectListener listener) {
        mListener = listener;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
    }

    public void setIndicatorColor(int indicatorColor) {
        mIndicatorColor = indicatorColor;
    }

    public void setBorderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
    }

    // ------------------------- PUBLIC API ------------------------- //

    @Override
    public void onClick(View v) {
        TextView selectedView = mSegmentedViews.get(mSelectedIndex);

        selectedView.setBackgroundColor(mUnselectedBackgroundColor);
        selectedView.setTextColor(mUnselectedTextColor);

        v.setBackground(mSelectedBackgroundDrawable);
        ((TextView) v).setTextColor(mSelectedTextColor);

        mSelectedIndex = v.getId();

        if (mListener != null) {
            mListener.onSegmentSelect(this, mSelectedIndex);
        }
    }

    public void setMenuList(List<String> menuList) {
        if (menuList != null && getChildCount() != menuList.size()) {
            removeAllViews();

            if (mSegmentedViews == null) {
                mSegmentedViews = new ArrayList<TextView>(menuList.size());
            } else {
                mSegmentedViews.clear();
            }

            createControlViews(menuList);
        }
    }

    private void initialize(AttributeSet attrs) {
        setOrientation(HORIZONTAL);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedControlView);

        if (typedArray != null) {
            mSelectedTextColor = typedArray.getColor(R.styleable.SegmentedControlView_highlightColor, mSelectedTextColor);
            mBorderColor = typedArray.getColor(R.styleable.SegmentedControlView_borderColor, mBorderColor);
            mIndicatorColor = typedArray.getColor(R.styleable.SegmentedControlView_indicatorColor, mIndicatorColor);
            typedArray.recycle();
        }

        if (getBackground() != null) {
            mUnselectedBackgroundColor = ((ColorDrawable) getBackground()).getColor();
            mUnselectedTextColor = getColorLuminance(mUnselectedBackgroundColor, 0.2f);
        }

        createIndicatorBGDrawable();
    }

    private void createControlViews(List<String> menuLabelList) {
        for (int i = 0; i < menuLabelList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(mSelectedTextColor);
            textView.setTextSize(20.f);
            textView.setText(menuLabelList.get(i));
            textView.setOnClickListener(this);
            textView.setId(i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setLayoutParams(params);

            if (i == mSelectedIndex) {
                textView.setBackground(mSelectedBackgroundDrawable);
                textView.setTextColor(mSelectedTextColor);

            } else {
                textView.setBackgroundColor(mUnselectedBackgroundColor);
                textView.setTextColor(mUnselectedTextColor);
            }

            addView(textView);
            mSegmentedViews.add(textView);

            if (i != menuLabelList.size() - 1) {
                addVerticalBorderView();
            }
        }
    }

    private void addVerticalBorderView() {
        View borderView = new View(getContext());
        borderView.setBackgroundColor(mBorderColor);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mBorderWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        borderView.setLayoutParams(params);

        addView(borderView);
    }

    private void createIndicatorBGDrawable() {
        ShapeDrawable border = new ShapeDrawable();
        border.getPaint().setColor(mIndicatorColor);

        ShapeDrawable background = new ShapeDrawable();
        background.getPaint().setColor(mUnselectedBackgroundColor);

        mSelectedBackgroundDrawable = new LayerDrawable(new Drawable[]{border, background});

        mSelectedBackgroundDrawable.setLayerInset(0, 0, 0, 0, 0);
        mSelectedBackgroundDrawable.setLayerInset(1, 0, 0, 0, 10);
    }

    private int getColorLuminance(int hex, double lum) {
        String result = "";

        String strHex = Integer.toHexString(hex);

        // If there is alpha value, keep the alpha value as it is
        if (strHex.length() == 8) {
            result += strHex.substring(0, 2);
            strHex = strHex.substring(2);
        }

        // Loop over each rgb hex
        for (int i = 0; i < 3; i++) {
            String oneHex = strHex.substring(i * 2, i * 2 + 2);

            double decimal = Integer.parseInt(oneHex, 16);
            decimal = Math.max(0, decimal + (decimal * lum));
            decimal = Math.min(decimal, 255);
            decimal = Math.round(decimal);

            String resultHex = Integer.toHexString((int) decimal);
            result += (resultHex.length() == 1) ? "0" + resultHex : resultHex;
        }

        return (int) Long.parseLong(result, 16);
    }

    public interface OnSegmentSelectListener {
        public void onSegmentSelect(View segmentedControlView, int position);
    }
}
