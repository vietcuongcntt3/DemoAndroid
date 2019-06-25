package com.example.trente.myapplication.Tictactoe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.GamePlayFragment;
import com.example.trente.myapplication.Tictactoe.Model.ItemModel;
import com.example.trente.myapplication.Tictactoe.ultils.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by cuongnv on 6/25/19.
 */

public class MyBoardView extends RelativeLayout {
    public Stack<ItemModel> items = new Stack<>();
    public RelativeLayout.LayoutParams params;
    public Paint mPaint = new Paint();
    public int frameWidth;
    public int frameHeight;
    public int x, y;
    public int xTouch, yTouch;
    public int previousx, previousy;
    public boolean isMove = false;

    public MyBoardView(Context context) {
        super(context);
        frameHeight = Const.SIZE_CHEESE * Const.NUMBER_ROWS;
        frameWidth = Const.SIZE_CHEESE * Const.NUMBER_ROWS;
        params = new RelativeLayout.LayoutParams(frameWidth, frameWidth);

        params.leftMargin = 0;
        params.topMargin = 0;
        setLayoutParams(params);
        setBackgroundResource(R.drawable.board_shape);
    }

    public void touchDownBoard(int x, int y) {
        this.xTouch = x - params.leftMargin;
        this.yTouch = y - params.topMargin;
        this.isMove = false;
        this.x = params.leftMargin;
        this.y = params.topMargin;
        this.previousx = this.x;
        this.previousy = this.y;
    }

//    public void touchUpBoard(int x, int y) {
//        if(isMove){
//            Log.e("tag", params.rightMargin + "|" + params.bottomMargin);
//        }else {
//
//        }
//
//    }
    public void touchMoveBoard(int x, int y, int widthView, int heightView) {
        params.leftMargin = x - xTouch;
        params.topMargin = y - yTouch;
        if(params.leftMargin > 0){
            params.leftMargin = 0;
        }
        if(params.topMargin > 0){
            params.topMargin = 0;
        }

        if(params.topMargin + frameHeight < heightView){
            params.topMargin = heightView - frameHeight;
        }

        if(params.leftMargin + frameWidth < widthView){
            params.leftMargin = widthView - frameWidth;
        }

        if(!isMove && (Math.abs(x - this.x) > 20 || Math.abs(y - this.y) > 20)){
            isMove = true;
//            Log.e("dfqf", "true");
        }


        setLayoutParams(params);
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2f);
//        float xWidth = frameWidth/ GamePlayFragment.numberline;
        for(int i = 1; i < GamePlayFragment.numberline; i++){
            canvas.drawLine(0, i*Const.SIZE_CHEESE, frameWidth, i *Const.SIZE_CHEESE, mPaint);
            canvas.drawLine(i*Const.SIZE_CHEESE, 0, i * Const.SIZE_CHEESE, frameWidth, mPaint);
        }

        for(ItemModel item : items){
            drawitem(canvas, item);
        }

    }

    public void drawitem(Canvas canvas, ItemModel item){
//        int width = getMeasuredWidth()/GamePlayFragment.numberline - 16;

        float bmWidth = item.bitmap.getWidth();
        float scale = (Const.SIZE_CHEESE-16)/bmWidth;
        canvas.save();
        canvas.translate(item.xLocal *Const.SIZE_CHEESE + 8, item.yLocal * Const.SIZE_CHEESE + 8);
        canvas.scale(scale,scale);
        if(item.isDrawBackground) {
            Paint p = new Paint();
            p.setColor(Color.YELLOW);
            canvas.drawRect(-4, -4, bmWidth + 4, bmWidth + 4, p);
        }
        canvas.drawBitmap(item.bitmap, 0, 0, mPaint);

        canvas.restore();
    }

    public int getLocationXY(int value, boolean isX){
        Log.e("value", isX + "|" + value + " | " + params.topMargin + "|" + params.leftMargin);
        int currentLocal = value;
        int index = Const.NUMBER_ROWS - 1;
        while(index * Const.SIZE_CHEESE >  currentLocal){
            index --;
        }
        if(index < 0) index = 0;
        return index;
    }

    public void updateBegin(int width, int height){
        params.leftMargin = (width - frameWidth)/2;
        params.topMargin = (height - frameWidth)/2;
        setLayoutParams(params);
    }



}
