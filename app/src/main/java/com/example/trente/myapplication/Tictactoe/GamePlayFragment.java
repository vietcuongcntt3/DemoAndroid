package com.example.trente.myapplication.Tictactoe;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.FragmentBase.MyFragment;
import com.example.trente.myapplication.Tictactoe.Model.Heuristic;
import com.example.trente.myapplication.Tictactoe.Model.ItemModel;
import com.example.trente.myapplication.Tictactoe.Model.MinMaxModel;
import com.example.trente.myapplication.Tictactoe.Model.NoteModel;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.Tictactoe.ultils.APIConfig;
import com.example.trente.myapplication.Tictactoe.ultils.Const;
import com.example.trente.myapplication.Tictactoe.view.MyBoardView;
import com.example.trente.myapplication.Tictactoe.view.MyCardView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuongnv on 6/18/19.
 */

public class GamePlayFragment extends MyFragment {
    public Timer timer;
//    public static final int maxdept = 9;
//    public static final int numberline = 20;
//    public static final int numberSameWin = 5;
//    public MyCardView gameTable;
    public RoomModel room;
    public int gameType = Const.GAME_TYPE_P2;
    public boolean enableTapGame = true;
    public boolean isMeTouch = true;
    public boolean isX = true;
    int[][] arrayValue;
    public final int ME = 1;
    public final int YOU = 2;
    public int valueMe =1;
    public static final int DEFAULT = 0;
    public static final int DRAWGAME = -1;
    public String itemMe = "x";
    public String itemYou = "o";
    public MyBoardView mBoard;

    Map<String, Bitmap> mapImage = new HashMap<>();

    public String response = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        super.initData();
        arrayValue = new int[Const.NUMBER_ROWS][Const.NUMBER_ROWS];
        if (isX) {
            itemMe = "x";
            itemYou = "o";
        } else {
            itemMe = "o";
            itemYou = "x";
        }
        mapImage.put("x", BitmapFactory.decodeResource(getResources(), R.drawable.x));
        mapImage.put("o", BitmapFactory.decodeResource(getResources(), R.drawable.o));

        mBoard = new MyBoardView(getContext());
        final RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.rlt_table);
        relativeLayout.addView(mBoard);
        relativeLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = relativeLayout.getMeasuredWidth();
                int height = relativeLayout.getMeasuredHeight();
                mBoard.updateBegin(width, height);
            }
        });
        mBoard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mBoard.touchDownBoard(x, y);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int widthView = relativeLayout.getMeasuredWidth() - 6;
                        int heightView = relativeLayout.getMeasuredHeight() - 6;
                        mBoard.touchMoveBoard(x, y, widthView, heightView);
                        break;

                    case MotionEvent.ACTION_UP:
                        if (enableTapGame && !mBoard.isMove) {
                            int i = mBoard.getLocationXY(x, true);
                            int j = mBoard.getLocationXY(y, false);

                            if (arrayValue[i][j] == DEFAULT) {
                                switch (gameType){
                                    case Const.GAME_TYPE_P1:
                                        touchInBoardTypeP1(i, j);
                                        break;
                                    case Const.GAME_TYPE_P2:
                                        touchInBoardTypeP2(i, j);
                                        break;
                                    case Const.GAME_TYPE_ONLINE:
                                        touchInBoardTypeOnline(i, j);
                                        break;
                                    default:
                                        break;
                                }

                            }
                        }
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });
    }

    public void showDialog(String message, final boolean isEnd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (isEnd) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        builder.setNeutralButton("View again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
//                if (isEnd) {
//                    getFragmentManager().popBackStack();
//                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void touchInBoardTypeP1(int i, int j){

        enableTapGame = false;
        arrayValue[i][j] = ME;
        ItemModel item = new ItemModel(mapImage.get(itemMe), i, j);
        item.isDrawBackground = true;
        if(!mBoard.items.isEmpty()){
            mBoard.items.get(0).isDrawBackground = false;
//                                    itemFirst.isDrawBackground = false;
        }
        mBoard.items.add(0, item);
        mBoard.invalidate();

        int result = MinMaxModel.checkResult(arrayValue, mBoard);
        if (result == ME) {
            showDialog(itemMe + " win!", true);
        } else if (result == DRAWGAME) {
            showDialog("Draw Game! ", true);
        } else {

            ControlTictacToe minMaxModel = new ControlTictacToe();
            NoteModel best = minMaxModel.AI(arrayValue, 2);

            ItemModel item2 = new ItemModel(mapImage.get(itemYou), best.x, best.y);
            if(!mBoard.items.isEmpty()){
                mBoard.items.get(0).isDrawBackground = false;
//                                        itemFirst.isDrawBackground = false;
            }
            item2.isDrawBackground = true;
            mBoard.items.add(0,item2);
            mBoard.invalidate();

            arrayValue[best.x][best.y] = YOU;

            result = MinMaxModel.checkResult(arrayValue, mBoard);
            if (result == YOU) {
                showDialog(itemYou + " win!", true);
            } else if (result == DRAWGAME) {
                showDialog("Draw Game! ", true);
            }else {
                enableTapGame = true;
            }
        }

    }

    public void touchInBoardTypeP2(int i, int j){

//        enableTapGame = false;
        ItemModel item;
        if(isMeTouch) {
            arrayValue[i][j] = ME;
            item = new ItemModel(mapImage.get(itemMe), i, j);
        }else {
            arrayValue[i][j] = YOU;
            item = new ItemModel(mapImage.get(itemYou), i, j);
        }
        isMeTouch = !isMeTouch;
        item.isDrawBackground = true;
        if (!mBoard.items.isEmpty()) {
            mBoard.items.get(0).isDrawBackground = false;
//                                    itemFirst.isDrawBackground = false;
        }
        mBoard.items.add(0, item);
        mBoard.invalidate();


        int result = MinMaxModel.checkResult(arrayValue, mBoard);
        if (result == ME) {
            enableTapGame = false;
            showDialog(itemMe + " win!", true);
        }else if (result == YOU) {
            enableTapGame = false;
            showDialog(itemYou + " win!", true);
        } else if (result == DRAWGAME) {
            enableTapGame = false;
            showDialog("Draw Game! ", true);
        }
    }

    public void touchInBoardTypeOnline(int i, int j){
        enableTapGame = false;
        arrayValue[i][j] = valueMe;
        ItemModel item = new ItemModel(mapImage.get(itemMe), i, j);
        item.isDrawBackground = true;
        if(!mBoard.items.isEmpty()){
            mBoard.items.get(0).isDrawBackground = false;
//                                    itemFirst.isDrawBackground = false;
        }
        mBoard.items.add(0, item);
        mBoard.invalidate();

        int result = MinMaxModel.checkResult(arrayValue, mBoard);
        if (result == ME) {
            showDialog(itemMe + " win!", true);
            room.status = Const.ROOM_STATUS_ENDED;


        } else if (result == DRAWGAME) {
            showDialog("Draw Game! ", true);
            room.status = Const.ROOM_STATUS_ENDED;
        }
            updateRoom(i, j);

    }


    public void loadRoom() {
        Map<String, String> params = new HashMap<>();
        params.put("roomid", room.roomid);
        getRequest(APIConfig.API_GET_ROOM, params);
    }

    @Override
    public void onGetSuccessResponse(JSONObject response, String responseUrl) {
        super.onGetSuccessResponse(response, responseUrl);
        if(!this.response.equals(response.toString())){
            this.response = response.toString();
            try {
                if(APIConfig.API_GET_ROOM.equals(responseUrl)){
                    RoomModel room = LoganSquare.parse(response.optString("room"), RoomModel.class);
                    String newChose = response.optString("newChose");
                    if(room != null) {
                        this.room = room;
                        if(!Const.ROOM_STATUS_ENDED.equals(this.room.status) && !this.room.turnid.equals(this.valueMe)){
                            enableTapGame = true;
                        }
                        int chose = -1;
                        if(!newChose.equals("")){
                            chose = Integer.parseInt(newChose);
                        }
                        Showdata(room, chose);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Showdata(RoomModel room, int newChose){
        this.arrayValue = Const.convertStringToArray(room.data, Const.NUMBER_ROWS, Const.NUMBER_ROWS);
        mBoard.items.clear();
        for(int i = 0; i < Const.NUMBER_ROWS; i ++){
            for(int j = 0; j < Const.NUMBER_ROWS; j++){
                if(this.arrayValue[i][j] != DEFAULT){
                    ItemModel item;
                    if(arrayValue[i][j] == valueMe){
                        item = new ItemModel(mapImage.get(itemMe), i, j);
                    }else {
                        item = new ItemModel(mapImage.get(itemYou), i, j);
                    }
                    if(i * Const.NUMBER_ROWS + j == newChose){
                        item.isDrawBackground = true;
                    }
                }
            }
        }
        mBoard.invalidate();
        int result = MinMaxModel.checkResult(arrayValue, mBoard);
        if (result == valueMe) {
            showDialog(itemMe + " win!", true);
            room.status = Const.ROOM_STATUS_ENDED;


        } else if (result == DRAWGAME) {
            showDialog("Draw Game! ", true);
            room.status = Const.ROOM_STATUS_ENDED;
        } else if(result != DEFAULT){
            showDialog(itemYou + " win!", true);
            room.status = Const.ROOM_STATUS_ENDED;
        }
    }

    public void updateRoom(int x, int y){
        Map<String, String> params = new HashMap<>();
        params.put("roomid", room.roomid);
        params.put("status", room.status);
        params.put("data", Const.convertArrayToString(arrayValue));

        params.put("turnid", valueMe + "");
        int chose = (x * Const.NUMBER_ROWS + y);
        params.put("newChose", chose + "" );
        postRequest(APIConfig.API_UPDATE_ROOM_DATA, params);
    }

    @Override
    public void onPostSuccessResponse(JSONObject response, String responseUrl) {
        super.onPostSuccessResponse(response, responseUrl);
        if(APIConfig.API_UPDATE_ROOM_DATA.equals(responseUrl)){
            Log.e("succes", "success");
        }
    }

    @Override
    public void pauseMyFragment(){
        super.pauseMyFragment();
        if(gameType == Const.GAME_TYPE_ONLINE){
            timer.cancel();
        }

    }

    @Override
    public void resumeMyFragment(){
        super.resumeMyFragment();
        if(gameType == Const.GAME_TYPE_ONLINE){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    loadRoom();
                }
            },0, 1000);
        }



    }

}
