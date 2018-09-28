package com.scanmaster.commonlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.scanmaster.commonlibrary.utils.UIUtils;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/26
 * explain:
 */
public class DotRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    private Paint mPaint;
    private boolean isShowDot;
    private boolean isShowNumberDot;

    private int width;
    private int height;

    /**
     * 顶部图片宽
     */
    private int IntrinsicWidth;

    private String numberText;
    /**
     * 圆点和未读消息的坐标
     */
    private float pivotX;
    private float pivotY;
    /**
     * 圆点半径
     */
    private final int circleDotRadius = UIUtils.dip2px(getContext(), 3);
    //圆点默认颜色
    private final int PAINT_COLOR_DEFAULT = Color.RED;
    private RectF mRoundRect;//圆角矩形
    private Rect mTextRect;//文字的矩形

    public DotRadioButton(Context context) {
        this(context, null);
    }

    public DotRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextRect = new Rect();
        init();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(PAINT_COLOR_DEFAULT);
        mPaint.setTextSize(16f);
    }

    private void init() {
        Drawable drawable = getCompoundDrawables()[1];
        if (drawable != null) {
            IntrinsicWidth = drawable.getIntrinsicWidth();
        }
        /**
         * 给RadioButton增加一定的padding
         * */
        if (getPaddingBottom() == 0) {
            setPadding(UIUtils.dip2px(getContext(), 10), UIUtils.dip2px(getContext(), 10), UIUtils.dip2px(getContext(), 10), UIUtils.dip2px(getContext(), 10));
        }
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        Drawable drawable = getCompoundDrawables()[1];
        if (drawable != null) {
            IntrinsicWidth = drawable.getIntrinsicWidth();
        }
        /*
         * 给RadioButton增加一定的padding
         */
        if (getPaddingBottom() == 0) {
            setPadding(UIUtils.dip2px(getContext(), 10), UIUtils.dip2px(getContext(), 10), UIUtils.dip2px(getContext(), 10), UIUtils.dip2px(getContext(), 10));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        pivotX = (float) (IntrinsicWidth / 2 > width / 2 ? width / 2 : IntrinsicWidth / 2);
        pivotY = (float) (height / 2 * 0.8);

        updateRoundRect();
    }

    private void updateRoundRect() {
        mRoundRect = new RectF(pivotX-mTextRect.width()/2 - UIUtils.dip2px(getContext(), 4), -pivotY - mTextRect.height() / 2, pivotX + mTextRect.width()/2 + UIUtils.dip2px(getContext(), 4), -pivotY + mTextRect.height());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        if (isShowDot) {
            canvas.drawCircle(pivotX, -pivotY, circleDotRadius, mPaint);
        } else if (isShowNumberDot && !TextUtils.isEmpty(numberText)) {
            mPaint.setColor(Color.RED);
            canvas.drawRoundRect(mRoundRect, mRoundRect.height() / 2, mRoundRect.height() / 2, mPaint);
            mPaint.setColor(Color.WHITE);
            canvas.drawText(numberText, pivotX, -pivotY + mRoundRect.height()/2 , mPaint);
        }
    }

    /**
     * `
     * 设置是否显示小圆点
     */
    public void setShowSmallDot(boolean isShowDot) {
        this.isShowDot = isShowDot;
        invalidate();
    }

    /**
     * 设置是否显示数字
     */
    public void setNumberDot(boolean isShowNumberDot, @NonNull String text) {
        this.isShowNumberDot = isShowNumberDot;
        this.numberText = text;
        updateTextSize();
        if (isShowNumberDot) {
            isShowDot = false;
        }
        invalidate();
    }

    private void updateTextSize() {
        if (null == mTextRect)
            mTextRect = new Rect();
        mPaint.getTextBounds(numberText, 0, numberText.length(), mTextRect);
        updateRoundRect();
    }
}
