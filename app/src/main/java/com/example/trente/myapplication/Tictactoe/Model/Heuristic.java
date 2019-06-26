package com.example.trente.myapplication.Tictactoe.Model;

import android.util.Log;

import com.example.trente.myapplication.Tictactoe.GamePlayFragment;
import com.example.trente.myapplication.Tictactoe.ultils.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuongnv on 6/21/19.
 */

public class Heuristic {
    //    public int ME = 1;
//    public int YOU = 2;
    public static final long MAX = 1000000;
    public static long MIN = -1000000;
    public int co = 0;

    public ResultModel bestResult;

    public Heuristic(int ME, int YOU) {
//        this.ME = ME;
//        this.YOU = YOU;
    }

//    public static long[] arrayAttack = new long[]{1, 3, 24, 192,1536, 12288, 98304};
//    public static long[] arrayProtect = new long[]{0, 1, 9, 81, 729, 6561, 59049};

    public static long getAttack(int num) {
        switch (num) {
            case 0:
                return 1;
            default:
                long result = 3;
                for (int temp = 0; temp < num - 1; temp++) {
                    result *= 8;
                }
                return result;
        }
    }

    public static long getProtect(int num) {
        switch (num) {
            case 0:
                return 0;
            default:
                long result = 1;
                for (int temp = 0; temp < num - 1; temp++) {
                    result *= 9;
                }
                return result;
        }
    }

    public NoteModel findBestMove(int[][] array, int ME) {
        NoteModel bestNode = null;
        long value = MIN;
        for (int i = 0; i < Const.NUMBER_ROWS; i++) {
            for (int j = 0; j < Const.NUMBER_ROWS; j++) {
                if (array[i][j] == GamePlayFragment.DEFAULT) {
                    array[i][j] = ME;
                    long attack = countAttackRow(array, i, j) + countAttackColumn(array, i, j) + countAttackX1(array, i, j)
                            + countAttackX2(array, i, j);
                    long protect = countProtectRow(array, i, j) + countProtectColumn(array, i, j) + countProtectX1(array, i, j)
                            + countProtectX2(array, i, j);
                    long valueNote = Math.max(attack, protect);
                    if (valueNote > value) {
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

    public static List<NoteModel> findListBestMove(int[][] array, int ME) {
        List<NoteModel> bestListNode = new ArrayList<>();
        long value = MIN;
        for (int i = 0; i < Const.NUMBER_ROWS; i++) {
            for (int j = 0; j < Const.NUMBER_ROWS; j++) {
                if (array[i][j] == GamePlayFragment.DEFAULT) {
                    array[i][j] = ME;

                    long valueNote = caculateNotePoint(array, i, j);
                    if (valueNote > value) {
                        bestListNode.clear();
                        NoteModel bestNode = new NoteModel(GamePlayFragment.DEFAULT, i, j);
                        bestListNode.add(bestNode);
                        value = valueNote;
                    } else if (value == valueNote) {
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

    public static long caculateNotePoint(int[][] array, int i, int j) {
        long attack = countAttackRow(array, i, j) + countAttackColumn(array, i, j) + countAttackX1(array, i, j)
                + countAttackX2(array, i, j);
        long protect = countProtectRow(array, i, j) + countProtectColumn(array, i, j) + countProtectX1(array, i, j)
                + countProtectX2(array, i, j);
        return Math.max(attack, protect);
    }

    // attack

    public static long countAttackRow(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        int numberdefault = 0;
        for (int index = 1; index < 6; index++) {
            if (x + index < Const.NUMBER_ROWS) {
                if (array[x + index][y] == array[x][y]) {
                    countMe++;
                } else if (array[x + index][y] == GamePlayFragment.DEFAULT) {
                    if (x + index + 1 < Const.NUMBER_ROWS) {
                        if (array[x + index + 1][y] == array[x][y]) {
                            numberdefault++;
                            countMe++;
                        } else if (array[x + index + 1][y] != GamePlayFragment.DEFAULT) {
                            countYou++;
                            numberdefault++;
                        }
                    }

                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (x - index >= 0) {
                if (array[x - index][y] == array[x][y]) {
                    countMe++;
                } else if (array[x - index][y] == GamePlayFragment.DEFAULT) {
                    if (x - index - 1 >= 0 && numberdefault == 0) {
                        if (array[x - index - 1][y] == array[x][y]) {
                            countMe++;
                        } else if (array[x - index - 1][y] != GamePlayFragment.DEFAULT) {
                            countYou++;
                        }
                    }

                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }
        if (countYou >= 2) return 0;
        return getAttack(countMe + 1) - getProtect(countYou + 1);
    }


    public static long countAttackColumn(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
//        int bonus = 0;
        int numberdefault = 0;
        for (int index = 1; index < 6; index++) {
            if (y + index < Const.NUMBER_ROWS) {
                if (array[x][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x][y + index] == GamePlayFragment.DEFAULT && y + index + 1 < Const.NUMBER_ROWS) {
                    if (array[x][y + index + 1] == array[x][y]) {
                        numberdefault++;
                        countMe++;
                    } else if (array[x][y + index + 1] != GamePlayFragment.DEFAULT) {
                        countYou++;
                        numberdefault++;
                    }
                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (y - index >= 0) {
                if (array[x][y - index] == array[x][y]) {
                    countMe++;
                } else if (array[x][y - index] == GamePlayFragment.DEFAULT) {
                    if (y - index - 1 >= 0 && numberdefault == 0){
                        if(array[x][y - index - 1] == array[x][y]){
                            countMe++;
                        }else if(array[x][y - index - 1] != GamePlayFragment.DEFAULT){
                            countYou ++;
                        }
                    }
                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }
        if (countYou >= 2) return 0;
        return getAttack(countMe + 1) - getProtect(countYou + 1);
    }

    public static long countAttackX1(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        int numberdefault = 0;
        for (int index = 1; index < 6; index++) {
            if (x + index < Const.NUMBER_ROWS && y + index < Const.NUMBER_ROWS) {
                if (array[x + index][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x + index][y + index] == GamePlayFragment.DEFAULT) {
                    if (y + index + 1 < Const.NUMBER_ROWS && x + index + 1 < Const.NUMBER_ROWS){
                        if(array[x + index + 1][y + index + 1] == array[x][y]) {
                            numberdefault++;
                            countMe++;
                        }else if(array[x + index + 1][y + index + 1] != GamePlayFragment.DEFAULT){
                            countYou ++;
                            numberdefault ++;
                        }
                    }
                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (x - index >= 0 && y - index >= 0) {
                if (array[x - index][y - index] == array[x][y]) {
                    countMe++;
                } else if (array[x - index][y - index] == GamePlayFragment.DEFAULT) {
                    if (y - index - 1 >= 0 && x - index - 1 >= 0 && numberdefault == 0) {
                        if(array[x - index - 1][y - index - 1] == array[x][y]) {
                            countMe++;
                        }else if(array[x - index - 1][y - index - 1] != GamePlayFragment.DEFAULT){
                            countYou ++;
                        }
                    }
                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }
        if (countYou >= 2) return 0;
        return getAttack(countMe + 1) - getProtect(countYou + 1);
    }

    public static long countAttackX2(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        int numberdefault = 0;
        for (int index = 1; index < 6; index++) {
            if (x + index < Const.NUMBER_ROWS && y - index >= 0) {
                if (array[x + index][y - index] == array[x][y]) {
                    countMe++;
                } else if (array[x + index][y - index] == GamePlayFragment.DEFAULT) {
                    if (x + index + 1 < Const.NUMBER_ROWS && y - index - 1 >= 0){
                        if(array[x + index + 1][y - index - 1] == array[x][y]) {
                            numberdefault++;
                            countMe++;
                        }else if(array[x + index + 1][y - index - 1] != GamePlayFragment.DEFAULT){
                            numberdefault ++;
                            countYou ++;
                        }
                    }
                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (x - index >= 0 && y + index < Const.NUMBER_ROWS) {
                if (array[x - index][y + index] == array[x][y]) {
                    countMe++;
                } else if (array[x - index][y + index] == GamePlayFragment.DEFAULT) {
                    if (x - index - 1 >= 0 && y + index + 1 < Const.NUMBER_ROWS && numberdefault == 0) {
                        if(array[x - index - 1][y + index + 1] == array[x][y]) {
                            countMe++;
                        }else if(array[x - index - 1][y + index + 1] != GamePlayFragment.DEFAULT){
                            countYou ++;
                        }
                    }
                    break;
                } else {
                    countYou++;
                    break;
                }
            }
        }
        if (countYou >= 2) return 0;
        return getAttack(countMe + 1) - getProtect(countYou + 1);
    }


    /////////////protect

    public static long countProtectRow(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        for (int index = 1; index < 6; index++) {
            if (x + index < Const.NUMBER_ROWS) {
                if (array[x + index][y] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x + index][y] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;
                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (x - index >= 0) {
                if (array[x - index][y] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x - index][y] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;
                }
            }
        }
        if (countMe >= 2) return 0;
        return getProtect(countYou + 1);
    }


    public static long countProtectColumn(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        for (int index = 1; index < 6; index++) {
            if (y + index < Const.NUMBER_ROWS) {
                if (array[x][y + index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x][y + index] == GamePlayFragment.DEFAULT) {

                    break;
                } else {
                    countYou++;

                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (y - index >= 0) {
                if (array[x][y - index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x][y - index] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;

                }
            }
        }
        if (countMe >= 2) return 0;
        return getProtect(countYou + 1);
    }

    public static long countProtectX1(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        for (int index = 1; index < 6; index++) {
            if (x + index < Const.NUMBER_ROWS && y + index < Const.NUMBER_ROWS) {
                if (array[x + index][y + index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x + index][y + index] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;

                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (x - index >= 0 && y - index >= 0) {
                if (array[x - index][y - index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x - index][y - index] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;

                }
            }
        }
        if (countMe >= 2) return 0;
        return getProtect(countYou + 1);
    }

    public static long countProtectX2(int[][] array, int x, int y) {
        int countMe = 0;
        int countYou = 0;
        for (int index = 1; index < 6; index++) {
            if (x + index < Const.NUMBER_ROWS && y - index >= 0) {
                if (array[x + index][y - index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x + index][y - index] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;
                }
            }
        }

        for (int index = 1; index < 6; index++) {
            if (x - index >= 0 && y + index < Const.NUMBER_ROWS) {
                if (array[x - index][y + index] == array[x][y]) {
                    countMe++;
                    break;
                } else if (array[x - index][y + index] == GamePlayFragment.DEFAULT) {
                    break;
                } else {
                    countYou++;
                }
            }
        }
        if (countMe >= 2) return 0;
        return getProtect(countYou + 1);
    }

}
