package com.lei.testapplication;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;

/**
 * Created by lei on 2019-09-25.
 */

public class MyBlurPopWin extends BlurPopupWindow
{
    Context mContext;
    GridLayout gridLayout;
//    SpringChain springChain;
    Button closeBtn;
    public MyBlurPopWin(Context context) {
        super(context);
        mContext=context;
    }


    @Override//父类回调，父类创建一个根FrameLayout，这里用来添加里面的内容。
    public void onCreatView(FrameLayout container) {
        //初始化很多东西，父类。
        super.onCreatView(container);
        //这里必须用三个参数，并且设置成false才能获得到layoutparams！！！否则获得到空的。
        //当然也可以自己设置layoutparams。
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop,container,false);
        FrameLayout.LayoutParams frameLayout= (FrameLayout.LayoutParams) rootView.getLayoutParams();
        frameLayout.gravity= Gravity.BOTTOM;
        //rootView.findViewById(R.id.btn).setOnClickListener(this);
        //FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layoutParams.gravity=Gravity.BOTTOM;
        //rootView.setLayoutParams(layoutParams);
        //找到id，view
//        gridLayout = (GridLayout) rootView.findViewById(R.id.group);
        closeBtn= (Button) rootView.findViewById(R.id.button);
        //设置监听，用来关闭popwin，用到了父类的backpress。
        setClickableItems(closeBtn);
        //添加到父容器。
        container.addView(rootView);
    }

    @Override//show的时候父类动画的时候调用此函数，在这里重写，实现自己需要的函数
    public void onShowAnimStart() {
//        Log.e("xxx","onShowAnimStart");
//        super.onShowAnimStart();
//        springChain =SpringChain.create(50,6,40,7);             //faceBook的rebounds
//        for (int i=0;i<gridLayout.getChildCount();i++){
//            final View child=gridLayout.getChildAt(i);
//            springChain.addSpring(new SimpleSpringListener(){
//                @Override
//                public void onSpringUpdate(Spring spring) {
//                    super.onSpringUpdate(spring);
//                    double value=spring.getCurrentValue();
//                    child.setTranslationY((float) (value*gridLayout.getMeasuredHeight()));
//                }
//            });
//        }
//        for(Spring s:springChain.getAllSprings()){
//            s.setCurrentValue(1);
//        }
//
//        springChain.setControlSpringIndex(1).getControlSpring().setEndValue(0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.button:
                onBackpressed();
                break;
        }
    }

    @Override
    public boolean onBackpressed() {
        boolean isDismiss=super.onBackpressed();
        if (!isDismiss){
//            springChain.setControlSpringIndex(1).getControlSpring().setEndValue(1);
        }
        return isDismiss;
    }
}

