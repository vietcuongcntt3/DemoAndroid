package com.example.trente.myapplication.Tictactoe.Model;

import android.util.Log;

import com.example.trente.myapplication.Tictactoe.GamePlayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuongnv on 6/21/19.
 */

public class Heuristic {
    public int ME = 1;
    public int YOU = 2;
    public static final long MAX = 1000000;
    public static long MIN = -1000000;
    public int co = 0;

    public ResultModel bestResult;

    public Heuristic(int ME, int YOU){
        this.ME = ME;
        this.YOU = YOU;
    }

    public static long[] arrayAttack = new long[]{1, 3, 24, 192,1536, 12288, 98304};
    public static long[] arrayProtect = new long[]{0, 1, 9, 81, 729, 6561, 59049};

    public NoteModel findBestMove(int[][] array) {
        NoteModel bestNode = null;
        long value = MIN;
        for(int i = 0; i < GamePlayFragment.numberline; i++){
            for(int j = 0; j < GamePlayFragment.numberline; j ++){
                if(array[i][j] == GamePlayFragment.DEFAULT ){
                    array[i][j] = ME;
                    long attack = countAttackRow(array, i, j) + countAttackColumn(array, i, j) + countAttackX1(array,i, j)
                            + countAttackX2(array, i, j);
                    long protect = countProtectRow(array, i, j) + countProtectColumn(array, i, j) + countProtectX1(array,i, j)
                            + countProtectX2(array, i, j);
                    long valueNote = Math.max(attack, protect);
                    if(valueNote > value){
                        bestNode = new NoteModel(ME, i, j);
                        value = valueNote;
                    }
                    array[i][j] = GamePlayFragment.DEFAULT;
                }
            }
        }
        Log.e("Co", bestNode.x + "    |  " + bestNode.y);
        return bestNode;
    }

    public List<NoteModel> findListBestMove(int[][] array) {
        List<NoteModel> bestListNode = new ArrayList<>();
        long value = MIN;
        for(int i = 0; i < GamePlayFragment.numberline; i++){
            for(int j = 0; j < GamePlayFragment.numberline; j ++){
                if(array[i][j] == GamePlayFragment.DEFAULT ){
                    array[i][j] = ME;

                    long valueNote = caculateNotePoint(array, i, j);
                    if(valueNote > value){
                        bestListNode.clear();
                        NoteModel bestNode = new NoteModel(GamePlayFragment.DEFAULT, i, j);
                        bestListNode.add(bestNode);
                        value = valueNote;
                    }else if(value == valueNote){
                        NoteModel bestNode = new NoteModel(GamePlayFragment.DEFAULT, i, j);
                        bestListNode.add(bestNode);
                    }
                    array[i][j] = GamePlayFragment.DEFAULT;
                }
            }
        }
//        Log.e("Co", bestNode.x + "    |  " + bestNode.y);
        return bestListNode;
    }

    public static long caculateNotePoint(int[][]array, int i, int j){
        long attack = countAttackRow(array, i, j) + countAttackColumn(array, i, j) + countAttackX1(array,i, j)
                + countAttackX2(array, i, j);
        long protect = countProtectRow(array, i, j) + countProtectColumn(array, i, j) + countProtectX1(array,i, j)
                + countProtectX2(array, i, j);
        return Math.max(attack, protect);
    }
    public static long countAttackRow(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        int bonous = 0;
        for(int index = 1; index < 6; index ++){
            if(x + index < GamePlayFragment.numberline) {
                if (array[x + index][y] == array[x][y]) {
                    countMe++;
                } else if (array[x + index][y] == GamePlayFragment.DEFAULT) {
                    if(x+index + 1 < GamePlayFragment.numberline && array[x + index + 1][y] == array[x][y]){
                        bonous += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(x - index >= 0) {
                if (array[x - index][y] == array[x][y]) {
                    countMe++;
                } else if (array[x - index][y] == GamePlayFragment.DEFAULT) {
                    if(x-index - 1 >= 0 && array[x - index - 1][y] == array[x][y]){
                        bonous += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }
        if(countYou >= 2) return 0;
        return arrayAttack[countMe + 1] - arrayProtect[countYou + 1] + bonous;
    }


    public static long countAttackColumn(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        int bonus = 0;
        for(int index = 1; index < 6; index ++){
            if(y + index < GamePlayFragment.numberline) {
                if (array[x][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x][y + index] == GamePlayFragment.DEFAULT) {
                    if(y+index + 1 < GamePlayFragment.numberline && array[x][y + index + 1] == array[x][y]){
                        bonus += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(y - index >= 0) {
                if (array[x][y - index] == array[x][y]) {
                    countMe++;
                } else if (array[x][y - index] == GamePlayFragment.DEFAULT) {
                    if(y-index - 1 >= 0 && array[x][y - index - 1] == array[x][y]){
                        bonus += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }
        if(countYou >= 2) return 0;
        return arrayAttack[countMe + 1] - arrayProtect[countYou + 1] + bonus;
    }

    public static long countAttackX1(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        int bonus = 0;
        for(int index = 1; index < 6; index ++){
            if(x + index < GamePlayFragment.numberline && y + index < GamePlayFragment.numberline) {
                if (array[x + index][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x + index][y + index] == GamePlayFragment.DEFAULT) {
                    if(y+index + 1 < GamePlayFragment.numberline && x + index + 1 < GamePlayFragment.numberline
                            && array[x+ index + 1][y + index + 1] == array[x][y]){
                        bonus += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(x - index >= 0 && y - index >= 0) {
                if (array[x- index][y - index] == array[x][y]) {
                    countMe++;
                } else if (array[x - index][y - index] == GamePlayFragment.DEFAULT) {
                    if(y-index - 1 >= 0 && x - index - 1 >= 0
                            && array[x- index - 1][y - index - 1] == array[x][y]){
                        bonus += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }
        if(countYou >= 2) return 0;
        return arrayAttack[countMe + 1] - arrayProtect[countYou + 1] + bonus;
    }

    public static long countAttackX2(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        int bonus = 0;
        for(int index = 1; index < 6; index ++){
            if(x + index < GamePlayFragment.numberline && y - index >= 0) {
                if (array[x + index][y - index] == array[x][y]) {
                    countMe++;
                } else if (array[x + index ][y - index] == GamePlayFragment.DEFAULT) {
                    if(x + index + 1 < GamePlayFragment.numberline && y - index - 1 >= 0
                            && array[x + index + 1][y - index - 1] == array[x][y]){
                        bonus += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(x - index >= 0 && y + index < GamePlayFragment.numberline) {
                if (array[x - index][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x- index][y + index] == GamePlayFragment.DEFAULT) {
                    if(x-index - 1 >= 0 && y + index + 1 < GamePlayFragment.numberline
                            &&array[x - index - 1][y + index + 1] == array[x][y]){
                        bonus += 5;
                    }
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }
        if(countYou >= 2) return 0;
        return arrayAttack[countMe + 1] - arrayProtect[countYou + 1] + bonus;
    }


    /////////////protect

    public static long countProtectRow(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        for(int index = 1; index < 6; index ++){
            if(x + index < GamePlayFragment.numberline) {
                if (array[x + index][y] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x + index][y] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;
                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(x - index >= 0) {
                if (array[x - index][y] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x - index][y] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;
                }
            }
        }
        if(countMe >= 2) return 0;
        return arrayProtect[countYou + 1];
    }


    public static long countProtectColumn(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        for(int index = 1; index < 6; index ++){
            if(y + index < GamePlayFragment.numberline) {
                if (array[x][y + index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x][y + index] == GamePlayFragment.DEFAULT) {

                    break;
                }else {
                    countYou ++;

                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(y - index >= 0) {
                if (array[x][y - index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x][y - index] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;

                }
            }
        }
        if(countMe >= 2) return 0;
        return arrayProtect[countYou + 1];
    }

    public static long countProtectX1(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        for(int index = 1; index < 6; index ++){
            if(x + index < GamePlayFragment.numberline && y + index < GamePlayFragment.numberline) {
                if (array[x + index][y + index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x + index][y + index] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;

                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(x - index >= 0 && y - index >= 0) {
                if (array[x- index][y - index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x - index][y - index] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;

                }
            }
        }
        if(countMe >= 2) return 0;
        return arrayProtect[countYou + 1];
    }

    public static long countProtectX2(int[][]array, int x, int y){
        int countMe = 0;
        int countYou = 0;
        for(int index = 1; index < 6; index ++){
            if(x + index < GamePlayFragment.numberline && y - index >= 0) {
                if (array[x + index][y - index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x + index ][y - index] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;
                }
            }
        }

        for(int index = 1; index < 6; index ++){
            if(x - index >= 0 && y + index < GamePlayFragment.numberline) {
                if (array[x - index][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x- index][y + index] == GamePlayFragment.DEFAULT) {
                    break;
                }else {
                    countYou ++;
                    break;
                }
            }
        }
        if(countMe >= 2) return 0;
        return arrayProtect[countYou + 1];
    }

}
