package com.shufa.easylayout.easylayout.chart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shufa.easylayout.easylayout.R;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class CustomChat extends View {

    //-------------必须给的数据相关-------------
    private String[] str = new String[]{"一年级", "二年级", "三年级", "四年级", "五年级", "六年级"};
    //分配比例大小，总比例大小为100
    private int[] strPercent = new int[]{10, 25, 18, 41, 2, 5};
    //圆的直径
    private float mRadius = 300;
    //圆的粗细
    private float mStrokeWidth = 40;
    //最外层圆径直径
    private int mBigRadius = 450;
    //最外层圆粗细
    private float mBigStrokeWidth = 10;
    //小圆直径
    private int[] mSmallRadius = new int[]{10, 25, 18, 41, 2, 5};

    //文字大小
    private int textSize = 20;
    //-------------画笔相关-------------
    //圆环的画笔
    private Paint cyclePaint;
    //最外层圆环
    private Paint bigCyclePaint;
    //小圆球
    private Paint smallCyclePaint;
    //文字的画笔
    private Paint textPaint;
    //标注的画笔
    private Paint labelPaint;
    //-------------颜色相关-------------
    //边框颜色和标注颜色
    private int[] mColor = new int[]{0xFFF06292, 0xFF9575CD, 0xFFE57373, 0xFF4FC3F7, 0xFFFFF176, 0xFF81C784};
    //文字颜色
    private int textColor = 0xFF000000;
    private int bigCycleColor = 0xAADDCC00;
    //-------------View相关-------------
    //View自身的宽和高
    private int mHeight;
    private int mWidth;

    private Paint mBitPaint;
    private Bitmap mBitmap;
    private int girlBitWidth , girlBitHeight;

    public CustomChat(Context context) {
        super(context);
    }

    public CustomChat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBitmap(context);
    }

    public CustomChat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

    }

    private void initBitmap(Context context) {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        girlBitWidth = mBitmap.getWidth();
        girlBitHeight = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //移动画布到圆环的左上角
        canvas.translate(mWidth / 2 - mRadius / 2, mHeight / 2 - mRadius / 2);
        //初始化画笔
        initPaint();
        //画圆环
        drawCycle(canvas);
        //画大圆环
        drawBigCycle(canvas);
        //画小圆
        drawSmallCycle(canvas);
        //画文字和标注
        drawTextAndLabel(canvas);

        drawPicture(canvas);

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setStrokeWidth(mStrokeWidth);
        //最外层边框画笔
        bigCyclePaint = new Paint();
        bigCyclePaint.setAntiAlias(true);
        //空心
        bigCyclePaint.setStyle(Paint.Style.STROKE);
        bigCyclePaint.setColor(bigCycleColor);
        bigCyclePaint.setStrokeWidth(mBigStrokeWidth);
        //小圆画笔
        smallCyclePaint = new Paint();
        smallCyclePaint.setAntiAlias(true);
        smallCyclePaint.setStrokeWidth(mBigStrokeWidth);

        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(textSize);
        //标注画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setStrokeWidth(2);

        //图像画笔
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }

    /**
     * 画图片
     * @param canvas
     */
    private void drawPicture(Canvas canvas) {
        //画中间的图
        canvas.drawBitmap(mBitmap, mRadius / 2 - girlBitWidth / 2, mRadius / 2 - girlBitHeight / 2, mBitPaint);
    }


    /**
     * 画圆环
     *
     * @param canvas
     */
    private void drawCycle(Canvas canvas) {
        float startPercent=0;
        float sweepPercent=0;
        for(int i = 0;i < strPercent.length; i++){
            cyclePaint.setColor(mColor[i]);
            startPercent = sweepPercent + startPercent;
//            if(i<3){
//                sweepPercent= (float) ((strPercent[i] + 2.5) * 360 / 100);
//            }else{
                sweepPercent=(strPercent[i]) * 360 / 100;

//            }
            Log.d("radius", startPercent + "  jieshu:" + sweepPercent);
            canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent,false,cyclePaint);
        }
    }

    /**
     * 画小圆
     * @param canvas
     */
    private void drawSmallCycle(Canvas canvas) {
        //圆环的角度
        float startPercent = 0;
        float sweepPercent = 0;
        float cx = 0;
        float cy = 0;
        for(int i = 0;i < strPercent.length;i++){
            smallCyclePaint.setColor(mColor[i]);
            startPercent = sweepPercent + startPercent;
//            if(i<3){
//                sweepPercent= (float) ((strPercent[i] + 2.5) *360 / 100);
//            }else{
                sweepPercent=(strPercent[i]) * 360 / 100;
//            }
            if (startPercent == 0){
                cx = (float)((mRadius / 2) + (mBigRadius / 2));
                cy = (float)(mRadius / 2);
            }else if (startPercent > 0 && startPercent <= 90){
                cx = (float) (mRadius / 2 + (mBigRadius / 2) * Math.sin(startPercent + 90));
                cy = (float) (mRadius / 2 + (mBigRadius / 2) * Math.cos(startPercent + 90));
            }else if (startPercent > 90 && startPercent <= 180){
                cx = (float) (mRadius / 2 + (mBigRadius / 2) * Math.sin(270 - startPercent - 90));
                cy = (float) (mRadius / 2 - (mBigRadius / 2) * Math.cos(270 - startPercent - 90));
                Log.d("radius", "sin------" + Math.sin(270 - startPercent - 90));
                Log.d("radius", "cos------" + Math.cos(270 - startPercent - 90));
            }else if (startPercent >= 180 && startPercent <= 270){
                cx = (float) (mRadius / 2 - (mBigRadius / 2) * Math.cos(startPercent));
                cy = (float) (mRadius / 2 - (mBigRadius / 2) * Math.sin(startPercent));
            }else {
                cx = (float) (mRadius / 2 - (mBigRadius / 2) * Math.sin(90 - startPercent));
                cy = (float) (mRadius / 2 + (mBigRadius / 2) * Math.cos(90 - startPercent));
            }
            Log.d("radius", "radius: " + mRadius / 2);
            Log.d("radius", "drawSmallCycle: " + cx + ":" + cy);
            canvas.drawCircle(cx, cy, strPercent[i], smallCyclePaint);
        }
    }

    /**
     * 画外层的大圆环
     * @param canvas
     */
    private void drawBigCycle(Canvas canvas) {
        canvas.drawCircle(mRadius / 2, mRadius / 2, mBigRadius / 2, bigCyclePaint);
    }

    /**
     * 画文字和标注
     *
     * @param canvas
     */
    private void drawTextAndLabel(Canvas canvas) {
        for (int i = 0; i < strPercent.length; i++) {
            //文字离右边环边距为60，文字与文字之间的距离为40
            canvas.drawText(str[i], mRadius + 140, i * 40, textPaint);
            //画标注,标注离右边环边距为40,y轴则要减去半径（10）的一半才能对齐文字
            labelPaint.setColor(mColor[i]);
            canvas.drawCircle(mRadius + 120, i * 40 - 5, 10, labelPaint);
        }
    }
}
