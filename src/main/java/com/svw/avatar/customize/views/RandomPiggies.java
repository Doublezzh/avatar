package com.svw.avatar.customize.views;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import com.svw.avatar.R;
import com.svw.avatar.customize.MyLayoutParams;
import com.svw.avatar.models.WayData;
import com.svw.avatar.utils.ThreadPool;
import com.svw.avatar.utils.ValueAnimatorUtil;

import java.util.Random;
import java.util.concurrent.Future;


/**
 * 主页中生成随机小猪跑过的动画
 */
public class RandomPiggies extends ViewGroup {

    private Random mRandom;
    private Future mTask;//生成小猪动画的线程
    private volatile boolean isNeed;//需要生成
    private OnTouchListener mOnTouchListener;
    private int mRunWidth;//小猪实际宽度

    public RandomPiggies(Context context) {
        this(context, null);
    }

    public RandomPiggies(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandomPiggies(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        mRandom = new Random();



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MyLayoutParams lp = (MyLayoutParams) view.getLayoutParams();
            if (lp.isDrag) {
                view.layout(lp.x, lp.y, lp.x + lp.width, lp.y + lp.height);
            } else {
                view.layout(lp.isLeft ? -lp.width : getWidth(),
                        lp.y, lp.isLeft ? 0 : getWidth() + lp.width, lp.y + lp.height);
            }
        }
    }

    public void startShow() {
        //避免多个线程同时生成
        if (mTask != null) {
            stopShow();
        }
        mTask = ThreadPool.getInstance().execute(() -> {
            isNeed = true;
            while (isNeed) {
                //生成随机大小,方向,速度的小猪跑过的动画,并接受触摸事件
                post(() -> {
                    ImageView imageView = new ImageView(getContext());
                    MyLayoutParams layoutParams = new MyLayoutParams(0, 0);
                    boolean isLeft = mRandom.nextBoolean();
                    float scale = mRandom.nextFloat() + .1F;
                    if (scale > 1) {
                        scale = 1;
                    }
                    imageView.setImageResource(isLeft ? R.drawable.anim_run_right2 : R.drawable.anim_run_left2);
                    AnimationDrawable drawable = (AnimationDrawable) imageView.getDrawable();
                    drawable.start();
                    if (mRunWidth == 0) {
                        mRunWidth = drawable.getIntrinsicWidth();
                    }
                    // 修改图片大小的地方
                    layoutParams.width = (int) (drawable.getIntrinsicWidth() * scale);
                    layoutParams.height = (int) (drawable.getIntrinsicHeight() * scale);
                    if (getHeight() <= 0) {
                        stopShow();
                        return;
                    }
                    layoutParams.y = mRandom.nextInt(getHeight() - drawable.getIntrinsicHeight());
                    layoutParams.isLeft = isLeft;
                    layoutParams.scale = scale;
                    imageView.setLayoutParams(layoutParams);
                    addView(imageView);
                    imageView.setOnTouchListener(mOnTouchListener);

                    ViewPropertyAnimator animator = imageView.animate();
                    animator.translationX(isLeft ? getWidth() + layoutParams.width : -(getWidth() + layoutParams.width))
                            .setDuration(3000 + mRandom.nextInt(5000)) // 增加动画持续时间


                            .setListener(new AnimatorListener(RandomPiggies.this, imageView));
                    imageView.setTag(R.id.current_duration, animator.getDuration());
                    imageView.setTag(animator);
                    ValueAnimatorUtil.resetDurationScale();
                    animator.start();
                });
                try {
                    Thread.sleep(300 + mRandom.nextInt(1700));
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
    }

    public void stopShow() {
        isNeed = false;
        if (mTask != null) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    @Override
    protected MyLayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(0, 0);
    }

    private static class AnimatorListener implements Animator.AnimatorListener {

        private ViewGroup mViewGroup;
        private View mView;
        private boolean isHasDragAction;

        AnimatorListener(ViewGroup viewGroup, View view) {
            mViewGroup = viewGroup;
            mView = view;
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            //小猪跑出屏幕后自动移除
            if (!isHasDragAction) {
                mViewGroup.removeView(mView);
                mViewGroup = null;
                mView = null;
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isHasDragAction = true;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
