package com.lei.testapplication;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.security.auth.login.LoginException;

import id.zelory.compressor.Compressor;
import io.reactivex.schedulers.Schedulers;
import razerdp.basepopup.BasePopupWindow;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button mButton;
    Button mButton2;
    Button mButton3;
    Button mButton4;
    Button mButton5;
    View mPopLayout;
    WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPopLayout = this.getLayoutInflater().inflate(R.layout.layout_pop, null);
        mPopLayout.setVisibility(View.VISIBLE);
        mButton = findViewById(R.id.button);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
//        final BlurPopupWindow popWin = new BlurPopupWindow(this);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BasePopupWindow popupWindow = new Demo
//                popupWindow.showPopupWindow();
//                new PopupWindow(mPopLayout, 800, 800).showAsDropDown(mButton);
//                popWin.show();
//            }
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestAlertWindowPermission();
        }

        mButton.setOnClickListener(v -> popWindow());
        mButton2.setOnClickListener(v -> popDialog());
        mButton3.setOnClickListener(v -> popSystemDialog());
        mButton4.setOnClickListener(v -> {
            addMask(mPopLayout.getWindowToken());
        });
        mButton5.setOnClickListener(v -> popToast());
    }

    private static final int REQUEST_CODE = 1;

    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Log.i(TAG, "onActivityResult granted");
            } else {
                Log.i(TAG, "onActivityResult: not granted");
            }
        }
    }

    private void yasou() {
        Luban.with(this)
                .load(new File("/sdcard/Slide_0.png"))
                .ignoreBy(100)
                .putGear(3)
                .setTargetDir("/sdcard")
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart ");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.i(TAG, "onSuccess: " + file.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                }).launch();
    }

    private void yasou2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "run: start time0 - " + System.currentTimeMillis());
                    new Compressor(getApplicationContext())
                            .setMaxWidth(3840)
                            .setMaxHeight(2160)
                            .setQuality(100)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath("/sdcard/1/")
                            .compressToFile(
                                    new File("/sdcard/1/d.png"));

                    Log.i(TAG, "run: start time1 - " + System.currentTimeMillis());
//                    new Compressor(getApplicationContext())
//                            .setMaxWidth(3840)
//                            .setMaxHeight(2160)
//                            .setQuality(100)
//                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                            .setDestinationDirectoryPath("/sdcard/1/")
//                            .compressToFile(new File("/sdcard/1/d.png"));
//                    Log.i(TAG, "run: start time2 - " + System.currentTimeMillis());
//                    new Compressor(getApplicationContext())
//                            .setMaxWidth(3840)
//                            .setMaxHeight(2160)
//                            .setQuality(100)
//                            .setCompressFormat(Bitmap.CompressFormat.PNG)
//                            .setDestinationDirectoryPath("/sdcard/1/")
//                            .compressToFile(
//                                    new File("/sdcard/1/d.png"));
//                    Log.i(TAG, "run: start time3 - " + System.currentTimeMillis());


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void test() {
//        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        Context context;
//        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
//        windowManager.addView();
//        View
        ViewGroup viewGroup = findViewById(R.id.content);
        viewGroup.getChildAt(0);
//        getApplicationContext()

//        TextUtils.isEmpty()
//        TimeUtils
//        DateUtils.formatDateTime()
//        PhoneNumberUtils.isVoiceMailNumber()
//        SizeUtils.px2dp()
//        ColorUtils.getColor()
//        Binder.allowB

    }

    public void popSystemWindow() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.height = SizeUtils.dp2px(100);
        layoutParams.width = SizeUtils.dp2px(1000);
        layoutParams.gravity = Gravity.CENTER | Gravity.START;
        final Button button = new Button(this);
        button.setOnClickListener(windowManager::removeView);
        button.setBackground(getResources().getDrawable(R.drawable.a));
        windowManager.addView(button, layoutParams);
    }

    public void popWindow() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.type = WindowManager.LayoutParams.FIRST_SUB_WINDOW;
        layoutParams.height = SizeUtils.dp2px(300);
        layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.BOTTOM;
        final Button button = new Button(this);
        button.setOnClickListener(windowManager::removeView);
        button.setBackground(getResources().getDrawable(R.drawable.a));
        windowManager.addView(button, layoutParams);
    }


    private void addMask(IBinder token) {
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        wl.height = WindowManager.LayoutParams.MATCH_PARENT;
        wl.format = PixelFormat.TRANSLUCENT; // 不设置这个弹出框的透明遮罩显示为黑色
        wl.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        wl.token = token; // 获取当前Activity中的View中的token,来依附Activity
        final View maskView = new View(this);
        maskView.setBackgroundColor(Color.parseColor("#4D000000"));
        maskView.setOnClickListener(v -> {
            windowManager.removeViewImmediate(maskView);
        });
        windowManager.addView(maskView, wl);
    }

    public void popDialog() {
        Dialog dialog = new Dialog(this);
        TextView text = new TextView(this);
        text.setText("a dialog");
        dialog.setContentView(text);
        dialog.getWindow().setGravity(Gravity.CENTER | Gravity.START);
        dialog.show();
    }

    public void popSystemDialog() {
        Dialog dialog = new Dialog(this);
        TextView text = new TextView(this);
        text.setText("a dialog");
        dialog.setContentView(text);
        dialog.getWindow().setGravity(Gravity.CENTER | Gravity.START);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    public void popToast() {
        Toast toast = Toast.makeText(this, "a toast", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.START | Gravity.CENTER, 0, 0);
        toast.show();
    }
}
