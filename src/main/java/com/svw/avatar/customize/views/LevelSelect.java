package com.svw.avatar.customize.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.svw.avatar.R;

/**
 * 关卡列表
 */
public class LevelSelect extends ViewGroup {

    private LevelView mItems[]; // 关卡按钮实例
    private int mItemSize; // 按钮的尺寸
    private int mMaxCount; // 最大关卡数
    private int mHorizontalSpacing; // 左右间距
    private int mVerticalSpacing; // 上下间距
    private int mTopSpacing; // 第一排与顶部的间距
    private OnLevelSelectedListener mOnLevelSelectedListener; // 关卡点击回调

    // 假设这是你的关卡图片资源数组
    private int[] levelImages = {
            R.mipmap.level_1,
            R.mipmap.level_2,
            R.mipmap.level_3,
            R.mipmap.level_4,
            R.mipmap.level_5,
            R.mipmap.level_6,
            R.mipmap.level_7,
            R.mipmap.level_8,
            R.mipmap.level_9,
            R.mipmap.level_10,
            R.mipmap.level_11,
            R.mipmap.level_12,
            R.mipmap.level_13,
            R.mipmap.level_14,
            R.mipmap.level_15,
    };

    public LevelSelect(Context context) {
        this(context, null);
    }

    public LevelSelect(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelSelect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemSize = (int) getResources().getDimension(R.dimen.xhpx_140);
        mHorizontalSpacing = 100; // 假设你定义了一个默认的左右间距
        mVerticalSpacing = 30; // 假设你定义了一个默认的上下间距
        mTopSpacing = 60; // 第一排与顶部的间距
    }

    public void setValidItemCount(int count) {
        if (count > mMaxCount) {
            count = mMaxCount;
        }
        // 可以玩的关卡就显示数字和接受点击事件
        if (mItems != null) {
            for (int i = 0; i < count; i++) {
                LevelView item = mItems[i];
                item.setLevelImage(levelImages[i]);
                item.setLevelBackground(R.mipmap.avatar_select);
                item.setEnabled(true);
            }
        }
    }

    public void setMaxItemCount(int count) {
        // already initialed
        if (getChildCount() > 0) {
            removeAllViews();
        }
        mMaxCount = count;
        mItems = new LevelView[count];
        OnClickListener onClickListener = v -> {
            if (mOnLevelSelectedListener != null) {
                mOnLevelSelectedListener.onSelected((int) v.getTag());
            }
        };
        // 初始化关卡按钮
        for (int i = 0; i < count; i++) {
            LevelView temp = new LevelView(getContext());
            temp.setTag(i + 1);

            if (i <= levelImages.length - 1) {
                temp.setLevelImage(levelImages[i]);
            }
            temp.setLevelBackground(R.mipmap.avatar_slelct_disable);
            temp.setGravity(Gravity.CENTER);
            temp.setOnClickListener(onClickListener);
            temp.setEnabled(false);
            mItems[i] = temp;
            addView(temp);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mItemSize * 5 + mHorizontalSpacing * 4;
        int height = 0;
        if (mItems != null) {
            int heightCount = mItems.length / 5;
            if (mItems.length % 5 > 0) {
                heightCount++;
            }
            height = mItemSize * heightCount + mVerticalSpacing * (heightCount - 1) + mTopSpacing;
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 排序关卡按钮，每行5个
        if (mItems != null) {
            int currentWidth;
            int currentHeight = mTopSpacing; // 从顶部间距开始布局
            for (int i = 0; i < mItems.length; i++) {
                if (mItems[i] != null) {
                    currentWidth = (i % 5) * (mItemSize + mHorizontalSpacing);
                    if (i % 5 == 0 && i != 0) {
                        currentHeight += mItemSize + mVerticalSpacing;
                    }
                    int left = currentWidth;
                    int top = currentHeight;
                    int right = currentWidth + mItemSize;
                    int bottom = currentHeight + mItemSize;
                    mItems[i].layout(left, top, right, bottom);
                }
            }
        }
    }

    public void release() {
        if (mItems != null) {
            for (LevelView tmp : mItems) {
                if (tmp != null) {
                    tmp.setLevelImage(null);
                    tmp.setOnClickListener(null);
                }
            }
            mItems = null;
        }
        mOnLevelSelectedListener = null;
    }

    public void setOnLevelSelectedListener(OnLevelSelectedListener levelSelectedListener) {
        mOnLevelSelectedListener = levelSelectedListener;
    }

    public interface OnLevelSelectedListener {
        void onSelected(int level);
    }
}
