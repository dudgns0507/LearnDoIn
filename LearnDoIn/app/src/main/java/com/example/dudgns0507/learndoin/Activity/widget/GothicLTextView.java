package com.example.dudgns0507.learndoin.Activity.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pyh42 on 2017-03-11.
 */

public class GothicLTextView extends TextView {
    public GothicLTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothicLight.otf");
        this.setTypeface(face);
    }

    public GothicLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothicLight.otf");
        this.setTypeface(face);
    }

    public GothicLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothicLight.otf");
        this.setTypeface(face);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
