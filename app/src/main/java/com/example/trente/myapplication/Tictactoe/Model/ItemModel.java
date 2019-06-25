package com.example.trente.myapplication.Tictactoe.Model;

import android.graphics.Bitmap;

/**
 * Created by cuongnv on 6/19/19.
 */

public class ItemModel {
    public int xLocal;
    public int yLocal;
    public Bitmap bitmap;
    public boolean isDrawBackground = false;

    public ItemModel(Bitmap bitmap, int x, int y){
        this.bitmap = bitmap;
        this.xLocal = x;
        this.yLocal = y;
    }
}
