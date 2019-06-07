package com.example.trente.myapplication.Tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
import com.example.trente.myapplication.user.UserModel;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuongnv on 6/7/19.
 */

public class TictactoeMain extends AppCompatActivity implements View.OnClickListener{
    String o = "X";
    int[][] arrayValue = new int[3][3];
    List<Node> listNode = new ArrayList<>();
    public static int MAX = 1000;
    public static int MIN = -1000;
//    public static int MyValue = 1;
    public static int WIN_GAME = 1;
    public static int LOSE_GAME = 2;
    public static int DRAW_GAME = 0;
    public static int INFINITY = 1;
    public TextView[] listTextView = new TextView[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_layout);
//        Button bt = (Button) findViewById(R.id.button_load);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                processLogin();
//            }
//        });


        listTextView[0] = (TextView)findViewById(R.id.o1);
        listTextView[0].setTag(new Node(-1, 0, 0));
        listTextView[0].setOnClickListener(this);

        listTextView[1] = (TextView)findViewById(R.id.o2);
        listTextView[1].setTag(new Node(-1, 0, 1));
        listTextView[1].setOnClickListener(this);

        listTextView[2] = (TextView)findViewById(R.id.o3);
        listTextView[2].setTag(new Node(-1, 0, 2));
        listTextView[2].setOnClickListener(this);

        listTextView[3] = (TextView)findViewById(R.id.o4);
        listTextView[3].setTag(new Node(-1, 1, 0));
        listTextView[3].setOnClickListener(this);

        listTextView[4] = (TextView)findViewById(R.id.o5);
        listTextView[4].setTag(new Node(-1, 1, 1));
        listTextView[4].setOnClickListener(this);

        listTextView[5] = (TextView)findViewById(R.id.o6);
        listTextView[5].setTag(new Node(-1, 1, 2));
        listTextView[5].setOnClickListener(this);

        listTextView[6] = (TextView)findViewById(R.id.o7);
        listTextView[6].setTag(new Node(-1, 2, 0));
        listTextView[6].setOnClickListener(this);

        listTextView[7] = (TextView)findViewById(R.id.o8);
        listTextView[7].setTag(new Node(-1, 2, 1));
        listTextView[7].setOnClickListener(this);

        listTextView[8] = (TextView)findViewById(R.id.o9);
        listTextView[8].setTag(new Node(-1, 2, 2));
        listTextView[8].setOnClickListener(this);

        for(int i = 0; i < 3; i++){
            for(int j = 0;j< 3; j ++){
                arrayValue[i][j] = DRAW_GAME;
            }
        }

    }


    @Override
    public void onClick(View v) {
        TextView tv = (TextView)findViewById(v.getId());
        if(!"O".equals(tv.getText()) && !"X".equals(tv.getText())) {
            tv.setText(o);
            Node node = (Node)tv.getTag();
//            if ("O".equals(o)) {
//                node.value = LOSE_GAME;
//                o = "X";
//            } else {
//                o = "O";
//                node.value = WIN_GAME;

//            }

            arrayValue[node.x][node.y] = LOSE_GAME;
            int result = checkResult(arrayValue);
            if(result == LOSE_GAME){
                showDialog("O da thang");

            }else if(result == WIN_GAME){
                showDialog("X da thang");

            }else if(result == 9) {
                showDialog("Hoa");
            }else {

                Node best = findBestMove(arrayValue);
                int index = best.x * 3 + best.y;
                listTextView[index].setText("O");
                arrayValue[best.x][best.y] = WIN_GAME;
                int result1 = checkResult(arrayValue);
                if(result1 == LOSE_GAME){
                    showDialog("X da thang");

                }else if(result1 == WIN_GAME){
                    showDialog("0 da thang");

                }
            }

        }
    }



    public void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public int checkResult(int[][] arrayValue){
        int count = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0;j< 3; j ++){
                if(arrayValue[i][j] != DRAW_GAME) {
                    count ++;
                    int result = checkNodeWin(arrayValue, i, j);
                    if (result != DRAW_GAME) {
                        return result;
                    }
                }
            }
        }
        if(count == 9) return 9;
        return DRAW_GAME;
    }

    public int checkNodeWin(int[][] arrayValue, int x, int y){
        if(x + 2 < 3){
            if(arrayValue[x][y] == arrayValue[x + 1][y] && arrayValue[x][y] == arrayValue[x + 2][y]){
                return arrayValue[x][y];
            }
        }

        if(y + 2 < 3){
            if(arrayValue[x][y] == arrayValue[x][y+1] && arrayValue[x][y] == arrayValue[x][y + 2]){
                return arrayValue[x][y];
            }
        }

        if(x + 2 < 3 && y + 2 < 3){
            if(arrayValue[x][y] == arrayValue[x + 1][y + 1] && arrayValue[x][y] == arrayValue[x + 2][y + 2]){
                return arrayValue[x][y];
            }
        }

        if(x + 2 < 3 && y - 2 >= 0){
            if(arrayValue[x][y] == arrayValue[x + 1][y - 1] && arrayValue[x][y] == arrayValue[x + 2][y - 2]){
                return arrayValue[x][y];
            }
        }

        return DRAW_GAME;
    }

    public Node findBestMove(int[][] array) {
        Node bestNode = null;
        int valueBest = MIN;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j ++){
                if(array[i][j] == DRAW_GAME){
                    Node node = new Node(WIN_GAME, i,j);
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

    public int calMinMax(Node nodeChose, int[][] array, boolean isXGo, int dept) {
        int[][] arrayValue = new int[3][3];
        int count = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j ++){
                arrayValue[i][j] = array[i][j];
                if(arrayValue[i][j] != DRAW_GAME) count ++;
            }

        }

        arrayValue[nodeChose.x][nodeChose.y] = nodeChose.value;
        count ++;

        if (checkResult(arrayValue) == WIN_GAME){
            return MAX - dept;
        }
        if (checkResult(arrayValue) == LOSE_GAME) {
            return MIN + dept;
        }

        if(count == 3*3){

            return (isXGo)? DRAW_GAME + dept : DRAW_GAME - dept;
        }

        int valueResult;
        if(isXGo){
            valueResult = MIN;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j ++){
                    if(arrayValue[i][j] == DRAW_GAME){
                        Node node = new Node(WIN_GAME, i,j);
                        int valueNode = calMinMax(node,arrayValue,false, dept + 1);
                        valueResult = Math.max(valueResult, valueNode);
                    }
                }

            }

        }else{
            valueResult = MAX;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j ++){
                    if(arrayValue[i][j] == DRAW_GAME){
                        Node node = new Node(LOSE_GAME, i,j);
                        int valueNode = calMinMax(node,arrayValue,true, dept + 1);
                        valueResult = Math.min(valueResult, valueNode);
                    }
                }

            }

        }

        return valueResult;
    }


    public class Node{
        public int x;
        public int y;
        public int value;

        public Node(int value, int x, int y){
            this.value = value;
            this.x = x;
            this.y = y;
        }
    }

}
