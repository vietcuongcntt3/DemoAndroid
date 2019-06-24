package com.example.trente.myapplication.Tictactoe;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.FragmentBase.MyFragment;
import com.example.trente.myapplication.Tictactoe.Model.Heuristic;
import com.example.trente.myapplication.Tictactoe.Model.ItemModel;
import com.example.trente.myapplication.Tictactoe.Model.MinMaxModel;
import com.example.trente.myapplication.Tictactoe.Model.NoteModel;
import com.example.trente.myapplication.Tictactoe.view.MyCardView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuongnv on 6/18/19.
 */

public class GamePlayFragment extends MyFragment {
    public static final int maxdept = 9;
    public static final int numberline = 12;
    public static final int numberSameWin = 5;
    public MyCardView gameTable;
    public boolean enableTapGame = true;
    public boolean isX = true;
    int[][] arrayValue = new int[numberline][numberline];
    public final int ME = 1;
    public final int YOU = 2;
    public static final int DEFAULT = 0;
    public static final int DRAWGAME = -1;
    public String itemMe="x";
    public String itemYou = "o";

    Map<String, Bitmap> mapImage = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        super.initData();
        if(isX){
            itemMe = "x";
            itemYou="o";
        }else {
            itemMe = "o";
            itemYou ="x";
        }
        mapImage.put("x",BitmapFactory.decodeResource(getResources(), R.drawable.x));
        mapImage.put("o",BitmapFactory.decodeResource(getResources(), R.drawable.o));
        gameTable = (MyCardView)getView().findViewById(R.id.gameplay_view);

        

        gameTable.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(enableTapGame){
                    int x = gameTable.getLocationXY(event.getX());
                    int y = gameTable.getLocationXY(event.getY());

                    Log.e("cuongxy", x + "    |    " + y);
                    if(arrayValue[x][y]== DEFAULT){
                        enableTapGame = false;
                        arrayValue[x][y] = ME;
                        ItemModel item = new ItemModel(mapImage.get(itemMe), x, y);
                        gameTable.items.add(item);
                        gameTable.invalidate();

                        int result = MinMaxModel.checkResult(arrayValue);
                        if(result == ME){
                            showDialog(itemMe + " win!", true);
                        }else if(result == DRAWGAME) {
                            showDialog("Draw Game! ", true);
                        }else {
//                            MinMaxModel minMaxModel = new MinMaxModel(YOU, ME);
//                            NoteModel best = minMaxModel.findBestMove(arrayValue);

                            ControlTictacToe minMaxModel = new ControlTictacToe();
                            NoteModel best = minMaxModel.AI(arrayValue, 2);

                            ItemModel item2 = new ItemModel(mapImage.get(itemYou), best.x, best.y);
                            gameTable.items.add(item2);
                            gameTable.invalidate();

                            arrayValue[best.x][best.y] = YOU;

                            result = MinMaxModel.checkResult(arrayValue);
                            if(result == YOU){
                                showDialog(itemYou + " win!", true);
                            }else if(result == DRAWGAME) {
                                showDialog("Draw Game! ", true);
                            }
                            enableTapGame = true;
                        }

                    }
                }
                return false;
            }
        });
    }

    public void showDialog(String message, final boolean isEnd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(isEnd){
                    getFragmentManager().popBackStack();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
