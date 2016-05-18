package com.luna.gao.androidtag_core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lunagao on 16/5/17.
 */
public class AndroidTag extends View implements View.OnTouchListener{

    /**
     * 文本
     */
    private String tagTitleText;
    /**
     * 文本的颜色
     */
    private int tagTitleTextSize;
    /**
     * 文本的大小
     */
    private int tagTitleColor;
    private int tagBackgroundColor;
    private int tagBorderWidth;
    private int tagBorderColor;
    private int tagBorderPaddingTop;
    private int tagBorderPaddingLeft;
    private int tagBorderPaddingBottom;
    private int tagBorderPaddingRight;
    private int tagBorderRadius;
    private int tagBorderType;
    private String tagOnClickTitleText;
    private int tagOnClickTitleColor;
    private int tagOnClickBackgroundColor;
    private int tagOnClickBorderWidth;
    private int tagOnClickBorderColor;
    private String tagSelectedTitleText;
    private int tagSelectedTitleColor;
    private int tagSelectedBackgroundColor;
    private int tagSelectedBorderWidth;
    private int tagSelectedBorderColor;
    private boolean tagIsSelected;
    private int tagType;

    private String currentTitleString;
    private int currentTitleColor;
    private int currentBorderColor;
    private int currentBorderWidth;
    private int currentBackgroundColor;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public AndroidTag(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public AndroidTag(Context context)
    {
        this(context, null);
    }

    /**
     * 获得样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AndroidTag(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        /**
         * 获得我们所定义的自定义样式属性
         */
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AndroidTag, defStyle, R.style.AndroidTag);
        try {
            tagTitleText = a.getString(R.styleable.AndroidTag_tagTitleText);
            tagTitleTextSize = (int) a.getDimension(R.styleable.AndroidTag_tagTitleTextSize, getResources().getDimension(R.dimen.titleTextSize));
            tagTitleColor = a.getColor(R.styleable.AndroidTag_tagTitleColor, getResources().getColor(R.color.titleColor));
            tagBackgroundColor = a.getColor(R.styleable.AndroidTag_tagBackgroundColor, getResources().getColor(R.color.backgroundColor));
            tagBorderPaddingTop = (int) a.getDimension(R.styleable.AndroidTag_tagBorderPaddingTop, getResources().getDimension(R.dimen.borderPaddingTop));
            tagBorderPaddingLeft = (int) a.getDimension(R.styleable.AndroidTag_tagBorderPaddingLeft, getResources().getDimension(R.dimen.borderPaddingLeft));
            tagBorderPaddingBottom = (int) a.getDimension(R.styleable.AndroidTag_tagBorderPaddingBottom, getResources().getDimension(R.dimen.borderPaddingBottom));
            tagBorderPaddingRight = (int) a.getDimension(R.styleable.AndroidTag_tagBorderPaddingRight, getResources().getDimension(R.dimen.borderPaddingRight));
            tagBorderRadius = (int) a.getDimension(R.styleable.AndroidTag_tagBorderRadius, getResources().getDimension(R.dimen.borderRadius));
            tagBorderWidth = (int) a.getDimension(R.styleable.AndroidTag_tagBorderWidth, getResources().getDimension(R.dimen.tagBorderWidth));
            tagBorderColor = a.getColor(R.styleable.AndroidTag_tagBorderColor, getResources().getColor(R.color.borderColor));
            tagBorderType = a.getInt(R.styleable.AndroidTag_tagBorderType, 0);
            tagOnClickTitleText = a.getString(R.styleable.AndroidTag_tagOnClickTitleText);
            tagOnClickTitleColor = a.getColor(R.styleable.AndroidTag_tagOnClickTitleColor,  getResources().getColor(R.color.titleColor));
            tagOnClickBackgroundColor = a.getColor(R.styleable.AndroidTag_tagOnClickBackgroundColor, getResources().getColor(R.color.onClickBackgroundColor));
            tagOnClickBorderWidth = (int) a.getDimension(R.styleable.AndroidTag_tagOnClickBorderWidth, getResources().getDimension(R.dimen.tagBorderWidth));
            tagOnClickBorderColor = a.getColor(R.styleable.AndroidTag_tagOnClickBorderColor, getResources().getColor(R.color.borderColor));
            tagSelectedTitleText = a.getString(R.styleable.AndroidTag_tagSelectedTitleText);
            tagSelectedTitleColor = a.getColor(R.styleable.AndroidTag_tagSelectedTitleColor, getResources().getColor(R.color.onSelectTitleColor));
            tagSelectedBackgroundColor = a.getColor(R.styleable.AndroidTag_tagSelectedBackgroundColor, getResources().getColor(R.color.onSelectBackgroundColor));
            tagSelectedBorderWidth = (int) a.getDimension(R.styleable.AndroidTag_tagSelectedBorderWidth, getResources().getDimension(R.dimen.tagBorderWidth));
            tagSelectedBorderColor = a.getColor(R.styleable.AndroidTag_tagSelectedBorderColor, getResources().getColor(R.color.borderColor));
            tagIsSelected = a.getBoolean(R.styleable.AndroidTag_tagIsSelected, false);
            tagType = a.getInt(R.styleable.AndroidTag_tagType, 0);
        } finally {
            a.recycle();
        }

        this.setClickable(true);
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mBound = new Rect();

        currentTitleString = tagTitleText;
        currentTitleColor = tagTitleColor;
        currentBackgroundColor = tagBackgroundColor;
        currentBorderColor = tagBorderColor;
        currentBorderWidth = tagBorderWidth;

        // 设置圆角或直角
        if (tagBorderType == 1) {
            tagBorderRadius = 0;
        }
        // 设置是否为选中状态
        if (tagIsSelected) {
            currentTitleString = tagTitleText;
            currentTitleString = tagSelectedTitleText == null ? tagTitleText : tagSelectedTitleText;
        }
        if (tagOnClickTitleText == null) {
            tagOnClickTitleText = currentTitleString;
        }
        if (tagSelectedTitleText == null) {
            tagSelectedTitleText = currentTitleString;
        }
        // 设置点击事件
        this.setOnTouchListener(this);
    }

    public Boolean isTagSelected() {
        return tagIsSelected;
    }

    public void setTagSelected(Boolean selected) {
        tagIsSelected = selected;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(tagTitleTextSize);
            mPaint.getTextBounds(currentTitleString, 0, currentTitleString.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight() + 2 + tagBorderPaddingRight + tagBorderPaddingLeft);
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(tagTitleTextSize);
            mPaint.getTextBounds(currentTitleString, 0, currentTitleString.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom() + 2 + tagBorderPaddingTop + tagBorderPaddingBottom);
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        /**
         * 获得绘制文本的宽和高
         */
        mPaint.setTextSize(tagTitleTextSize);
        mPaint.getTextBounds(currentTitleString, 0, currentTitleString.length(), mBound);

        // 绘制背景
        setBackground(canvas);
        // 绘制border
        setBorderColor(canvas);
        // 绘制文字
        setTitle(canvas);
    }

    private void setBackground(Canvas canvas) {
        drawBackgroundRoundRect(Paint.Style.FILL, canvas, currentBackgroundColor);
    }

    private void setBorderColor(Canvas canvas) {
        drawBackgroundRoundRect(Paint.Style.STROKE, canvas, currentBorderColor);
    }

    private void setTitle(Canvas canvas) {
        mPaint.reset();
        mPaint.setTextSize(tagTitleTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(currentTitleColor);
        canvas.drawText(currentTitleString, tagBorderPaddingLeft, tagBorderPaddingTop + mBound.height(), mPaint);
    }

    private void drawBackgroundRoundRect(Paint.Style style, Canvas canvas, int color) {
        mPaint.reset();
        mPaint.setColor(color);
        mPaint.setStrokeWidth(currentBorderWidth);
        mPaint.setStyle(style);
        RectF rectF = new RectF();
        rectF.left = 1;
        rectF.right = 1 + mBound.width() + tagBorderPaddingRight + tagBorderPaddingLeft;
        rectF.top = 1 ;
        rectF.bottom = 1 + mBound.height() + tagBorderPaddingTop + tagBorderPaddingBottom;
        canvas.drawRoundRect(rectF, tagBorderRadius, tagBorderRadius, mPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                currentTitleString = tagOnClickTitleText;
                currentTitleColor = tagOnClickTitleColor;
                currentBackgroundColor = tagOnClickBackgroundColor;
                currentBorderColor = tagOnClickBorderColor;
                currentBorderWidth = tagOnClickBorderWidth;
                break;
            case MotionEvent.ACTION_UP:
                currentTitleString = tagTitleText;
                currentTitleColor = tagTitleColor;
                currentBackgroundColor = tagBackgroundColor;
                currentBorderColor = tagBorderColor;
                currentBorderWidth = tagBorderWidth;
                int[] location = new int[2];
                this.getLocationOnScreen(location);
                if (event.getX() >= 0 && event.getX() <= this.getMeasuredWidth()
                        && event.getY() >= 0 && event.getY() <= this.getMeasuredHeight()) {
                    tagIsSelected = !tagIsSelected;
                }
                if (tagIsSelected) {
                    currentTitleString = tagSelectedTitleText;
                    currentTitleColor = tagSelectedTitleColor;
                    currentBackgroundColor = tagSelectedBackgroundColor;
                    currentBorderColor = tagSelectedBorderColor;
                    currentBorderWidth = tagSelectedBorderWidth;
                }
                break;
        }
        postInvalidate();
        return false;
    }
}
