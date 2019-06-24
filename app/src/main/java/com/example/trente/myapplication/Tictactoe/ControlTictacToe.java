package com.example.trente.myapplication.Tictactoe;

import com.example.trente.myapplication.Tictactoe.Model.NoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuongnv on 6/24/19.
 */

public class ControlTictacToe {

    public int[] AScore = {0,4,27,256,1458};// Mang diem tan cong 0,4,28,256,2308
    public int[] DScore = {0,2,9,99,769};  // Mang diem phong ngu 0,1,9,85,769
    public NoteModel goPoint;
    public static int maxDepth = 6; // do sau toi da
    public static int maxMove = 4;  // so o tiep theo dem xet toi da

    public int checkEnd(int[][]boardArr, int row, int col, int p1, int p2) {
        int r = 0, c = 0;
        int i;
        boolean human, pc;
        // Check hang ngang
        while (c < GamePlayFragment.numberline - 4) {
            human = true;
            pc = true;
            for (i = 0; i < 5; i++) {
                if (boardArr[row][c + i] != p1)
                    human = false;
                if (boardArr[row][c + i] != p2)
                    pc = false;
            }
            if (human)
                return p1;
            if (pc)
                return p2;
            c++;
        }

        // Check  hang doc
        while (r < GamePlayFragment.numberline - 4) {
            human = true;
            pc = true;
            for (i = 0; i < 5; i++) {
                if (boardArr[r + i][col] != p1)
                    human = false;
                if (boardArr[r + i][col] != p2)
                    pc = false;
            }
            if (human)
                return p1;
            if (pc)
                return p2;
            r++;
        }

        // Check duong cheo xuong
        r = row;
        c = col;
        while (r > 0 && c > 0) {
            r--;
            c--;
        }
        while (r < GamePlayFragment.numberline - 4 && c < GamePlayFragment.numberline - 4) {
            human = true;
            pc = true;
            for (i = 0; i < 5; i++) {
                if (boardArr[r + i][c + i] != p1)
                    human = false;
                if (boardArr[r + i][c + i] != p2)
                    pc = false;
            }
            if (human)
                return p1;
            if (pc)
                return p2;
            r++;
            c++;
        }

        // Check duong cheo len
        r = row;
        c = col;
        while (r < GamePlayFragment.numberline - 1 && c > 0) {
            r++;
            c--;
        }

        while (r >= 4 && c < GamePlayFragment.numberline - 4) {
            human = true;
            pc = true;
            for (i = 0; i < 5; i++) {
                if (boardArr[r - i][c + i] != p1)
                    human = false;
                if (boardArr[r - i][c + i] != p2)
                    pc = false;
            }
            if (human)
                return p1;
            if (pc)
                return p2;
            r--;
            c++;
        }
        return GamePlayFragment.DRAWGAME;
    }


    // ham luong gia
    public int [][] evalChessBoard(int [][] array, int player) {
        int [][] eBoard = new int[GamePlayFragment.numberline][GamePlayFragment.numberline];
        int row, col;
        int ePC, eHuman;
//        eBoard.resetBoard(); // reset toan bo diem trang thai cua toan bo o co
        // Duyet theo hang
        for (row = 0; row < GamePlayFragment.numberline; row++)
            for (col = 0; col < GamePlayFragment.numberline - 4; col++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (array[row][col + i] == 1) // neu quan do la cua human
                        eHuman++;
                    if (array[row][col + i] == 2) // neu quan do la cua pc
                        ePC++;
                }
                // trong vong 5 o khong co quan dich
                if (eHuman * ePC == 0 && eHuman != ePC)
                    for (int i = 0; i < 5; i++) {
                        if (array[row][col + i] == 0) { // neu o chua danh
                            if (eHuman == 0) // ePC khac 0
                                if (player == 1)
                                    eBoard[row][col + i] += DScore[ePC]; // cho diem phong ngu
                                else
                                    eBoard[row][col + i] += AScore[ePC];// cho diem tan cong
                            if (ePC == 0) // eHuman khac 0
                                if (player == 2)
                                    eBoard[row][col + i] += DScore[eHuman];// cho diem phong ngu
                                else
                                    eBoard[row][col + i] += AScore[eHuman];// cho diem tan cong
                            if (eHuman == 4 || ePC == 4)
                                eBoard[row][col + i] *= 2;
                        }
                    }
            }

        // Duyet theo cot
        for (col = 0; col < GamePlayFragment.numberline; col++)
            for (row = 0; row < GamePlayFragment.numberline - 4; row++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (array[row + i][col] == 1)
                        eHuman++;
                    if (array[row + i][col] == 2)
                        ePC++;
                }
                if (eHuman * ePC == 0 && eHuman != ePC)
                    for (int i = 0; i < 5; i++) {
                        if (array[row + i][col] == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0)
                                if (player == 1)
                                    eBoard[row + i][col] += DScore[ePC];
                                else
                                    eBoard[row + i][col] += AScore[ePC];
                            if (ePC == 0)
                                if (player == 2)
                                    eBoard[row + i][col] += DScore[eHuman];
                                else
                                    eBoard[row + i][col] += AScore[eHuman];
                            if (eHuman == 4 || ePC == 4)
                                eBoard[row + i][col] *= 2;
                        }

                    }
            }

        // Duyet theo duong cheo xuong
        for (col = 0; col < GamePlayFragment.numberline - 4; col++)
            for (row = 0; row < GamePlayFragment.numberline - 4; row++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (array[row + i][col + i] == 1)
                        eHuman++;
                    if (array[row + i][col + i] == 2)
                        ePC++;
                }
                if (eHuman * ePC == 0 && eHuman != ePC)
                    for (int i = 0; i < 5; i++) {
                        if (array[row + i][col + i] == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0)
                                if (player == 1)
                                    eBoard[row + i][col + i] += DScore[ePC];
                                else
                                    eBoard[row + i][col + i] += AScore[ePC];
                            if (ePC == 0)
                                if (player == 2)
                                    eBoard[row + i][col + i] += DScore[eHuman];
                                else
                                    eBoard[row + i][col + i] += AScore[eHuman];
                            if (eHuman == 4 || ePC == 4)
                                eBoard[row + i][col + i] *= 2;
                        }

                    }
            }

        // Duyet theo duong cheo len
        for (row = 4; row < GamePlayFragment.numberline; row++)
            for (col = 0; col < GamePlayFragment.numberline - 4; col++) {
                ePC = 0; // so quan PC
                eHuman = 0; // so quan Human
                for (int i = 0; i < 5; i++) {
                    if (array[row - i][col + i] == 1) // neu la human
                        eHuman++; // tang so quan human
                    if (array[row - i][col + i] == 2) // neu la PC
                        ePC++; // tang so quan PC
                }
                if (eHuman * ePC == 0 && eHuman != ePC)
                    for (int i = 0; i < 5; i++) {
                        if (array[row - i][col + i] == 0) { // neu o chua duoc danh
                            if (eHuman == 0)
                                if (player == 1)
                                    eBoard[row - i][col + i] += DScore[ePC];
                                else
                                    eBoard[row - i][col + i] += AScore[ePC];
                            if (ePC == 0)
                                if (player == 2)
                                    eBoard[row - i][col + i] += DScore[eHuman];
                                else
                                    eBoard[row - i][col + i] += AScore[eHuman];
                            if (eHuman == 4 || ePC == 4)
                                eBoard[row - i][col + i] *= 2;
                        }

                    }
            }

            return eBoard;
    }

    public int getMaxValue(int[][] eBoard){
        int max = eBoard[0][0];
        for(int i = 0;i< GamePlayFragment.numberline; i++){
            for(int j = 0; j< GamePlayFragment.numberline; j ++){
                if(eBoard[i][j] > max){
                    max = eBoard[i][j];
                }
            }
        }
        return max;
    }

    public NoteModel MaxPos(int[][] eBoard) {
        int Max = 0; // diem max
        NoteModel p = new NoteModel(0, 0, 0);
        for (int i = 0; i < GamePlayFragment.numberline; i++) {
            for (int j = 0; j < GamePlayFragment.numberline; j++) {
                if (eBoard[i][j] > Max) {
                    p.x = i;
                    p.y = j;
                    Max = eBoard[i][j];
                }
            }
        }
        if (Max == 0) {
            return null;
        }
        return p;
    }

    private int maxValue(int[][] state, int alpha, int beta, int depth) {

//        eBoard.MaxPos();  // tinh toa do co diem cao nhat
        int [][] valuearray = evalChessBoard(state, 2); // danh gia diem voi nguoi choi hien tai la PC
        int value = getMaxValue(valuearray); // gia tri max hien tai
        if (depth >= maxDepth) {
            return value;
        }

        List<NoteModel> list = new ArrayList<>(); // list cac nut con
        for (int i = 0; i < maxMove; i++) {
            NoteModel node = MaxPos(valuearray);
            if(node == null)
                break;
            list.add(node);
            valuearray[node.x][node.y] = 0;
        }
        int v = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            NoteModel com = list.get(i);
            state[com.x][com.y] = 2;
            v = Math.max(v, minValue(state, alpha, beta, depth+1));
            state[com.x][com.y] = 0;
            if(v>= beta || checkEnd(state, com.x, com.y, 1, 2) == 2){
                goPoint = com;
                return v;

            }
            alpha = Math.max(alpha, v);
        }

        return v;
    }

    private int minValue(int[][] state, int alpha, int beta, int depth) {

        int [][] valuearray = evalChessBoard(state, 2); // danh gia diem voi nguoi choi hien tai la PC
        int value = getMaxValue(valuearray); // gia tri max hien tai
        if (depth >= maxDepth) {
            return value;
        }

        ArrayList<NoteModel> list = new ArrayList<>(); // list cac nut con
        for (int i = 0; i < maxMove; i++) {
            NoteModel node = MaxPos(valuearray);
            if(node == null)
                break;
            list.add(node);
            valuearray[node.x][node.y] = 0;
        }
        int v = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            NoteModel com = list.get(i);
            state[com.x][com.y] = 1;
            v = Math.min(v, maxValue(state, alpha, beta, depth+1));
            state[com.x][com.y] = 0;
            if(v <= alpha || checkEnd(state, com.x, com.y, 1, 2)==1){
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
    }

    // thuat toan alpha-beta
    public void alphaBeta(int[][] boardState, int alpha, int beta, int depth, int player) {
        if(player==2){
            maxValue(boardState, alpha, beta, depth);

        }else{
            minValue(boardState, alpha, beta, depth);
        }
    }

    // tinh toan nuoc di
    public NoteModel AI(int[][] boardState, int player) {
        alphaBeta(boardState, 0, 1,2 ,player);
        NoteModel temp = goPoint;
        return temp;
    }

    public ControlTictacToe(){

    }

}
