package com.example.trente.myapplication.Tictactoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.FragmentBase.MyFragment;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.Tictactoe.ultils.APIConfig;
import com.example.trente.myapplication.Tictactoe.ultils.Const;

import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuongnv on 6/14/19.
 */

public class RoomDetailFragment extends MyFragment {
    public RoomModel room;
    public boolean isAdmin = false;

    public TextView player1Name;
    public TextView player2Name;
    public Button btnClear;
    public Button btnStart;
    public Button btnCancel;

    public String response = "";
    public Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_detail, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    protected void initData() {
        super.initData();
        int[][]data = new int[Const.NUMBER_ROWS][Const.NUMBER_ROWS];
//        Log.e("cuong1", data.toString());
        if(room.creater_name != null) {
            player1Name.setText(room.creater_name);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                if(isAdmin){
                    deleteRoom(room.roomid);
                }else {
                    deleteRoomJoiner(room.roomid);
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        if(isAdmin){
            btnStart.setVisibility(View.VISIBLE);
            btnClear.setVisibility(View.VISIBLE);
            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRoomJoiner(room.roomid);
                }
            });

        }else {
            btnStart.setVisibility(View.INVISIBLE);
            btnClear.setVisibility(View.INVISIBLE);
        }

        timer = new Timer();

    }

    @Override
    protected void initView() {
        super.initView();
        player1Name = (TextView)getView().findViewById(R.id.txt_name1);
        player2Name = (TextView)getView().findViewById(R.id.txt_name2);
        btnClear = (Button)getView().findViewById(R.id.btn_clear);
        btnStart = (Button)getView().findViewById(R.id.btn_start);
        btnCancel = (Button)getView().findViewById(R.id.btn_cancel);
    }

    public void loadRoom() {
        Map<String, String> params = new HashMap<>();
        params.put("roomid", room.roomid);
        getRequest(APIConfig.API_GET_ROOM, params);
    }

    @Override
    public void onGetSuccessResponse(JSONObject response, String responseUrl) {
        super.onGetSuccessResponse(response, responseUrl);
        updateData(response, responseUrl);
    }

    public void updateData(JSONObject response, String responseUrl){
        if(!this.response.equals(response.toString())){
            this.response = response.toString();
            try {
                if(APIConfig.API_GET_ROOM.equals(responseUrl)){
                    RoomModel room = LoganSquare.parse(response.optString("room"), RoomModel.class);
                    if(room != null){
                        if(!isAdmin && room.joiner_id == null){
                            timer.cancel();
                            getFragmentManager().popBackStack();
                        }else {
                            this.room = room;
                            showRoom(room);
                            if(Const.ROOM_STATUS_PLAYING.equals(room.status) && !isAdmin){
                                GamePlayFragment fragment = new GamePlayFragment();
                                fragment.isX = false;
                                fragment.valueMe = 2;
                                this.room.status = Const.ROOM_STATUS_PLAYING;
                                fragment.gameType = Const.GAME_TYPE_ONLINE;
                                fragment.room = this.room;
                                ((TictacActivity)getActivity()).addFragment(fragment);
                            }
                        }
                    }else {
                        timer.cancel();
                        getFragmentManager().popBackStack();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void showRoom(RoomModel room){
        if(room.creater_name != null){
            player1Name.setText(room.creater_name);
        }
        if(room.joiner_name != null){
            player2Name.setText(room.joiner_name);
            if(isAdmin){
                btnClear.setVisibility(View.VISIBLE);
            }
        }else {
            player2Name.setText("");
            if(isAdmin){
                btnClear.setVisibility(View.INVISIBLE);
            }
        }

    }

    public void deleteRoomJoiner(String roomId){
        Map<String, String> params = new HashMap<>();
        params.put("roomid", roomId);
        postRequest(APIConfig.API_DELETE_ROOM_JOINER, params);
    }

    @Override
    public void onPostSuccessResponse(JSONObject response, String responseUrl) {
        super.onPostSuccessResponse(response, responseUrl);
        if(APIConfig.API_DELETE_ROOM_JOINER.equals(responseUrl)) {
            if(!isAdmin){
                getFragmentManager().popBackStack();
            }
        }else if(APIConfig.API_DELETE_ROOM.equals(responseUrl)){
            getFragmentManager().popBackStack();
        }else if(APIConfig.API_UPDATE_ROOM_DATA.equals(responseUrl)){
            GamePlayFragment fragment = new GamePlayFragment();
            fragment.isX = true;
            fragment.valueMe = 1;
            fragment.room = this.room;
            fragment.gameType = Const.GAME_TYPE_ONLINE;
            ((TictacActivity)getActivity()).addFragment(fragment);
        }
    }

    public void deleteRoom(String roomId){
        Map<String, String> params = new HashMap<>();
        params.put("roomid", roomId);
        postRequest(APIConfig.API_DELETE_ROOM, params);
    }

    @Override
    public void pauseMyFragment(){
        super.pauseMyFragment();
        timer.cancel();

    }

    @Override
    public void resumeMyFragment(){
        super.resumeMyFragment();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadRoom();
            }
        },0, 1000);


    }

    public void startGame(){
        Map<String, String> params = new HashMap<>();
        room.status = Const.ROOM_STATUS_PLAYING;
        params.put("roomid", room.roomid);
        params.put("status", room.status);
        int[][]data = new int[Const.NUMBER_ROWS][Const.NUMBER_ROWS];
        params.put("data", Const.convertArrayToString(data));

        params.put("turnid","1");
        params.put("newChose","-1");
        postRequest(APIConfig.API_UPDATE_ROOM_DATA, params);

        Log.e("cuong", params.toString());
    }


}
