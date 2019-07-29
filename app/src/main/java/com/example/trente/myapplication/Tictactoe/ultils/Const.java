package com.example.trente.myapplication.Tictactoe.ultils;

import android.util.Log;

/**
 * Created by cuongnv on 6/25/19.
 */

public class Const {
    public static final int SIZE_CHEESE = 75;
    public static final int NUMBER_ROWS = 20;
    public static final int NUMBER_WIN = 5;
    public static final int MAX_DEPT = 9;

    public static final int GAME_TYPE_P1 = 1;
    public static final int GAME_TYPE_P2 = 2;
    public static final int GAME_TYPE_ONLINE = 3;

    public static final String ROOM_STATUS_WAITING = "0";
    public static final String ROOM_STATUS_PLAYING = "1";
    public static final String ROOM_STATUS_ENDED = "2";

    public static String convertArrayToString(int[][]data){
        String result = "";
        for(int i = 0; i< data.length; i ++){
            for(int j = 0; j<data[i].length; j ++){
                result += data[i][j] + ",";
                Log.e("cuong",data[i][j]+" ");
            }
        }
        return result;
    }

    public static int[][] convertStringToArray(String data, int lengthX, int lengthY){
        int[][] result = new int[lengthX][lengthY];
        if(data.length() > lengthX* lengthY) {
            String[] spilit = data.split(",");

            for (int i = 0; i < lengthX; i++) {
                for (int j = 0; j < lengthY; j++) {
                    result[i][j] = Integer.parseInt(spilit[i * lengthY + j]);
                }
            }
        }
        return result;
    }
}
