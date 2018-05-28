package com.shufa.easylayout.easylayout.fiveView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shufa.easylayout.easylayout.R;

/**
 * 五边形统计图
 * Created by Administrator on 2018/5/21 0021.
 */

public class CustomView extends View {

    private int dataCount = 5;//多边形维度
    private float radian = (float) (Math.PI * 2 / dataCount);//每个维度的角度
    private float radius;//一条星射线的长度，即是发散的五条线白线
    private int centerX;//中心坐标 Y
    private int centerY;//中心坐标 X
    private String[] titles = {"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质"};//标题
    private int[] icos = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};//五个维度的图标
    private float[] data;//五个维度的数据值
    private float maxValue = 360;//每个维度的最大值
    private Paint mPaintText;//绘制文字的画笔
    private int radarMargin = 20;//
    private Path mPentagonPath;//记录白色五边形的路径
    private Paint mPentagonPaint;//绘制白色五边形的画笔

    private Path mDataPath;//记录红色五边形的路径
    private Paint mDataPaint;//绘制红色五边形的画笔

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化红色五边形的画笔
        mDataPaint = new Paint();
        mDataPaint.setAntiAlias(true);
        mDataPaint.setStrokeWidth(10);
        mDataPaint.setColor(Color.RED);
        mDataPaint.setAlpha(150);//
        mDataPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //初始化文字画笔
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(20);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setStyle(Paint.Style.FILL);
        //初始化白色五边形画笔
        mPentagonPaint = new Paint();
        mPentagonPaint.setAntiAlias(true);
        mPentagonPaint.setStrokeWidth(5);
        mPentagonPaint.setColor(Color.WHITE);
        mPentagonPaint.setStyle(Paint.Style.STROKE);
        //初始化白色五边形路径
        mPentagonPath = new Path();
        mDataPath = new Path();//初始化红色五边形路径
        //星射线的初始值，最小五边形的一条星射线的长度（后期会递增）
        radius = 80;
    }

    public Point getPoint(int position){
        return getPoint(position, 0, 1);
    }

    /**
     * 计算五边形顶点的坐标值
     * @param position
     * @param radarMargin
     * @param percent
     * @return
     */
    private Point getPoint(int position, int radarMargin, float percent) {

        int x = 0;
        int y = 0;
        switch (position){
            case 0: //第一象限右上角顶点坐标
                x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
                y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);
                break;
            case 1: //第四象限右下角顶点坐标
                x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
                y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);
                break;
            case 2: //第三象限左下角顶点坐标
                x = (int) (centerX - (radius + radarMargin) *  Math.sin(radian / 2) * percent);
                y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);
                break;
            case 3: //第二象限左上角顶点坐标
                x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
                y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);
                break;
            case 4: //y轴正方向顶点的计算
                x = centerX;
                y = (int) (centerY - (radius + radarMargin) * percent);
                break;
        }
        return new Point(x, y);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        draPentagon(canvas);
        drawTitle(canvas);
    }

    /**
     * 绘制文字标题
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {

        for (int i = 0; i < dataCount; i++) {
            //获取添加radarMargin值之后的x坐标的值
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icos[i]);
            int iconHeight = bitmap.getHeight();
            int titleWidth = (int) mPaintText.measureText(titles[i]);
            switch (i){
                case 1:
                    y += iconHeight / 4;
                    break;
                case 2:
                    x -= titleWidth;
                    y += iconHeight / 4;
                    break;
                case 3:
                    x -= titleWidth;
                    break;
                case 4:
                    x -= titleWidth / 2;
                   break;
            }
            canvas.drawText(titles[i], x, y, mPaintText);
        }

    }

    /**
     * 绘制五边形
     * @param canvas
     */
    private void draPentagon(Canvas canvas) {
        //绘制两层五边形
        for (int j = 0; j < 2; j++) {//绘制三层白色五边形
            radius += 40;//每一层五边形的星射线增加 70 的长度
            for (int i = 0; i < dataCount; i++) {//绘制一层
                if (i == 0) {
                    mPentagonPath.moveTo(getPoint(i).x, getPoint(i).y);
                } else {
                    mPentagonPath.lineTo(getPoint(i).x, getPoint(i).y);
                }
            }
            mPentagonPath.close();
            canvas.drawPath(mPentagonPath, mPentagonPaint);
        }

        for (int i = 0; i < dataCount; i++) {//绘制红色五边形
            float percent = getData()[i] / maxValue;//数据值与最大值的百分比
            if (i == 0) {
                mDataPath.moveTo(getPoint(i, 0, percent).x, getPoint(i, 0, percent).y);//通过百分比计算出红色顶点的位置
            } else {
                mDataPath.lineTo(getPoint(i, 0, percent).x, getPoint(i, 0, percent).y);
            }
        }
        mDataPath.close();
        canvas.drawPath(mDataPath, mDataPaint);
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }
}
