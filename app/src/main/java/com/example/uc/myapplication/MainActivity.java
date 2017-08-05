package com.example.uc.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    ImageView iv;
    int i =0;
    boolean ff = false;
    boolean ss = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        iv = (ImageView) findViewById(R.id.iv);
//        Camera camera = new Camera();
//        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.bg_top_right)).getBitmap();
//        Canvas canvas = new Canvas();
//        Matrix matrix = new Matrix();
//
//        camera.rotateY(180);
//        camera.getMatrix(matrix);
//        camera.restore();

//        iv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final float centerX = iv.getWidth() / 2.0f;
//                final float centerY = iv.getHeight() / 2.0f;
//                final Rotate3dAnimation rotation =
//                        new Rotate3dAnimation(0, 180, centerX, centerY, 310.0f, true);
//                rotation.setDuration(10);
//                rotation.setFillAfter(true);
//                rotation.setInterpolator(new AccelerateInterpolator());
//
//                iv.startAnimation(rotation);
//            }
//        },100);

//        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.bg_top_right)).getBitmap();
//        Matrix matrix = new Matrix();
//        camera.save();
//        camera.rotateY(90f);
//        camera.getMatrix(matrix);
//        camera.restore();

//        Canvas canvas = new Canvas();
//        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888, true);

//        iv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final float centerX = iv.getWidth() / 2.0f;
//                final float centerY = iv.getHeight() / 2.0f;
//                iv.setBackgroundResource(R.mipmap.bg_top_right);
//                RotateAnimation rotation = new RotateAnimation(centerX, centerY, RotateAnimation.ROTATE_DECREASE);
//                rotation.setDuration(1000);
//                rotation.setFillAfter(true);
//                iv.startAnimation(rotation);
//            }
//        }, 4000);


//        TextView tvFont = (TextView) findViewById(R.id.tv_custom_font);//TitilliumWeb-Regular.ttf
////        Typeface customFont = Typeface.createFromAsset(this.getAssets(), "fonts/TitilliumWeb-Bold.ttf");
////        tvFont.setTypeface(customFont);
//
//        iv.setBackgroundResource(R.mipmap.ff);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                iv.setScaleType(-1);
////                Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.bg_top_right)).getBitmap();
////                turnCurrentLayer(bitmap, 1, -1);
//
//                final float centerX = iv.getWidth() / 2.0f;
//                final float centerY = iv.getHeight() / 2.0f;
////                iv.setBackgroundResource(R.mipmap.ss);
//                boolean type = ff ? RotateAnimation.ROTATE_DECREASE : RotateAnimation.ROTATE_INCREASE;
//                RotateAnimation rotation = new RotateAnimation(centerX, centerY, type);
//                rotation.setDuration(600);
//                iv.startAnimation(rotation);
//
//                rotation.setInterpolatedTimeListener(new RotateAnimation.InterpolatedTimeListener() {
//                    @Override
//                    public void interpolatedTime(float interpolatedTime) {
////                        Log.d("TAG", "interpolatedTime :" + interpolatedTime);
//                        if (interpolatedTime <  0.55 && interpolatedTime > 0.5) {
//                            if (!ff) {
//                                ff = true;
//                                iv.setBackgroundResource(R.mipmap.ss);
//                            } else {
//                                iv.setBackgroundResource(R.mipmap.ff);
//                                ff = false;
//                            }
//                        }
//                        if (interpolatedTime == 1.0) {
//                            Log.d("TAG", "interpolatedTime 1.0 :" + interpolatedTime);
//                        }
//                    }
//
//                });
//            }
//        });

    }

    public void turnCurrentLayer(Bitmap srcBitmap,float sx,float sy){
        Bitmap cacheBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);// 创建缓存像素的位图
        int w = cacheBitmap .getWidth();
        int h=cacheBitmap.getHeight();

        Canvas cv = new Canvas(cacheBitmap );

        Matrix mMatrix = new Matrix();

        mMatrix.postScale(sx, sy);

        Bitmap resultBimtap= Bitmap.createBitmap(srcBitmap, 0, 0, w, h, mMatrix, true);
        cv.drawBitmap(resultBimtap,
                new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight()),
                new Rect(0, 0, w, h), null);
//        saveBitmapWithPath(resultBimtap, Bitmap.CompressFormat.JPEG,"resultBitmap.jpg");
        iv.setImageBitmap(resultBimtap);
    }
}
