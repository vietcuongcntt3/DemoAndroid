package com.example.trente.myapplication.Tictactoe.Model;

import android.util.Log;

import com.example.trente.myapplication.Tictactoe.GamePlayFragment;
import com.example.trente.myapplication.Tictactoe.TictactoeMain;
import com.example.trente.myapplication.Tictactoe.ultils.Const;
import com.example.trente.myapplication.Tictactoe.view.MyBoardView;

import java.util.List;

/**
 * Created by cuongnv on 6/19/19.
 */

public class MinMaxModel {
    public int ME = 1;
    public int YOU = 2;
    public static final long MAX = 100000;
    public long MIN = -100000;
    public int co = 0;

    public ResultModel bestResult;
//    public Heuristic heuristic;

    public MinMaxModel(int ME, int YOU){
        this.ME = ME;
        this.YOU = YOU;
//        heuristic = new Heuristic(ME, YOU);
    }

    public NoteModel findBestMove(int[][] array) {
        NoteModel bestNode = null;
        long anpha = MIN;
        long beta = MAX;
        bestResult = new ResultModel(Const.MAX_DEPT, MIN);
        List<NoteModel> nodes = Heuristic.findListBestMove(array, ME);
        for(NoteModel node : nodes) {
//            Log.e("Co123", node.x + "|" + node.y);
            node.value = ME;
            ResultModel valueNode = calMinMax(node, array,false, 0, anpha, beta);
            array[node.x][node.y] = GamePlayFragment.DEFAULT;
            if(bestResult.point < valueNode.point){
                bestResult = valueNode;
                bestNode = node;
                anpha = bestResult.point;
                if(bestResult.point >= MAX){

                    return bestNode;
                }
            }
        }
//        for(int i = 0; i < GamePlayFragment.numberline; i++){
//            for(int j = 0; j < GamePlayFragment.numberline; j ++){
//                if(array[i][j] == GamePlayFragment.DEFAULT ){
//                    NoteModel node = new NoteModel(ME, i,j);
//                    ResultModel valueNode = calMinMax(node, array,false, 0, anpha, beta);
//                    array[i][j] = GamePlayFragment.DEFAULT;
//                    if(bestResult.point < valueNode.point){
//                        bestResult = valueNode;
//                        bestNode = node;
//                        anpha = bestResult.point;
//                        if(bestResult.point >= MAX){
//                            Log.e("Co", co + "");
//                            return bestNode;
//                        }
//                    }
//                }
//            }
//        }
        Log.e("Co", co + "");
        return bestNode;
    }


    public ResultModel calMinMax(NoteModel nodeChose, int[][] array, boolean isME, int dept, long anpha, long beta) {
        co ++;
        int[][] arrayValue = array;
//        int aa = anpha;
//        int bb = beta;
        arrayValue[nodeChose.x][nodeChose.y] = nodeChose.value;
        int result = checkResult(arrayValue, nodeChose.x, nodeChose.y);

        if (result == ME){
            return new ResultModel(dept, MAX);
        }else if (result == YOU) {
            return new ResultModel(dept, MIN);
        }else if(result == GamePlayFragment.DRAWGAME){

            int point = (isME)? GamePlayFragment.DRAWGAME + dept : GamePlayFragment.DRAWGAME - dept;
            return new ResultModel(dept, point);
        }else if(dept >= this.bestResult.dept) {
            long calResult = Heuristic.caculateNotePoint(arrayValue, nodeChose.x, nodeChose.y);
            long point = (isME)? calResult: -calResult;
            return new ResultModel(dept, point);
        }else {
            ResultModel valueResult;
            if (isME) {
                valueResult = new ResultModel(Const.MAX_DEPT, MIN);

                List<NoteModel> nodes = Heuristic.findListBestMove(array, ME);
                for(NoteModel node : nodes) {
                    node.value = ME;
                    ResultModel valueNode = calMinMax(node, arrayValue, false, dept + 1, anpha, beta);
                    array[node.x][node.y] = GamePlayFragment.DEFAULT;
                    if(valueResult.point < valueNode.point){
                        valueResult = valueNode;
                        if(valueResult.point > anpha){
                            anpha = valueResult.point;
                        }
                        if(valueResult.point <= beta){
                            return valueResult;
                        }
                    }
                }

//                for (int i = 0; i < GamePlayFragment.numberline; i++) {
//                    for (int j = 0; j < GamePlayFragment.numberline; j++) {
//                        if (arrayValue[i][j] == GamePlayFragment.DEFAULT) {
//                            NoteModel node = new NoteModel(ME, i, j);
//                            ResultModel valueNode = calMinMax(node, arrayValue, false, dept + 1, anpha, beta);
//                            array[i][j] = GamePlayFragment.DEFAULT;
//                            if(valueResult.point < valueNode.point){
//                                valueResult = valueNode;
//                                if(valueResult.point > anpha){
//                                    anpha = valueResult.point;
//                                }
//                                if(valueResult.point <= beta){
//                                    return valueResult;
//                                }
//                            }
//                        }
//                    }
//                }
            } else {
                valueResult = new ResultModel(Const.MAX_DEPT, MAX);
                List<NoteModel> nodes = Heuristic.findListBestMove(array, ME);
                for(NoteModel node : nodes) {
                    node.value = YOU;
                    ResultModel valueNode = calMinMax(node, arrayValue, true, dept + 1, anpha, beta);
                    array[node.x][node.y] = GamePlayFragment.DEFAULT;
                    if(valueResult.point > valueNode.point){
                        valueResult = valueNode;
                        if(valueResult.point <= anpha){
                            return valueResult;
                        }
                        if(valueResult.point < beta){
                            beta = valueResult.point;
                        }
                    }
                }
//                for (int i = 0; i < GamePlayFragment.numberline; i++) {
//                    for (int j = 0; j < GamePlayFragment.numberline; j++) {
//                        if (arrayValue[i][j] == GamePlayFragment.DEFAULT) {
//                            NoteModel node = new NoteModel(YOU, i, j);
//                            ResultModel valueNode = calMinMax(node, arrayValue, true, dept + 1, anpha, beta);
//                            array[i][j] = GamePlayFragment.DEFAULT;
//                            if(valueResult.point > valueNode.point){
//                                valueResult = valueNode;
//                                if(valueResult.point <= anpha){
//                                    return valueResult;
//                                }
//                                if(valueResult.point < beta){
//                                    beta = valueResult.point;
//                                }
//                            }
//                        }
//                    }
//
//                }

            }

            return valueResult;
        }
    }



    public static int checkResult(int[][] arrayValue, MyBoardView board){
        int count = 0;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            for(int j = 0;j< Const.NUMBER_ROWS; j ++){
                if(arrayValue[i][j] != GamePlayFragment.DEFAULT) {
                    count ++;
                    if(checkRow(arrayValue, i,j, board)||checkColm(arrayValue, i, j, board)
                            ||checkX1(arrayValue, i, j, board)||checkX2(arrayValue, i, j, board)){
                        return arrayValue[i][j];
                    }
                }
            }
        }
        if(count == Const.NUMBER_ROWS * Const.NUMBER_ROWS) return GamePlayFragment.DRAWGAME;
        return GamePlayFragment.DEFAULT;
    }

    public static boolean checkRow(int[][]arrayValue, int x, int y, MyBoardView board){
        if(y + Const.NUMBER_WIN - 1 >= Const.NUMBER_ROWS){
            return false;
        }
        for(int i = 1;i < Const.NUMBER_WIN; i ++){
            if(arrayValue[x][y+i]!= arrayValue[x][y]){
                return false;
            }
        }

        for(ItemModel item : board.items){
            if( item.yLocal > y && item.yLocal < y + Const.NUMBER_ROWS && item.xLocal == x){
                item.isDrawBackground = true;
            }
        }
        return true;
    }

    public static boolean checkColm(int[][]arrayValue, int x, int y, MyBoardView board){
        if(x + Const.NUMBER_WIN - 1 >= Const.NUMBER_ROWS){
            return false;
        }
        for(int i = 1;i < Const.NUMBER_WIN; i ++){
            if(arrayValue[x+i][y]!= arrayValue[x][y]){
                return false;
            }
        }
        for(ItemModel item : board.items){
            if( item.xLocal > x && item.xLocal < x + Const.NUMBER_ROWS && item.yLocal == y){
                item.isDrawBackground = true;
            }
        }
        return true;
    }

    public static boolean checkX1(int[][]arrayValue, int x, int y, MyBoardView board){
        if(x + Const.NUMBER_WIN - 1 >= Const.NUMBER_ROWS || y + Const.NUMBER_WIN - 1 >= Const.NUMBER_ROWS){
            return false;
        }
        for(int i = 1;i < Const.NUMBER_WIN; i ++){
            if(arrayValue[x+i][y+i]!= arrayValue[x][y]){
                return false;
            }
        }

        for(ItemModel item : board.items){
            if( item.yLocal > y && item.yLocal < y + Const.NUMBER_ROWS && item.xLocal - item.yLocal == x - y){
                item.isDrawBackground = true;
            }
        }
        return true;
    }

    public static boolean checkX2(int[][]arrayValue, int x, int y, MyBoardView board){
        if(x + Const.NUMBER_WIN - 1 >= Const.NUMBER_ROWS || y - Const.NUMBER_WIN + 1 < 0){
            return false;
        }
        for(int i = 1;i < Const.NUMBER_WIN; i ++){
            if(arrayValue[x+i][y-i]!= arrayValue[x][y]){
                return false;
            }
        }

        for(ItemModel item : board.items){
            if( item.xLocal > x && item.xLocal < x + Const.NUMBER_ROWS
                    && item.yLocal < y && item.yLocal > y - Const.NUMBER_WIN &&  item.xLocal + item.yLocal == x + y){
                item.isDrawBackground = true;
            }
        }

        return true;
    }


    public static boolean isDrawGame(int[][]arrayValue){
        for(int i = 0; i < Const.NUMBER_ROWS; i++) {
            for (int j = 0; j < Const.NUMBER_ROWS; j++) {
                if (arrayValue[i][j] == GamePlayFragment.DEFAULT) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int checkResult(int[][]array, int x, int y){
        if(isWinCol(array, x, y)|| isWinRow(array, x, y) || isWinX1(array, x, y) || isWinX2(array, x, y)){
            return array[x][y];
        }

        if(isDrawGame(array)) return GamePlayFragment.DRAWGAME;
        return GamePlayFragment.DEFAULT;
    }

    public static boolean isWinRow(int[][]array, int x, int y){
        int count = 0;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(array[i][y]==array[x][y]){
                count ++;
            }else {
                count = 0;
            }
        }
        if(count >= Const.NUMBER_WIN){
            return true;
        }
        return false;
    }

    public static boolean isWinCol(int[][]array, int x, int y){
        int count = 0;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(array[x][i]==array[x][y]){
                count ++;
            }else {
                count = 0;
            }
        }
        if(count >= Const.NUMBER_WIN){
            return true;
        }
        return false;
    }

    public static boolean isWinX1(int[][]array, int x, int y){
        int count = 0;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(i - x + y >= 0 && i -x + y < Const.NUMBER_ROWS){
                if(array[i][i - x + y]==array[x][y]){
                    count ++;
                }else {
                    count = 0;
                }
            }

        }
        if(count >= Const.NUMBER_WIN){
            return true;
        }
        return false;
    }

    public static boolean isWinX2(int[][]array, int x, int y){
        int count = 0;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(x + y - i  < Const.NUMBER_ROWS && x + y - i >= 0){
                if(array[i][x + y - i]==array[x][y]){
                    count ++;
                }else {
                    count = 0;
                }
            }

        }
        if(count >= Const.NUMBER_WIN){
            return true;
        }
        return false;
    }


    public static int calResult(int[][]array, int x, int y){
        int result = calCol(array, x, y) + calRow(array, x, y) + calX1(array, x, y) + calX2(array, x, y);
        if(result >= MAX){
            return array[x][y];
        }

        if(isDrawGame(array)) return GamePlayFragment.DRAWGAME;
        return result;
    }

    public static long calPoint(int count, int numDefault, boolean isMe){
        if(isMe){
            switch (count){
                case 0:case 1:case 2:
                    return 0;
                case 3:
                    if(numDefault > 1)return 100;
                    return 0;
                case 4:
                    switch (numDefault){
                        case 0: return 0;
                        case 1: return 100;
                        default: return MAX;
                    }
                    default:
                        return MAX;
            }

        }else {
            switch (count) {
                case 0:case 1:case 2:
                    return 0;
                case 3:
                    switch (numDefault) {
                        case 0:
                            return 0;
                        case 1:
                            return  -100;
                        default:
                            return -MAX;
                    }
                default:
                    return -MAX;
            }
        }
    }

    public static int calRow(int[][]array, int x, int y){
        int count = 0;
        int numDefault = 0;
        int point = 0;
        boolean isDoubleDefault = false;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(array[i][y]==array[x][y]){
                count ++;
            }else if(array[i][y] == GamePlayFragment.DEFAULT){
                numDefault ++;
                if(isDoubleDefault || numDefault >= 2){
                    point += calPoint(count, numDefault, true);
                    count = 0;
                    numDefault = 1;
                }
                isDoubleDefault = true;
            }else {
                point += calPoint(count, numDefault, true);
                count = 0;
                isDoubleDefault = false;
                numDefault = 0;
            }
        }

        point += calPoint(count, numDefault, true);
        return point;
    }

    public static int calCol(int[][]array, int x, int y){
        int count = 0;
        int numDefault = 0;
        int point = 0;
        boolean isDoubleDefault = false;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(array[x][i]==array[x][y]){
                count ++;
            }else if(array[x][i] == GamePlayFragment.DEFAULT){
                numDefault ++;
                if(isDoubleDefault || numDefault >= 2){
                    point += calPoint(count, numDefault, true);
                    count = 0;
                    numDefault = 1;
                }
                isDoubleDefault = true;
            }else {
                point += calPoint(count, numDefault, true);
                count = 0;
                isDoubleDefault = false;
                numDefault = 0;
            }
        }
        point += calPoint(count, numDefault, true);
        return point;
    }

    public static int calX1(int[][]array, int x, int y){
        int count = 0;
        int numDefault = 0;
        int point = 0;
        boolean isDoubleDefault = false;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(i - x + y >= 0 && i -x + y < Const.NUMBER_ROWS){
                int value = array[i][i - x + y];
                if(value == array[x][y]){
                    count ++;
                }else if(value == GamePlayFragment.DEFAULT){
                    numDefault ++;
                    if(isDoubleDefault || numDefault >= 2){
                        point += calPoint(count, numDefault, true);
                        count = 0;
                        numDefault = 1;
                    }
                    isDoubleDefault = true;
                }else {
                    point += calPoint(count, numDefault, true);
                    count = 0;
                    isDoubleDefault = false;
                    numDefault = 0;
                }
            }

        }
        point += calPoint(count, numDefault, true);
        return point;
    }

    public static int calX2(int[][]array, int x, int y){
        int count = 0;
        int numDefault = 0;
        int point = 0;
        boolean isDoubleDefault = false;
        for(int i = 0; i < Const.NUMBER_ROWS; i++){
            if(x + y - i  < Const.NUMBER_ROWS && x + y - i >= 0){
                int value = array[i][x + y - i];
                if(value == array[x][y]){
                    count ++;
                }else if(value == GamePlayFragment.DEFAULT){
                    numDefault ++;
                    if(isDoubleDefault || numDefault >= 2){
                        point += calPoint(count, numDefault, true);
                        count = 0;
                        numDefault = 1;
                    }
                    isDoubleDefault = true;
                }else {
                    point += calPoint(count, numDefault, true);
                    count = 0;
                    isDoubleDefault = false;
                    numDefault = 0;
                }
            }

        }
        point += calPoint(count, numDefault, true);
        return point;
    }



}
