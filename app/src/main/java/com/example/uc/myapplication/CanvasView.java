package com.example.uc.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * Created by tangsj on 2017/8/4.
 */

public class CanvasView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 刻度尺高度
    private static final int DIVIDING_RULE_HEIGHT = 60;
    // 距离左右间
    private static final int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 20;

    // 第一条线距离边框距离
    private static final int FIRST_LINE_MARGIN = 5;
    // 打算绘制的厘米数
    private static final int DEFAULT_COUNT = 10;
    private int mDividRuleHeight;
    private int mHalfRuleHeight;
    private int mDividRuleLeftMargin;
    private int mFirstLineMargin;
    private Resources mResources = getResources();
    private Rect mOutRect;
    private int mLineInterval;
    private int mTotalWidth = 1000;
    private int mRuleBottom = 150;
    private int top = 30;
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mLineStartX = 20;
    private int mMaxLineTop = 60;
    private int mMiddleLineTop = 40;
    private int mMinLineTop = 25;
    private int mSpaceTextVer = 10;

    public CanvasView(Context context) {
        super(context);
        initData();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawArcTest(canvas);

        // 绘制外框
        drawOuter(canvas);
//        // 绘制刻度线
        drawLines(canvas);
//        // 绘制数字
        drawNumbers(canvas);

//        drawSquare(canvas);

        drawWatch(canvas);

    }

    private void drawWatch(Canvas canvas) {
        int watchStokeWidth  = 15;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(watchStokeWidth);
        int first = canvas.save();
        float CenterX = canvas.getWidth() /2;
        float CenterY = canvas.getHeight() / 2;
        float radius = 200;

//        RectF ol = new RectF(CenterX - radius, CenterY - radius, CenterX + radius, CenterY + radius);
//        canvas.drawArc(ol, 0, 360, false, paint);
        canvas.drawCircle(CenterX, CenterY, radius, paint);
        int pointerWidth = 3;
        int degree = 6;
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(pointerWidth);
//        paint.setColor(Color.WHITE);
        canvas.translate(CenterX, CenterY);//移动canvas原点
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mResources.getDisplayMetrics()));
        p.setColor(Color.WHITE);
        p.setStrokeWidth(5);
        for (int i = 0; i < 360; i+=degree) {
            if (i % 30 == 0) {
                canvas.drawLine(0,  - radius + watchStokeWidth /2 , 0, - radius + watchStokeWidth/2 + 35, paint);
                String text = i/ 30 == 0 ? "12" : String.valueOf(i / 30);
                float textW = p.measureText(text);
                int second = canvas.save();
                if (i != 0) {
                    Rect boundRect = new Rect();
                    p.getTextBounds(text, 0, text.length(), boundRect);
                    //获得文字的高度
                    int textH = boundRect.bottom - boundRect.top;
                    int space = 10;
                    //????int second = canvas.save(); 相当于canvas 还原到旋转前的状态，正常draw完Text后就再反向旋转就可以得到正向文字
                    canvas.translate(0, -radius + 35 + textH + space);
                    canvas.rotate(-i);
                    canvas.drawText(text, -(boundRect.right - boundRect.left) / 2 , boundRect.bottom, p);
                } else {
                    canvas.drawText(text, - textW / 2 , - radius + watchStokeWidth/2 + 60, p);
                }
                canvas.restore();

            } else {
                canvas.drawLine(0, - radius + watchStokeWidth /2 , 0, - radius + watchStokeWidth/2 + 20, paint);
            }
            canvas.rotate(degree);
        }

        canvas.drawPoint(CenterX, CenterY, paint);

        canvas.restore();
    }

    /**
     * 绘制正方形
     *
     * @param canvas
     */
    private void drawSquare(Canvas canvas) {

        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 将画笔设置为空心
        mPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔颜色
        mPaint.setColor(Color.BLACK);
        // 设置画笔宽度
        mPaint.setStrokeWidth(3);

        float TOTAL_SQUARE_COUNT =  30;
        float mHalfWidth = canvas.getWidth() / 2;
        float mHalfHeight = canvas.getHeight() / 2 - 200 ;
        Rect mSquareRect = new Rect(0, 0, (int) mHalfWidth * 2, (int)mHalfHeight * 2);

        canvas.save();
        canvas.translate(0, 500);

        for (int i = 0; i < TOTAL_SQUARE_COUNT; i++) {
            // 保存画布
            canvas.save();
            float fraction = (float) i / TOTAL_SQUARE_COUNT;
            // 将画布以正方形中心进行缩放
            canvas.scale(fraction, fraction, mHalfWidth, mHalfHeight);
            canvas.drawRect(mSquareRect, mPaint);
            // 画布回滚
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawArcTest(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
//        canvas.save();
//        mPaint.setColor(Color.WHITE);
//        canvas.translate(100, 100);
//        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
//        canvas.translate(100, 100);
//        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
//        canvas.restore();


        canvas.save();
        canvas.translate(100, 100);
        int stick = 20;
        mPaint.setColor(Color.RED);
        int x = (getWidth()) / 6;
        int y = (getHeight()) / 6;
        int degree = 10;
        canvas.translate(stick * 4, stick * 4);
        mPaint.setStrokeWidth(10);
        int count = 360 / 10;
        for (int i = 0; i < count; i++) {
            canvas.drawLine(x, 20, x, 0, mPaint);
            canvas.rotate(degree, x, y);
        }
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(15);
        p.setStrokeCap(Paint.Cap.BUTT);

        canvas.drawPoint(x, y, p);
        canvas.restore();
    }

    private void drawNumbers(Canvas canvas) {
        mLineStartX = mDividRuleLeftMargin + mFirstLineMargin;
        canvas.save();
        canvas.translate(mLineStartX, 0);
        int top = mMaxLineTop;
        for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
            if (i % 10 == 0) {
                mLinePaint.setColor(Color.RED);
                top = mMaxLineTop;
                String text = String.valueOf(i / 10);
                mPaint.setColor(Color.BLUE);
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mResources.getDisplayMetrics()));
                canvas.drawText(text, 0, mRuleBottom - top - mSpaceTextVer, mPaint);
            }
            canvas.translate(mLineInterval, 0);

        }
        canvas.restore();
    }

    private void drawOuter(Canvas canvas) {
        mPaint.setColor(0xffedffee);
        canvas.drawRect(mOutRect, mPaint);
    }

    /**
     * 绘制刻度线
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        mLineStartX = mDividRuleLeftMargin + mFirstLineMargin;
        canvas.save();
        canvas.translate(mLineStartX, 0);
        int top = mMaxLineTop;
        for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
            if (i % 10 == 0) {
                mLinePaint.setColor(Color.RED);
                top = mMaxLineTop;
            } else if (i % 5 == 0) {
                mLinePaint.setColor(Color.BLUE);
                top = mMiddleLineTop;
            } else {
                mLinePaint.setColor(Color.BLACK);
                top = mMinLineTop;
            }

            canvas.drawLine(0, mRuleBottom, 0, mRuleBottom - top, mLinePaint);
            canvas.translate(mLineInterval, 0);

        }
        canvas.restore();

    }


    private void initData() {
        mTotalWidth = mResources.getDisplayMetrics().widthPixels - 2 * mDividRuleLeftMargin;
        mDividRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_HEIGHT, mResources.getDisplayMetrics());
        mHalfRuleHeight = mDividRuleHeight / 2;

        mDividRuleLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_MARGIN_LEFT_RIGHT, mResources.getDisplayMetrics());
        mFirstLineMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                FIRST_LINE_MARGIN, mResources.getDisplayMetrics());

        mOutRect = new Rect(mDividRuleLeftMargin, top, mTotalWidth - mDividRuleLeftMargin,
                mRuleBottom);

        mLineInterval = (mTotalWidth - 2 * mDividRuleLeftMargin - 2 * mFirstLineMargin)
                / (DEFAULT_COUNT * 10 - 1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
