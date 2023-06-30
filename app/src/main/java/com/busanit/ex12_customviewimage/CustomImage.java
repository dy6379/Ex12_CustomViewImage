package com.busanit.ex12_customviewimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomImage extends View {
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Paint mPaint;

    public CustomImage(Context context) {
        super(context);
        init(context);
    }

    public CustomImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        createCacheBitmap(w,h);
        textDrawing();
    }

    private void createCacheBitmap(int w, int h) {
        cacheBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
    }

    private void textDrawing() {
        cacheCanvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.RED);
        cacheCanvas.drawRect(100,100,200,200,mPaint);

        Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.waterdrop);
        cacheCanvas.drawBitmap(srcImg, 30, 30, mPaint);

        Matrix horInverseMatrix = new Matrix(); //좌우 대칭
        horInverseMatrix.setScale(-1,1);
        Bitmap horInverseImage = Bitmap.createBitmap(srcImg,0,0,srcImg.getWidth(),srcImg.getHeight(),
                horInverseMatrix, false);
        cacheCanvas.drawBitmap(horInverseImage, 30,130,mPaint);

        Matrix verInverseMatrix = new Matrix(); //상하 대칭
        verInverseMatrix.setScale(1,-1);
        Bitmap verInverseImage = Bitmap.createBitmap(srcImg, 0,0,srcImg.getWidth(),
                srcImg.getHeight(),verInverseMatrix, false);
        cacheCanvas.drawBitmap(verInverseImage, 30,230,mPaint);

        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//번짐효과
        Bitmap scaledImg = Bitmap.createScaledBitmap(srcImg,srcImg.getWidth()*3,
                srcImg.getHeight()*3,false);
        cacheCanvas.drawBitmap(scaledImg,30,300,mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cacheBitmap!=null) canvas.drawBitmap(cacheBitmap,0,0,null);
    }
}
