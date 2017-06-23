package com.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/6/23.
 */

public class HSelectView extends View {
    private Context context;
    private float selectedTextSize;
    private int selectedColor;
    private float textSize;
    private int textColor;
    private int seeSize = 5;//可见个数
    private TextPaint textPaint;
    private TextPaint selectedPaint;
    private int anInt;//每个字母所占的大小；
    private int width;//控件宽度
    private int height;//控件高度
    private int num;
    private List<String> dataList;//数据源字符串数组
    private Rect rect = new Rect();
    private boolean firstVisible = true;
    private float anOffset;
    private int textWidth = 0;
    private int textHeight = 0;
    private int centerTextHeight = 0;
    private float downX;

    public HSelectView(Context context) {
        this(context, null);
    }

    public HSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setWillNotDraw(false);
        setClickable(true);
        //初始化属性
        initAttrs(attrs);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);

        selectedPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(selectedColor);
        selectedPaint.setTextSize(selectedTextSize);
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.HSelectView);
        //两种字体颜色和字体大小
        seeSize = tta.getInteger(R.styleable.HSelectView_seesize, 5);
        selectedTextSize = tta.getFloat(R.styleable.HSelectView_SelectedTextSize, 50);
        selectedColor = tta.getColor(R.styleable.HSelectView_SelectedTextColor, context.getResources().getColor(android.R.color.black));
        textSize = tta.getFloat(R.styleable.HSelectView_TextSize, 40);
        textColor = tta.getColor(R.styleable.HSelectView_TextColor, context.getResources().getColor(android.R.color.darker_gray));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //第一次绘制的时候得到控件 宽高；
        if (firstVisible) {
            width = getWidth();
            height = getHeight();
            anInt = width / seeSize;
            firstVisible = false;
        }

        //加个保护；防止越界
        if (num < 0 || num >= dataList.size()) {
            return;
        }
        String str = dataList.get(num);//得到被选中的文字
        /**
         * 得到被选中文字 绘制时所需要的宽高
         */
        selectedPaint.getTextBounds(str, 0, str.length(), rect);
        //3从矩形区域中读出文本内容的宽高
        int centerTextWidth = rect.width();
        centerTextHeight = rect.height();
        //绘制被选中文字，注意点是y坐标
        canvas.drawText(dataList.get(num), width / 2 - centerTextWidth / 2 + anOffset,
                height / 2 + centerTextHeight / 2, selectedPaint);
        //遍历strings，把每个地方都绘制出来
        for (int i = 0; i < dataList.size(); i++) {
            //这里主要是因为strings数据源的文字长度不一样，为了让被选中两边文字距离中心宽度一样，我们取得左右两个文字长度的平均值
            if (num > 0 && num < dataList.size() - 1) {
                textPaint.getTextBounds(dataList.get(num - 1), 0, dataList.get(num - 1).length(), rect);
                int width1 = rect.width();
                textPaint.getTextBounds(dataList.get(num + 1), 0, dataList.get(num + 1).length(), rect);
                int width2 = rect.width();
                textWidth = (width1 + width2) / 2;
            }
            if (i == 0) {//得到高，高度是一样的，所以无所谓
                textPaint.getTextBounds(dataList.get(0), 0, dataList.get(0).length(), rect);
                textHeight = rect.height();
            }

            if (i != num) {
                canvas.drawText(dataList.get(i), (i - num) * anInt + width / 2 - textWidth / 2 + anOffset,
                        height / 2 + textHeight / 2, textPaint);//画出每组文字
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();//获得点下去的x坐标
                break;

            case MotionEvent.ACTION_MOVE://复杂的是移动时的判断
                float scrollX = event.getX();
                if (num != 0 && num != dataList.size() - 1)
                    anOffset = scrollX - downX;//滑动时的偏移量，用于计算每个是数据源文字的坐标值
                else {
                    anOffset = (float) ((scrollX - downX) / 1.5);//当滑到两端的时候添加一点阻力
                }
                if (scrollX > downX) {
                    //向右滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (scrollX - downX >= anInt) {
                        if (num > 0) {
                            anOffset = 0;
                            num = num - 1;
                            downX = scrollX;
                        }
                    }
                } else {

                    //向左滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (downX - scrollX >= anInt) {
                        if (num < dataList.size() - 1) {
                            anOffset = 0;
                            num = num + 1;
                            downX = scrollX;
                        }
                    }
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                //抬起手指时，偏移量归零，相当于回弹。
                anOffset = 0;
                invalidate();

                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 设置个数据源
     *
     * @param strings 数据源String集合
     */
    public void setData(List<String> strings) {
        this.dataList = strings;
        num = strings.size() / 2;
        invalidate();
    }

    /**
     * 向左移动一个单元
     */
    public void setAnLeftOffset() {
        if (num < dataList.size() - 1) {
            num = num + 1;
            invalidate();
        }

    }

    /**
     * 向右移动一个单元
     */
    public void setAnRightOffset() {
        if (num > 0) {
            num = num - 1;
            invalidate();
        }
    }

    /**
     * 获得被选中的文本
     *
     * @return 被选中的文本
     */
    public String getSelectedString() {
        if (dataList != null && dataList.size() != 0) {
            return dataList .get(num);
        }
        return null;
    }
}
