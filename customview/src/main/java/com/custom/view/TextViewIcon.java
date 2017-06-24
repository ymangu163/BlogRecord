package com.custom.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.common.utils.CommonUtil;

/**
 * Created by gao on 2017/6/24.
 */

public class TextViewIcon extends AppCompatTextView {
    public TextViewIcon(Context context) {
        this(context, null);
    }

    public TextViewIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(CommonUtil.getIconfont(context));
    }
}
