package com.scanmaster.commonlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.scanmaster.commonlibrary.R;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/25
 * explain:
 */
public class DotImageView extends AppCompatImageView {

    private Drawable mDotDrawable;
    private int mDotLeft;
    private int mDotTop;
    private String mText = "22";
    private int mTextColor = Color.RED;
    private int mTextSize = 30;
    private int mTextPending = 10;
    private int mDotRadius = 10;

    private Paint mTextPaint;
    private Rect mTextRect;

    public DotImageView(Context context) {
        this(context, null);
    }

    public DotImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDotDrawable = getResources().getDrawable(R.drawable.oval_red);
        initPaint();
    }

    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w;
        int h;

        // Desired aspect ratio of the view's contents (not including padding)
        float desiredAspect = 0.0f;

        // We are allowed to change the view's width
        boolean resizeWidth = false;

        // We are allowed to change the view's height
        boolean resizeHeight = false;

        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        Drawable drawable = getDrawable();
        if (drawable == null) {
            // If no drawable, its intrinsic size is 0.
            w = h = 0;
        } else {
            w = drawable.getIntrinsicWidth();
            h = drawable.getIntrinsicHeight();
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
            int textHeight = mTextRect.height();
            int textWidth = mTextRect.width();
            if (textHeight > 0 && textWidth > 0) {
                mDotRadius = textHeight / 2 + mTextPending;
                w += mDotRadius;
                h += textHeight + mDotRadius;
            }

            if (w <= 0) w = 1;
            if (h <= 0) h = 1;

            // We are supposed to adjust view bounds to match the aspect
            // ratio of our drawable. See if that is possible.
            if (getAdjustViewBounds()) {
                resizeWidth = widthSpecMode != MeasureSpec.EXACTLY;
                resizeHeight = heightSpecMode != MeasureSpec.EXACTLY;

                desiredAspect = (float) w / (float) h;
            }
        }

        final int pleft = getPaddingLeft();
        final int pright = getPaddingRight();
        final int ptop = getPaddingTop();
        final int pbottom = getPaddingBottom();

        int widthSize;
        int heightSize;

        if (resizeWidth || resizeHeight) {
            /* If we get here, it means we want to resize to match the
                drawables aspect ratio, and we have the freedom to change at
                least one dimension.
            */

            // Get the max possible width given our constraints
            widthSize = resolveAdjustedSize(w + pleft + pright, getMaxWidth(), widthMeasureSpec);

            // Get the max possible height given our constraints
            heightSize = resolveAdjustedSize(h + ptop + pbottom, getMaxHeight(), heightMeasureSpec);

            if (desiredAspect != 0.0f) {
                // See what our actual aspect ratio is
                final float actualAspect = (float) (widthSize - pleft - pright) /
                        (heightSize - ptop - pbottom);

                if (Math.abs(actualAspect - desiredAspect) > 0.0000001) {

                    boolean done = false;

                    // Try adjusting width to be proportional to height
                    if (resizeWidth) {
                        int newWidth = (int) (desiredAspect * (heightSize - ptop - pbottom)) +
                                pleft + pright;


                        if (newWidth <= widthSize) {
                            widthSize = newWidth;
                            done = true;
                        }
                    }

                    // Try adjusting height to be proportional to width
                    if (!done && resizeHeight) {
                        int newHeight = (int) ((widthSize - pleft - pright) / desiredAspect) +
                                ptop + pbottom;

                        if (newHeight <= heightSize) {
                            heightSize = newHeight;
                        }
                    }
                }
            }
        } else {
            /* We are either don't want to preserve the drawables aspect ratio,
               or we are not allowed to change view dimensions. Just measure in
               the normal way.
            */
            w += pleft + pright;
            h += ptop + pbottom;

            w = Math.max(w, getSuggestedMinimumWidth());
            h = Math.max(h, getSuggestedMinimumHeight());

            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
        }

//        setMeasuredDimension(widthSize, heightSize);
        updateDot(getDrawable(), mDotDrawable);
    }

    private int resolveAdjustedSize(int desiredSize, int maxSize,
                                    int measureSpec) {
        int result = desiredSize;
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                /* Parent says we can be as big as we want. Just don't be larger
                   than max size imposed on ourselves.
                */
                result = Math.min(desiredSize, maxSize);
                break;
            case MeasureSpec.AT_MOST:
                // Parent says we can be as big as we want, up to specSize.
                // Don't be larger than specSize, and don't be larger than
                // the max size imposed on ourselves.
                result = Math.min(Math.min(desiredSize, specSize), maxSize);
                break;
            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mText)) return;
        canvas.save();
        if (getImageMatrix() == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
            canvas.translate(mDotLeft, mDotTop);
        } else {

        }
//        mDotDrawable.draw(canvas);
//        canvas.drawCircle();
        canvas.drawText(mText, 10, 20, mTextPaint);
        canvas.restore();

    }

    /**
     * @param d           绘制的图片
     * @param dotDrawable 小红点背景
     */

    private void updateDot(Drawable d, Drawable dotDrawable) {
        int dHeight = d.getIntrinsicHeight();
        int dWidth = d.getIntrinsicWidth();
        int dotHeight = dotDrawable.getIntrinsicHeight();
        int dotWidth = dotDrawable.getIntrinsicWidth();
        mDotTop = 0;
        mDotLeft = dWidth - dotWidth / 2;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }
}
