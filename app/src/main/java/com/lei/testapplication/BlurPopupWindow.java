package com.lei.testapplication;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by lei on 2019-09-25.
 */

public class BlurPopupWindow {
    PopupWindow mPopupWindow;
    View mRootView;
    FrameLayout mContentView;
    ImageView mBlurImageView;
    Activity mActivity;
    Button closeBtn;
    private int mDelayAnim = 150;

    public BlurPopupWindow(Context context) {
        mActivity = (Activity) context;
        if (mActivity.getParent() != null) {
            ViewGroup contentView = (ViewGroup) mActivity.getParent().getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            mRootView = (ViewGroup) contentView.getChildAt(0);
            mActivity = mActivity.getParent();
        }
        if (mRootView == null) {
            ViewGroup contentView = (ViewGroup) mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            mRootView = (ViewGroup) contentView.getChildAt(0);
        }
        generateContentView();
        BlurKit.init(mActivity.getApplicationContext());

    }

    public boolean isShowing() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            return true;
        }
        return false;
    }

    boolean isAnimRuning;

    public boolean onBackpressed() {
        if (mPopupWindow == null || !mPopupWindow.isShowing()) {
            return true;
        }
        if (isAnimRuning) {
            return true;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(255, 0).setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                mBlurImageView.setImageAlpha(value);
            }
        });
        valueAnimator.setStartDelay(mDelayAnim);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setRepeatCount(0);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                isAnimRuning = false;

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimRuning = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
        isAnimRuning = true;
        return false;
    }

    public void onShowAnimStart() {
    }

    public void onShowAnimEnd() {

    }

    public final void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        isAnimRuning = false;
    }

    private void generateContentView() {
        mContentView = new FrameLayout(mActivity);
        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBlurImageView = new ImageView(mActivity);
        mBlurImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.addView(mBlurImageView, params);
        mPopupWindow.setContentView(mContentView);
        mPopupWindow.setAnimationStyle(0);
//        mPopupWindow.setFocusable(true);
//        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mContentView.setFocusableInTouchMode(true);
        mContentView.setFocusable(true);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        mContentView.setForeground(new ColorDrawable(Color.BLACK));
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mContentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {


                    return true;
                }
                return false;
            }
        });

    }

    public void show() {
        if (!isViewCreated) {
            isViewCreated = true;
            onCreatView(mContentView);
        }
        Bitmap bitmap = BlurKit.getInstance().fastBlur(mRootView, 10, 0.12f);
//        Bitmap bitmap = BlurKit.getInstance().fastBlur(mRootView,5,0.5f);
        mBlurImageView.setImageBitmap(bitmap);
        mBlurImageView.setImageAlpha(0);
        mBlurImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 255).setDuration(400);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int value = (int) valueAnimator.getAnimatedValue();
                        mBlurImageView.setImageAlpha(value);
                    }
                });
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setRepeatCount(0);
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        onShowAnimStart();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimRuning = false;
                        onShowAnimEnd();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isAnimRuning = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
                mBlurImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                isAnimRuning = true;
                return true;
            }
        });

        mPopupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
    }

    boolean isViewCreated;

    public void onCreatView(FrameLayout container) {
        //初始化很多东西，父类。
        //这里必须用三个参数，并且设置成false才能获得到layoutparams！！！否则获得到空的。
        //当然也可以自己设置layoutparams。
        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_pop, container, false);
        FrameLayout.LayoutParams frameLayout = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        frameLayout.gravity = Gravity.BOTTOM;

        closeBtn = (Button) rootView.findViewById(R.id.button);
        //设置监听，用来关闭popwin，用到了父类的backpress。
        setClickableItems(closeBtn);
        //添加到父容器。
        container.addView(rootView);
    }

    @SuppressWarnings("unchecked")
    final public <E extends View> E findView(int id) {
        try {
            return (E) mContentView.findViewById(id);
        } catch (ClassCastException e) {

            throw e;
        }
    }

    /* Click listener that avoid double click event in short time*/
    public NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {

        @Override
        public void onClickInternal(View v) {
            BlurPopupWindow.this.onClick(v);
        }
    };

    public interface NoDoubleClickListener {
        public void onClickInternal(View v);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                onBackpressed();
                break;
        }
    }

    /*Set all widget that need to implements OnClick() here*/
    protected void setClickableItems(View... views) {
        if (views != null && views.length > 0) {
            for (View v : views) {
                if (v != null)
                    v.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BlurPopupWindow.this.onClick(v);
                        }
                    }));
            }
        }
    }

    /*Set all widget that need to implements OnClick() here*/
    protected void setClickableItems(int... residGroup) {
        if (residGroup != null && residGroup.length > 0) {
            for (int resid : residGroup) {
                if (resid != 0) {
                    findView(resid).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BlurPopupWindow.this.onClick(v);
                        }
                    });
                }
            }
        }
    }


}
