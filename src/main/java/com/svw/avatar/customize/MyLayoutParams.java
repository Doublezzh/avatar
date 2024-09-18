package com.svw.avatar.customize;

import android.view.ViewGroup;

public class MyLayoutParams extends ViewGroup.LayoutParams implements Cloneable {
    public int x;
    public int y;
    public int verticalPos, horizontalPos;
    public int index;
    public boolean isLeft, isDrag;
    public float scale;

    public MyLayoutParams(int width, int height) {
        super(width, height);
    }

    @Override
    public MyLayoutParams clone() {
        MyLayoutParams layoutParams = null;
        try {
            layoutParams = (MyLayoutParams) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (layoutParams == null) {
            layoutParams = new MyLayoutParams(width, height);
        } else {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        layoutParams.x = x;
        layoutParams.y = y;
        layoutParams.verticalPos = verticalPos;
        layoutParams.horizontalPos = horizontalPos;
        layoutParams.index = index;
        layoutParams.isLeft = isLeft;
        layoutParams.isDrag = isDrag;
        layoutParams.scale = scale;
        return layoutParams;
    }
}