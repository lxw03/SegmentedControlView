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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements SegmentedControlView.OnSegmentSelectListener {

    private SegmentedControlView mSegmentedControlView1, mSegmentedControlView2, mSegmentedControlView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mSegmentedControlView1 = (SegmentedControlView) findViewById(R.id.segmented_control1);
        mSegmentedControlView2 = (SegmentedControlView) findViewById(R.id.segmented_control2);
        mSegmentedControlView3 = (SegmentedControlView) findViewById(R.id.segmented_control3);

        List<String> list1 = new ArrayList<String>(3);
        List<String> list2 = new ArrayList<String>(4);
        List<String> list3 = new ArrayList<String>(5);

        list1.add("left");
        list1.add("middle");
        list1.add("right");

        list2.add("spades");
        list2.add("clubs");
        list2.add("diamonds");
        list2.add("hearts");

        list3.add("1");
        list3.add("2");
        list3.add("3");
        list3.add("4");
        list3.add("5");

        mSegmentedControlView1.setMenuList(list1);
        mSegmentedControlView2.setMenuList(list2);
        mSegmentedControlView3.setMenuList(list3);

        mSegmentedControlView1.setOnSegmentSelectListener(this);
        mSegmentedControlView2.setOnSegmentSelectListener(this);
        mSegmentedControlView3.setOnSegmentSelectListener(this);
    }

    // Sample action code
    @Override
    public void onSegmentSelect(View segmentedControlView, int position) {
        if (segmentedControlView.equals(mSegmentedControlView1)) {
            switch (position) {
                // Please implement custom actions here
            }

        } else if (segmentedControlView.equals(mSegmentedControlView2)) {

        } else if (segmentedControlView.equals(mSegmentedControlView3)) {

        }
    }
}
