package com.example.trente.myapplication.Tictactoe.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.GamePlayFragment;
import com.example.trente.myapplication.Tictactoe.Model.ItemModel;
import com.example.trente.myapplication.Tictactoe.ultils.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by cuongnv on 6/18/19.
 */

public class MyCardView extends View {

    public Paint mPaint = new Paint();
    public Matrix matrix= new Matrix();
    public List<ItemModel> items = new ArrayList<>();

    public MyCardView(Context context) {
        super(context);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        Log.e("size", size + "");
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //width = height
        float with = getMeasuredWidth();
        float xWidth = with/ Const.NUMBER_ROWS;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2f);
        for(int i = 1;i < Const.NUMBER_ROWS; i++){
            canvas.drawLine(0, i*xWidth, with, i * xWidth, mPaint);
            canvas.drawLine(i*xWidth, 0, i *xWidth, with, mPaint);
        }

        for(ItemModel item : items){
            drawitem(canvas, item, xWidth);
        }
    }

    public int getLocationXY(float x){
        int index = Const.NUMBER_ROWS - 1;
        int xWidth = getMeasuredWidth()/Const.NUMBER_ROWS;
        while(index * xWidth >  x){
            index --;
        }
        if(index < 0) index = 0;
        return index;
    }

    public void drawitem(Canvas canvas, ItemModel item, float size){
        int width = getMeasuredWidth()/Const.NUMBER_ROWS - 16;

        float bmWidth = item.bitmap.getWidth();
        float scale = width/bmWidth;
        canvas.save();
        canvas.translate(item.xLocal * size + 8, item.yLocal * size + 8);
        canvas.scale(scale,scale);

        canvas.drawBitmap(item.bitmap, 0, 0, mPaint);
        canvas.restore();
    }


}