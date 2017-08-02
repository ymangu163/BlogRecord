package com.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 从左到右展开的ImageView
 * Created by gao on 2017/8/1.
 */

public class SpreadImageView extends AppCompatImageView {
    private Bitmap bitmap;
    //每次刷新比上一次多显示图片的比例
    private float step = 0.01f;
    //已经显示图片的比例
    private float currentScale = 0.0f;
    //显示图片的区域
    private RectF dst;
    private Rect src;
    private Context context;

    public SpreadImageView(Context context) {
        this(context, null);
    }

    public SpreadImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        Drawable drawable = getDrawable();
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        dst = new RectF();
        dst.left = 0;
        dst.top = 0;
        dst.bottom = bitmap.getHeight();
        src = new Rect();
        src.left = 0;
        src.top = 0;
        src.bottom = bitmap.getHeight();
    }

    /**
     * 默认从左到右显示
     */
    @Override
    protected void onDraw(Canvas canvas) {
        currentScale = currentScale + step > 1 ? 1 : currentScale + step;
        dst.right = bitmap.getWidth() * currentScale;
        src.right = (int) (bitmap.getWidth() * currentScale);
        /*
         * drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint)；
         * Rect src: 是对图片进行裁截，若是空null则显示整个图片
         * RectF dst：是图片在Canvas画布中显示的区域，
         * 大于src则把src的裁截区放大，
         * 小于src则把src的裁截区缩小。
         * 当想要让图片以画卷方式展现的话，主要是设置src大小，这边是默认从左到右显示，所以每次只要修改src中right的大小就好
         */
        canvas.drawBitmap(bitmap, src, dst, null);
        if (currentScale < 1) {
            invalidate();
        }
    }
}
