SegmentedControlView
====================

<img src="https://raw.githubusercontent.com/emmasuzuki/SegmentedControlView/master/screenshot.png" width="200" />

SegmentedControlView is unswipable button style control view. Inspired by iOS's UISegmentedControl, buttons are evenly spaced in width.  Simple usage ! Just specify the highlight colors and unselected text colors are calculated automatically.

##Customizable attributes
```
highlightColor:  Text highlight color
borderColor:     Vertical border color 
indicatorColor:  Bar indicator color at bottom
```

##How to use
```
mSegmentedControlView = (SegmentedControlView) findViewById(R.id.segmented_control);

List<String> list = new ArrayList<String>(3);
list.add("left");
list.add("middle");
list.add("right");

mSegmentedControlView.setMenuList(list);  // This will generate 3 segmented views
```

##Action
```
mSegmentedControlView.setOnSegmentSelectListener(new OnSegmentSelectListener() {

    @Override
    public void onSegmentSelect(View segmentedControlView, int position) {
        // Implement custom actions here
    }
});
```

##API
`setSelectedIndex(int selectedIndex)`: Set custom selection on load or at any time
`setSelectedTextColor(int selectedTextColor)`: Set highlighted text color
`setBorderColor(int borderColor)`: Set vertical border color
`setIndicatorColor(int indicatorColor)`: Set bottom indicator color
`setBorderWidth(int borderWidth)`: Set vertical border width
`setOnSegmentSelectListener(OnSegmentSelectListener listener)`: Set select listener

##Questions?
Please contact me at emma11suzuki@gmail.com
