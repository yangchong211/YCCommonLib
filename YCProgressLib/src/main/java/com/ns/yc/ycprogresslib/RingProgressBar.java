package com.ns.yc.ycprogresslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/6/18
 * 描    述：自定义进度条
 * 修订历史：参考案例：夏安明博客http://blog.csdn.net/xiaanming/article/details/10298163
 * ================================================
 */
@SuppressWarnings("unused")
public class RingProgressBar extends View{

    private int progress;                       //当前进度
    private int percent;                        //当前百分比
    private int progressMax;                    //最大进度
    private int dotColor;                       //颜色
    private int dotBgColor;                     //背景颜色
    private int showMode;                       //进度条的样式
    public static final int SHOW_MODE_NULL = 0;
    public static final int SHOW_MODE_PERCENT = 1;
    public static final int SHOW_MODE_ALL  = 2;

    private float percentTextSize;              //百分比字体大小
    private int percentTextColor;               //百分比字体颜色
    private boolean isPercentFontSystem;        //是否是系统字体样式
    private int percentThinPadding;

    private String unitText;
    private float unitTextSize;
    private int unitTextColor;
    private int unitTextAlignMode;
    public static final int UNIT_TEXT_ALIGN_MODE_DEFAULT = 0;
    public static final int UNIT_TEXT_ALIGN_MODE_CN = 1;
    public static final int UNIT_TEXT_ALIGN_MODE_EN      = 2;

    private String buttonText;
    private float buttonTextSize;
    private int buttonTextColor;
    private int buttonBgColor;
    private int buttonClickColor;
    private int buttonClickBgColor;
    private float buttonTopOffset;


    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    private float mSin_1;                       // sin(1°)
    private boolean mIsButtonTouched;           // 按钮是否被触摸到
    private Typeface mPercentTypeface;

    private PointF mButtonRect_start;           // 按钮背景的方块左上角开始点
    private PointF mButtonRect_end;             // 按钮背景的方块右下角结束点
    private float mButtonRadius;

    private OnClickListener mButtonClickListener; // 按钮点击事件
    private float mCenterX;
    private float mCenterY;

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Xfermode mClearCanvasXfermode;
    private Xfermode mPercentThinXfermode;

    public RingProgressBar(Context context) {
        this(context, null);
    }

    public RingProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RingProgressBar);

        // 获取自定义属性值或默认值
        progressMax = ta.getInteger(R.styleable.RingProgressBar_progressMax, 100);
        dotColor = ta.getColor(R.styleable.RingProgressBar_dotColor, Color.WHITE);
        dotBgColor = ta.getColor(R.styleable.RingProgressBar_dotBgColor, Color.GRAY);
        showMode = ta.getInt(R.styleable.RingProgressBar_showMode, SHOW_MODE_PERCENT);

        if (showMode != SHOW_MODE_NULL) {
            percentTextSize = ta.getDimension(R.styleable.RingProgressBar_percentTextSize, dp2px(30));
            percentTextColor = ta.getInt(R.styleable.RingProgressBar_percentTextColor, Color.WHITE);
            isPercentFontSystem = ta.getBoolean(R.styleable.RingProgressBar_isPercentFontSystem, false);
            percentThinPadding = ta.getInt(R.styleable.RingProgressBar_percentThinPadding, 0);

            unitText = ta.getString(R.styleable.RingProgressBar_unitText);
            unitTextSize = ta.getDimension(R.styleable.RingProgressBar_unitTextSize, percentTextSize);
            unitTextColor = ta.getInt(R.styleable.RingProgressBar_unitTextColor, Color.WHITE);
            unitTextAlignMode = ta.getInt(R.styleable.RingProgressBar_unitTextAlignMode, UNIT_TEXT_ALIGN_MODE_DEFAULT);

            if (unitText == null) {
                unitText = "%";
            }
        }

        if (showMode == SHOW_MODE_ALL) {
            buttonText = ta.getString(R.styleable.RingProgressBar_buttonText);
            buttonTextSize = ta.getDimension(R.styleable.RingProgressBar_buttonTextSize, dp2px(15));
            buttonTextColor = ta.getInt(R.styleable.RingProgressBar_buttonTextColor, Color.GRAY);
            buttonBgColor = ta.getInt(R.styleable.RingProgressBar_buttonBgColor, Color.WHITE);
            buttonClickColor = ta.getInt(R.styleable.RingProgressBar_buttonClickColor, buttonBgColor);
            buttonClickBgColor = ta.getInt(R.styleable.RingProgressBar_buttonClickBgColor, buttonTextColor);
            buttonTopOffset = ta.getDimension(R.styleable.RingProgressBar_buttonTopOffset, dp2px(15));
            if (buttonText == null) {
                buttonText = context.getString(R.string.progress_btn);
            }
        }
        ta.recycle();

        // 其他准备工作
        mSin_1 = (float) Math.sin(Math.toRadians(1)); // 求sin(1°)。角度需转换成弧度
        mPaint = new Paint();
        mPaint.setAntiAlias(true);      // 消除锯齿
        mPath = new Path();
        mRectF = new RectF();
        mButtonRect_start = new PointF();
        mButtonRect_end = new PointF();

        //百分比字体类型，设置成默认值
        mPercentTypeface = Typeface.DEFAULT;

        mClearCanvasXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        if (percentThinPadding != 0) {
            mPercentThinXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制外围圆点进度
        drawCircleDot(mCanvas);

        if (showMode != SHOW_MODE_NULL) {
            // 绘制百分比+单位
            drawPercentUnit(mCanvas);
            if (showMode == SHOW_MODE_ALL) {
                // 绘制按钮
                drawButton(mCanvas);
            }
        }

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制圆点进度
     * @param canvas 画布
     */
    private void drawCircleDot(Canvas canvas) {
        // 先清除上次绘制的
        mPaint.setXfermode(mClearCanvasXfermode);
        mCanvas.drawPaint(mPaint);
        mPaint.setXfermode(null);

        // 计算圆点半径
        float outerRadius = (getWidth() < getHeight() ? getWidth() : getHeight()) / 2f;
        mCenterX = getWidth() / 2f;
        mCenterY = getHeight() / 2f;

        // outerRadius = innerRadius + dotRadius
        // sin((360°/200)/2) = sin(0.9°) = dotRadius / innerRadius;
        // 为了让圆点饱满一些，把角度0.9°增加0.1°到1°
        float dotRadius = mSin_1 * outerRadius / (1 + mSin_1);


        // 画进度
        mPaint.setColor(dotColor);
        mPaint.setStyle(Paint.Style.FILL);
        int count = 0;
        // 1.1 当前进度
        while (count++ < percent) {
            mCanvas.drawCircle(mCenterX, mCenterY - outerRadius + dotRadius, dotRadius, mPaint);
            mCanvas.rotate(3.6f, mCenterX, mCenterY);
        }
        // 1.2 未完成进度
        mPaint.setColor(dotBgColor);
        count--;
        while (count++ < 100) {
            mCanvas.drawCircle(mCenterX, mCenterY - outerRadius + dotRadius, dotRadius, mPaint);
            mCanvas.rotate(3.6f, mCenterX, mCenterY);
        }
    }

    /**
     * 绘制百分比和单位
     * @param canvas 画布
     */
    private void drawPercentUnit(Canvas canvas) {
        // 测量百分比和单位的宽度
        mPaint.setTypeface(mPercentTypeface);
        mPaint.setTextSize(percentTextSize);
        float percentTextWidth = mPaint.measureText(percent + "");

        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(unitTextSize);
        float unitTextWidth = mPaint.measureText(unitText);
        Paint.FontMetrics fm_unit = mPaint.getFontMetrics();

        float textWidth = percentTextWidth + unitTextWidth;

        float baseline = 0;
        if (showMode == SHOW_MODE_PERCENT) {
            float textHeight = percentTextSize > unitTextSize ? percentTextSize : unitTextSize;
            // 计算Text垂直居中时的baseline
            mPaint.setTextSize(textHeight);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            // 字体在垂直居中时，字体中间就是centerY，加上字体实际高度的一半就是descent线，减去descent就是baseline线的位置（fm中以baseline为基准）
            baseline = mCenterY + (fm.descent - fm.ascent)/2 - fm.descent;
        }else if (showMode == SHOW_MODE_ALL) {
            baseline = mCenterY;
        }

        // 2.1 画百分比
        mPaint.setTypeface(mPercentTypeface);
        mPaint.setTextSize(percentTextSize);
        mPaint.setColor(percentTextColor);
        canvas.drawText(percent + "", mCenterX - textWidth / 2, baseline, mPaint);

        // 2.1.1 对百分比瘦身
        if (percentThinPadding != 0) {
            // 使用橡皮擦擦除
            mPaint.setXfermode(mPercentThinXfermode);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(percentThinPadding);
            canvas.drawText(percent + "", mCenterX - textWidth / 2, baseline, mPaint);
            mPaint.setXfermode(null);
            mPaint.setStyle(Paint.Style.FILL);
        }

        // 2.2 画单位
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(unitTextSize);
        mPaint.setColor(unitTextColor);
        // 单位对齐方式
        switch (unitTextAlignMode) {
            case UNIT_TEXT_ALIGN_MODE_CN:
                baseline -= fm_unit.descent / 4;
                break;
            case UNIT_TEXT_ALIGN_MODE_EN:
                baseline -= fm_unit.descent * 2/3;
                break;
            default:
                break;
        }
        canvas.drawText(unitText, mCenterX - textWidth / 2 + percentTextWidth, baseline, mPaint);
    }

    /**
     * 绘制按钮
     * @param canvas 画布
     */
    private void drawButton(Canvas canvas) {
        // 3 画按钮
        mPaint.setTextSize(buttonTextSize);
        float buttonTextWidth = mPaint.measureText(buttonText);
        Paint.FontMetrics fm = mPaint.getFontMetrics();

        // 3.1 画按钮背景
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mIsButtonTouched ? buttonClickBgColor : buttonBgColor);
        float buttonHeight = 2 * buttonTextSize;
        mButtonRadius = buttonHeight / 2;

        mButtonRect_start.set(mCenterX - buttonTextWidth / 2, mCenterY + buttonTopOffset);
        mButtonRect_end.set(mCenterX + buttonTextWidth / 2, mCenterY + buttonTopOffset + buttonHeight);

        mPath.reset();
        mPath.moveTo(mButtonRect_start.x, mButtonRect_start.y);
        mPath.rLineTo(buttonTextWidth, 0);
        float left = mCenterX + buttonTextWidth / 2 - mButtonRadius;
        float top = mCenterY + buttonTopOffset;
        float right = left + 2 * mButtonRadius;
        float bottom = top + 2 * mButtonRadius;
        mRectF.set(left, top, right, bottom);
        mPath.arcTo(mRectF, 270, 180); // 参数1：内切这个方形，参数2：开始角度，参数3：画的角度范围
        mPath.rLineTo(-buttonTextWidth, 0);
        mRectF.offset(-buttonTextWidth, 0); // 平移位置
        mPath.arcTo(mRectF, 90, 180);
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        // 3.2 画按钮文本
        mPaint.setColor(mIsButtonTouched ? buttonClickColor : buttonTextColor);
        float baseline = mCenterY + buttonTopOffset + buttonTextSize + (fm.descent - fm.ascent) / 2 - fm.descent;
        canvas.drawText(buttonText, mCenterX - buttonTextWidth / 2, baseline, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (showMode == SHOW_MODE_ALL) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isTouchInButton(event.getX(), event.getY())) {
                        mIsButtonTouched = true;
                        postInvalidate();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mIsButtonTouched) {
                        if (!isTouchInButton(event.getX(), event.getY())) {
                            mIsButtonTouched = false;
                            postInvalidate();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mIsButtonTouched && mButtonClickListener != null) {
                        mButtonClickListener.onClick(this);
                    }
                    mIsButtonTouched = false;
                    postInvalidate();
                    break;
                default:
                    break;
            }
            if (mIsButtonTouched) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 判断坐标是否在按钮中
     * @param x 坐标的x
     * @param y 坐标的y
     * @return true-在按钮中，false-不在按钮中
     */
    private boolean isTouchInButton(final float x, final float y) {
        // 判断是否在按钮矩形中
        if (x >= mButtonRect_start.x && x <= mButtonRect_end.x
                && y >= mButtonRect_start.y && y <= mButtonRect_end.y) {
            return true;
        }

        // 判断是否在左边半圆中：另一半圆在矩形中，也属于按钮范围，所以直接判断整个圆即可
        // 把坐标系移到圆心再判断
        float centerX = mButtonRect_start.x;
        float centerY = (mButtonRect_start.y + mButtonRect_end.y) / 2;
        float newX = x - centerX;
        float newY = y - centerY;
        if (newX * newX + newY * newY <= mButtonRadius * mButtonRadius) {
            return true;
        }

        // 判断是否在右边半圆中
        centerX = mButtonRect_end.x;
        newX = x - centerX;
        return newX * newX + newY * newY <= mButtonRadius * mButtonRadius;
    }

    /**
     * 设置按钮点击事件
     * @param onClickListener 点击事件
     */
    public void setOnButtonClickListener(OnClickListener onClickListener) {
        mButtonClickListener = onClickListener;
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + .5f);
    }


    /**
     * Setter and Getter
     */

    public synchronized int getProgressMax() {
        return progressMax;
    }

    public synchronized void setProgressMax(int progressMax) {
        if (progressMax < 0) {
            throw new IllegalArgumentException("progressMax mustn't smaller than 0");
        }
        this.progressMax = progressMax;
    }

    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度
     * 同步，允许多线程访问
     * @param progress 进度
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0 || progress > progressMax) {
            throw new IllegalArgumentException(String.format(getResources().getString(R.string.progress_out_of_range), progressMax));
        }
        this.progress = progress;
        percent = progress * 100 / progressMax;
        postInvalidate();       // 可以直接在子线程中调用，而invalidate()必须在主线程（UI线程）中调用
    }


    public int getPercent() {
        return percent;
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
    }

    public int getDotBgColor() {
        return dotBgColor;
    }

    public void setDotBgColor(int dotBgColor) {
        this.dotBgColor = dotBgColor;
    }

    public int getShowMode() {
        return showMode;
    }

    public void setShowMode(int showMode) {
        this.showMode = showMode;
    }

    public float getPercentTextSize() {
        return percentTextSize;
    }

    public void setPercentTextSize(float percentTextSize) {
        this.percentTextSize = percentTextSize;
    }

    public int getPercentTextColor() {
        return percentTextColor;
    }

    public void setPercentTextColor(int percentTextColor) {
        this.percentTextColor = percentTextColor;
    }

    public boolean isPercentFontSystem() {
        return isPercentFontSystem;
    }

    public void setIsPercentFontSystem(boolean isPercentFontSystem) {
        this.isPercentFontSystem = isPercentFontSystem;
    }

    public int getPercentThinPadding() {
        return percentThinPadding;
    }

    public void setPercentThinPadding(int percentThinPadding) {
        this.percentThinPadding = percentThinPadding;
    }

    public String getUnitText() {
        return unitText;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public float getUnitTextSize() {
        return unitTextSize;
    }

    public void setUnitTextSize(float unitTextSize) {
        this.unitTextSize = unitTextSize;
    }

    public int getUnitTextColor() {
        return unitTextColor;
    }

    public void setUnitTextColor(int unitTextColor) {
        this.unitTextColor = unitTextColor;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public float getButtonTextSize() {
        return buttonTextSize;
    }

    public void setButtonTextSize(float buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
    }

    public int getButtonTextColor() {
        return buttonTextColor;
    }

    public void setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }

    public int getButtonBgColor() {
        return buttonBgColor;
    }

    public void setButtonBgColor(int buttonBgColor) {
        this.buttonBgColor = buttonBgColor;
    }

    public int getButtonClickColor() {
        return buttonClickColor;
    }

    public void setButtonClickColor(int buttonClickColor) {
        this.buttonClickColor = buttonClickColor;
    }

    public int getButtonClickBgColor() {
        return buttonClickBgColor;
    }

    public void setButtonClickBgColor(int buttonClickBgColor) {
        this.buttonClickBgColor = buttonClickBgColor;
    }

    public int getUnitTextAlignMode() {
        return unitTextAlignMode;
    }

    public void setUnitTextAlignMode(int unitTextAlignMode) {
        this.unitTextAlignMode = unitTextAlignMode;
    }

    public float getButtonTopOffset() {
        return buttonTopOffset;
    }

    public void setButtonTopOffset(float buttonTopOffset) {
        this.buttonTopOffset = buttonTopOffset;
    }
}
