package com.example.dudgns0507.learndoin.Activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by sonbi on 2016-11-09.
 */

public class DrawCircle {
    protected void onDraw(Canvas canvas, double score,  double unitCnt) {
        final double zeroPoint = -90;
        final double unitDegree = 360/unitCnt; // 1회 완료시 몇 도 이동?

        double degree = score * unitDegree;

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(0x00);
        RectF rectF = new RectF(100, 100, 400, 400);

        canvas.drawArc(rectF,(float)zeroPoint, (float)degree, false, p);
    }
}
