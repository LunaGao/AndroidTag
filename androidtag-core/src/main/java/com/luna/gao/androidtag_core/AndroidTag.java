package com.luna.gao.androidtag_core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lunagao on 16/5/17.
 */
public class AndroidTag extends View {

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
     * 获得我自定义的样式属性
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
        // Load styled attributes.
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

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mBound = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
        mPaint.getTextBounds(tagTitleText, 0, tagTitleText.length(), mBound);

        setBackground(canvas, tagBackgroundColor);
        setBorderColor(canvas, tagBorderColor);
        setTitle(canvas, tagTitleColor, tagTitleText);
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

//        mPaint.setColor(tagTitleColor);
//        canvas.drawText(tagTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    private void setBackground(Canvas canvas, int color) {
        drawBackgroundRoundRect(Paint.Style.FILL, canvas, color);
    }

    private void setBorderColor(Canvas canvas, int color) {
        drawBackgroundRoundRect(Paint.Style.STROKE, canvas, color);
    }

    private void setTitle(Canvas canvas, int color, String titleString) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        canvas.drawText(titleString, tagBorderPaddingTop, tagBorderPaddingLeft + mBound.height(), mPaint);
    }

    private void drawBackgroundRoundRect(Paint.Style style, Canvas canvas, int color) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(tagBorderWidth);
        mPaint.setStyle(style);
        //新建矩形r2
        RectF rectF = new RectF();
        rectF.left = 1;
        rectF.right = 1 + mBound.width() + tagBorderPaddingRight + tagBorderPaddingLeft;
        rectF.top = 1 ;
        rectF.bottom = 1 + mBound.height() + tagBorderPaddingTop + tagBorderPaddingBottom;
        canvas.drawRoundRect(rectF, tagBorderRadius, tagBorderRadius, mPaint);
    }
}
