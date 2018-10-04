package com.scanmaster.commonlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public class ArcView extends View {

    private int width;
    private int height;
    private double ovalRadius;//圆弧对应圆的半径
    private Paint mPaint;

    private int mColor = Color.WHITE;
    private RectF mRectF;
    private float mStartAngle;
    private float mSweepAngle;
    private Path mPath;
    private RectF mViewRectF;//view的矩形

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initPath();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    private void initPath() {
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mPath.reset();
        if (null == mViewRectF)
            mViewRectF = new RectF();
        mViewRectF.set(0, 0, width, height);
        mPath.addRect(mViewRectF, Path.Direction.CCW);
//        ovalRadius = width * 2;
        ovalRadius = Math.abs((Math.pow(width / 2, 2) + Math.pow(height, 2)) / (height * 2));
        if (null == mRectF)
            mRectF = new RectF();
        mRectF.set(-(float) (ovalRadius - width / 2), (float) (-ovalRadius * 2 + height), (float) (width / 2 + ovalRadius), height);
        double startDiffer = Math.acos((width / 2) / ovalRadius) * 180 / Math.PI;
        mStartAngle = (float) (startDiffer);
        mSweepAngle = (float) (180 - startDiffer * 2);
//        mPath.addCircle(width / 2, (float) (-ovalRadius + height), (float) ovalRadius, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
