package com.dylf.hometown;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class IconRibbon extends RelativeLayout {

  HorizontalScrollView scrollView;
  LinearLayout hLayout;
  ArrayList<ImageButton> buttons;
  
  public IconRibbon(Context context, AttributeSet attrs) {
    super(context, attrs);
    scrollView = new HorizontalScrollView(context, attrs);
    scrollView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    hLayout = new LinearLayout(context, attrs);
    hLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    hLayout.setOrientation(LinearLayout.HORIZONTAL);
    scrollView.addView(hLayout);
    addView(scrollView);
    buttons = new ArrayList<ImageButton>();
  }

  @SuppressWarnings("deprecation")
  @SuppressLint("NewApi")
  public void addItem(RibbonItem item) {
    ImageButton button = new ImageButton(getContext());
    button.setId(item.getBID());
    button.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
    StateListDrawable states = new StateListDrawable();
    states.addState(new int[] {android.R.attr.state_pressed}, getResources().getDrawable(item.getDID()));
    states.addState(new int[] {android.R.attr.state_focused}, getResources().getDrawable(item.getDID()));
    states.addState(new int[] { }, getResources().getDrawable(item.getUID()));
    if (Build.VERSION.SDK_INT >= 16) button.setBackground(states);
    else button.setBackgroundDrawable(states);
    if (item.getListener() != null) button.setOnClickListener(item.getListener());
    buttons.add(button);
    hLayout.addView(button);
  }
}
