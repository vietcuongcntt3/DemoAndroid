package com.example.trente.myapplication.Tictactoe.Model;

import com.example.trente.myapplication.Tictactoe.GamePlayFragment;
import com.example.trente.myapplication.Tictactoe.TictactoeMain;

/**
 * Created by cuongnv on 6/19/19.
 */

public class MinMaxModel {
    public int ME = 1;
    public int YOU = 2;
    public int MAX = 1000;
    public int MIN = -1000;


    public MinMaxModel(int ME, int YOU){
        this.ME = ME;
        this.YOU = YOU;
    }

    public NoteModel findBestMove(int[][] array) {
        NoteModel bestNode = null;
        int valueBest = MIN;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j ++){
                if(array[i][j] == GamePlayFragment.DEFAULT){
                    NoteModel node = new NoteModel(ME, i,j);
                    int valueNode = calMinMax(node, array,false, 0);
                    if(valueBest < valueNode){
                        valueBest = valueNode;
                        bestNode = node;
                    }
                }
            }
        }
        return bestNode;
    }

    public int calMinMax(NoteModel nodeChose, int[][] array, boolean isME, int dept) {
        int[][] arrayValue = new int[GamePlayFragment.numberline][GamePlayFragment.numberline];
        int count = 0;
        for(int i = 0; i < GamePlayFragment.numberline; i++){
            for(int j = 0; j < GamePlayFragment.numberline; j ++){
                arrayValue[i][j] = array[i][j];
                if(arrayValue[i][j] != GamePlayFragment.DEFAULT) count ++;
            }

        }

        arrayValue[nodeChose.x][nodeChose.y] = nodeChose.value;
        count ++;

        if (checkResult(arrayValue) == ME){
            return MAX - dept;
        }
        if (checkResult(arrayValue) == YOU) {
            return MIN + dept;
        }

        if(count == GamePlayFragment.numberline*GamePlayFragment.numberline){

            return (isME)? GamePlayFragment.DRAWGAME + dept : GamePlayFragment.DRAWGAME - dept;
        }

        int valueResult;
        if(isME){
            valueResult = MIN;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j ++){
                    if(arrayValue[i][j] == GamePlayFragment.DEFAULT){
                        NoteModel node = new NoteModel(ME, i,j);
                        int valueNode = calMinMax(node, arrayValue,false, dept + 1);
                        valueResult = Math.max(valueResult, valueNode);
                    }
                }
            }
        }else{
            valueResult = MAX;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j ++){
                    if(arrayValue[i][j] == GamePlayFragment.DEFAULT){
                        NoteModel node = new NoteModel(YOU, i,j);
                        int valueNode = calMinMax(node,arrayValue,true, dept + 1);
                        valueResult = Math.min(valueResult, valueNode);
                    }
                }

            }

        }

        return valueResult;
    }

    public static int checkResult(int[][] arrayValue){
        int count = 0;
        for(int i = 0; i < GamePlayFragment.numberline; i++){
            for(int j = 0;j< GamePlayFragment.numberline; j ++){
                if(arrayValue[i][j] != GamePlayFragment.DEFAULT) {
                    count ++;
                    if(checkRow(arrayValue, i,j)||checkColm(arrayValue, i, j)||checkX1(arrayValue, i, j)||checkX2(arrayValue, i, j)){
                        return arrayValue[i][j];
                    }
                }
            }
        }
        if(count == GamePlayFragment.numberline * GamePlayFragment.numberline) return GamePlayFragment.DRAWGAME;
        return GamePlayFragment.DEFAULT;
    }

    public static boolean checkRow(int[][]arrayValue, int x, int y){
        if(y + GamePlayFragment.numberSameWin - 1 >= GamePlayFragment.numberline){
            return false;
        }
        for(int i = 1;i < GamePlayFragment.numberSameWin; i ++){
            if(arrayValue[x][y+i]!= arrayValue[x][y]){
                return false;
            }
        }
        return true;
    }

    public static boolean checkColm(int[][]arrayValue, int x, int y){
        if(x + GamePlayFragment.numberSameWin - 1 >= GamePlayFragment.numberline){
            return false;
        }
        for(int i = 1;i < GamePlayFragment.numberSameWin; i ++){
            if(arrayValue[x+i][y]!= arrayValue[x][y]){
                return false;
            }
        }

        return true;
    }

    public static boolean checkX1(int[][]arrayValue, int x, int y){
        if(x + GamePlayFragment.numberSameWin - 1 >= GamePlayFragment.numberline || y + GamePlayFragment.numberSameWin - 1 >= GamePlayFragment.numberline){
            return false;
        }
        for(int i = 1;i < GamePlayFragment.numberSameWin; i ++){
            if(arrayValue[x+i][y+i]!= arrayValue[x][y]){
                return false;
            }
        }
        return true;
    }

    public static boolean checkX2(int[][]arrayValue, int x, int y){
        if(x + GamePlayFragment.numberSameWin - 1 >= GamePlayFragment.numberline || y - GamePlayFragment.numberSameWin + 1 < 0){
            return false;
        }
        for(int i = 1;i < GamePlayFragment.numberSameWin; i ++){
            if(arrayValue[x+i][y-i]!= arrayValue[x][y]){
                return false;
            }
        }
        return true;
    }

}
