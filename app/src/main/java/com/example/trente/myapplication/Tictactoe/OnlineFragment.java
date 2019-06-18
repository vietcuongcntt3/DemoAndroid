package com.example.trente.myapplication.Tictactoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.FragmentBase.MyFragment;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.Tictactoe.ultils.APIConfig;
import com.example.trente.myapplication.Tictactoe.ultils.SharedPreferencesUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuongnv on 6/13/19.
 */

public class OnlineFragment extends MyFragment{
    public ListView lstRooms;
    public String response = "";
    public ArrayAdapter<String> adapter;
    public List<RoomModel> rooms = new ArrayList<>();
    public Button btAddRoom;
    public Timer timer;
    public String userId;
    public String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("cuong", "onCreateView");
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        btAddRoom = (Button)getView().findViewById(R.id.btn_right);
        lstRooms = (ListView) getView().findViewById(R.id.lst_room);
        btAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRom();
            }
        });
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1);
        lstRooms.setAdapter(adapter);
        lstRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoomModel room = rooms.get(i);
                updateRoomJoiner(room.roomid, userId, userName);
            }
        });

        this.response = "";
        timer = new Timer();

        SharedPreferencesUtils mShare = new SharedPreferencesUtils(getContext());
        userName = mShare.readStringPreference("userName","");
        userId = mShare.readStringPreference("userId", "");
    }

    public void loadRooms() {
        Map<String, String> params = new HashMap<>();
        getRequest(APIConfig.API_ROOMS, params);
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
                if(APIConfig.API_ROOMS.equals(responseUrl)) {
                    List<RoomModel> list = LoganSquare.parseList(response.optString("rooms"), RoomModel.class);
//                adapter.clear();
                    List<String> roomsString = new ArrayList<>();
                    for (RoomModel room : list) {
                        if (room.joiner_id != null && room.joiner_id != "") {
                            roomsString.add(room.roomname + " (2/2) ");
                        } else {
                            roomsString.add(room.roomname + " (1/2) ");
                        }
                    }
                    this.rooms = list;
                    adapter.clear();
                    adapter.addAll(roomsString);
                    adapter.notifyDataSetChanged();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onPostSuccessResponse(JSONObject response, String responseUrl) {
        super.onPostSuccessResponse(response, responseUrl);
        try{
            if(APIConfig.API_INSERT_ROOM.equals(responseUrl)){
                RoomModel room = LoganSquare.parse(response.optString("room"), RoomModel.class);
                RoomDetailFragment fragment = new RoomDetailFragment();
                fragment.isAdmin = true;
                fragment.room = room;
                ((TictacActivity)getActivity()).addFragment(fragment);
            }else if(APIConfig.API_UPDATE_ROOM_JOINER.equals(responseUrl)){
                RoomModel room = LoganSquare.parse(response.optString("room"), RoomModel.class);
                RoomDetailFragment fragment = new RoomDetailFragment();
                fragment.isAdmin = false;
                fragment.room = room;
                ((TictacActivity)getActivity()).addFragment(fragment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void registerRom(){
        Map<String, String> params = new HashMap<>();
        params.put("roomname", userName);
        params.put("creatername", userName);
        params.put("createrid", userId);
        postRequest(APIConfig.API_INSERT_ROOM, params);
    }

    public void updateRoomJoiner(String roomId, String joinerId, String joinerName){
        Map<String, String> params = new HashMap<>();
        params.put("joinername", joinerName);
        params.put("joinerid", joinerId);
        params.put("roomid", roomId);
        postRequest(APIConfig.API_UPDATE_ROOM_JOINER, params);
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
                loadRooms();
            }
        },0, 1000);


    }



}
