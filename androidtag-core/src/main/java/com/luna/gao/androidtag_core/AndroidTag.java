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

    private String tagTitleText;
    private int tagTitleTextSize;
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

    public static class BorderType {
        public static int Round = 0;
        public static int Square = 1;
    }

    public static class TagType {
        public static int Normal = 0;
        public static int Selected = 1;
    }

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
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBound = new Rect();

        textSet(false);
        currentTitleColor = tagTitleColor;
        currentBackgroundColor = tagBackgroundColor;
        currentBorderColor = tagBorderColor;
        currentBorderWidth = tagBorderWidth;

        // 设置圆角或直角
        if (tagBorderType == 1) {
            tagBorderRadius = 0;
        }

        // 设置点击事件
        this.setOnTouchListener(this);
    }

    private void textSet(Boolean changeToTitleText) {
        if (changeToTitleText) {
            currentTitleString = tagTitleText;
        }
        currentTitleString = currentTitleString == null ? tagTitleText : currentTitleString;
        if (currentTitleString == null) {
            currentTitleString = " ";
        }
        // 设置是否为选中状态
        if (tagIsSelected) {
            currentTitleString = tagSelectedTitleText == null ? tagTitleText : tagSelectedTitleText;
        }
        if (tagOnClickTitleText == null) {
            tagOnClickTitleText = currentTitleString;
        }
        if (tagSelectedTitleText == null) {
            tagSelectedTitleText = currentTitleString;
        }
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
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight() + 2 + tagBorderPaddingRight + tagBorderPaddingLeft + currentBorderWidth * 2);
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
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom() + 2 + tagBorderPaddingTop + tagBorderPaddingBottom + currentBorderWidth * 2);
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
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
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(tagTitleTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(currentTitleColor);
        canvas.drawText(currentTitleString, tagBorderPaddingLeft + currentBorderWidth, tagBorderPaddingTop + mBound.height() + currentBorderWidth, mPaint);
    }

    private void drawBackgroundRoundRect(Paint.Style style, Canvas canvas, int color) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(currentBorderWidth);
        mPaint.setStyle(style);
        RectF rectF = new RectF();
        rectF.left = 1 + currentBorderWidth;
        rectF.right = 1 + mBound.width() + tagBorderPaddingRight + tagBorderPaddingLeft + currentBorderWidth * 2;
        rectF.top = 1 + currentBorderWidth;
        rectF.bottom = 1 + mBound.height() + tagBorderPaddingTop + tagBorderPaddingBottom + currentBorderWidth * 2;
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
                if (tagType == 1) {
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
                }
                break;
        }
        reload();
        return false;
    }

    public void reload() {
        currentTitleString = currentTitleString == null ? tagTitleText : currentTitleString;
        if (currentTitleString == null) {
            currentTitleString = " ";
        }

        // 设置圆角或直角
        if (tagBorderType == 1) {
            tagBorderRadius = 0;
        }
        // 设置是否为选中状态
        if (tagIsSelected) {
            currentTitleString = tagSelectedTitleText == null ? tagTitleText : tagSelectedTitleText;
            currentTitleColor = tagSelectedTitleColor;
            currentBackgroundColor = tagSelectedBackgroundColor;
            currentBorderColor = tagSelectedBorderColor;
            currentBorderWidth = tagSelectedBorderWidth;
        } else {
            currentTitleString = tagTitleText;
            currentTitleColor = tagTitleColor;
            currentBackgroundColor = tagBackgroundColor;
            currentBorderColor = tagBorderColor;
            currentBorderWidth = tagBorderWidth;
        }
        if (tagOnClickTitleText == null) {
            tagOnClickTitleText = currentTitleString;
        }
        if (tagSelectedTitleText == null) {
            tagSelectedTitleText = currentTitleString;
        }
        requestLayout();
        invalidate();
    }

    public Boolean isTagSelected() {
        return tagIsSelected;
    }

    public void setTagSelected(Boolean selected) {
        tagIsSelected = selected;
    }

    public String getTagTitleText() {
        return tagTitleText;
    }

    public void setTagTitleText(String tagTitleText) {
        this.tagTitleText = tagTitleText;
        textSet(true);
        reload();
    }

    public int getTagTitleTextSize() {
        return tagTitleTextSize;
    }

    public void setTagTitleTextSize(int tagTitleTextSize) {
        this.tagTitleTextSize = tagTitleTextSize;
    }

    public int getTagTitleColor() {
        return tagTitleColor;
    }

    public void setTagTitleColor(int tagTitleColor) {
        this.tagTitleColor = tagTitleColor;
    }

    public int getTagBackgroundColor() {
        return tagBackgroundColor;
    }

    public void setTagBackgroundColor(int tagBackgroundColor) {
        this.tagBackgroundColor = tagBackgroundColor;
    }

    public int getTagBorderWidth() {
        return tagBorderWidth;
    }

    public void setTagBorderWidth(int tagBorderWidth) {
        this.tagBorderWidth = tagBorderWidth;
    }

    public int getTagBorderColor() {
        return tagBorderColor;
    }

    public void setTagBorderColor(int tagBorderColor) {
        this.tagBorderColor = tagBorderColor;
    }

    public int getTagBorderPaddingTop() {
        return tagBorderPaddingTop;
    }

    public void setTagBorderPaddingTop(int tagBorderPaddingTop) {
        this.tagBorderPaddingTop = tagBorderPaddingTop;
    }

    public int getTagBorderPaddingLeft() {
        return tagBorderPaddingLeft;
    }

    public void setTagBorderPaddingLeft(int tagBorderPaddingLeft) {
        this.tagBorderPaddingLeft = tagBorderPaddingLeft;
    }

    public int getTagBorderPaddingBottom() {
        return tagBorderPaddingBottom;
    }

    public void setTagBorderPaddingBottom(int tagBorderPaddingBottom) {
        this.tagBorderPaddingBottom = tagBorderPaddingBottom;
    }

    public int getTagBorderPaddingRight() {
        return tagBorderPaddingRight;
    }

    public void setTagBorderPaddingRight(int tagBorderPaddingRight) {
        this.tagBorderPaddingRight = tagBorderPaddingRight;
    }

    public int getTagBorderRadius() {
        return tagBorderRadius;
    }

    public void setTagBorderRadius(int tagBorderRadius) {
        this.tagBorderRadius = tagBorderRadius;
    }

    public int getTagBorderType() {
        return tagBorderType;
    }

    public void setTagBorderType(int tagBorderType) {
        this.tagBorderType = tagBorderType;
    }

    public String getTagOnClickTitleText() {
        return tagOnClickTitleText;
    }

    public void setTagOnClickTitleText(String tagOnClickTitleText) {
        this.tagOnClickTitleText = tagOnClickTitleText;
        textSet(true);
        reload();
    }

    public int getTagOnClickTitleColor() {
        return tagOnClickTitleColor;
    }

    public void setTagOnClickTitleColor(int tagOnClickTitleColor) {
        this.tagOnClickTitleColor = tagOnClickTitleColor;
    }

    public int getTagOnClickBackgroundColor() {
        return tagOnClickBackgroundColor;
    }

    public void setTagOnClickBackgroundColor(int tagOnClickBackgroundColor) {
        this.tagOnClickBackgroundColor = tagOnClickBackgroundColor;
    }

    public int getTagOnClickBorderWidth() {
        return tagOnClickBorderWidth;
    }

    public void setTagOnClickBorderWidth(int tagOnClickBorderWidth) {
        this.tagOnClickBorderWidth = tagOnClickBorderWidth;
    }

    public int getTagOnClickBorderColor() {
        return tagOnClickBorderColor;
    }

    public void setTagOnClickBorderColor(int tagOnClickBorderColor) {
        this.tagOnClickBorderColor = tagOnClickBorderColor;
    }

    public String getTagSelectedTitleText() {
        return tagSelectedTitleText;
    }

    public void setTagSelectedTitleText(String tagSelectedTitleText) {
        this.tagSelectedTitleText = tagSelectedTitleText;
        textSet(true);
        reload();
    }

    public int getTagSelectedTitleColor() {
        return tagSelectedTitleColor;
    }

    public void setTagSelectedTitleColor(int tagSelectedTitleColor) {
        this.tagSelectedTitleColor = tagSelectedTitleColor;
    }

    public int getTagSelectedBackgroundColor() {
        return tagSelectedBackgroundColor;
    }

    public void setTagSelectedBackgroundColor(int tagSelectedBackgroundColor) {
        this.tagSelectedBackgroundColor = tagSelectedBackgroundColor;
    }

    public int getTagSelectedBorderWidth() {
        return tagSelectedBorderWidth;
    }

    public void setTagSelectedBorderWidth(int tagSelectedBorderWidth) {
        this.tagSelectedBorderWidth = tagSelectedBorderWidth;
    }

    public int getTagSelectedBorderColor() {
        return tagSelectedBorderColor;
    }

    public void setTagSelectedBorderColor(int tagSelectedBorderColor) {
        this.tagSelectedBorderColor = tagSelectedBorderColor;
    }

    public boolean isTagIsSelected() {
        return tagIsSelected;
    }

    public void setTagIsSelected(boolean tagIsSelected) {
        this.tagIsSelected = tagIsSelected;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public Rect getmBound() {
        return mBound;
    }

    public void setmBound(Rect mBound) {
        this.mBound = mBound;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }
}
