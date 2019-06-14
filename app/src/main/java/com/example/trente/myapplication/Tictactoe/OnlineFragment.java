package com.example.trente.myapplication.Tictactoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.Tictactoe.ultils.SharedPreferencesUtils;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.BasePostRequest;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuongnv on 6/13/19.
 */

public class OnlineFragment extends Fragment{
    public ListView lstRooms;
    public String response = "";
    public ArrayAdapter<String> adapter;
    public List<RoomModel> rooms = new ArrayList<>();
    public Timer timer;
    public String userId;
    public String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnRight = (Button)getView().findViewById(R.id.btn_right);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRom();
            }
        });

        lstRooms = (ListView) getView().findViewById(R.id.lst_room);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1);

        lstRooms.setAdapter(adapter);
        lstRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoomModel room = rooms.get(i);
                updateRoomJoiner(room.roomid, userId, userName);
            }
        });

        timer = new Timer();
        //Set the schedule function
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            // Magic here
                loadRooms();
            }
            },0, 1000);
        SharedPreferencesUtils mShare = new SharedPreferencesUtils(getContext());
        userName = mShare.readStringPreference("userName","");
        userId = mShare.readStringPreference("userId", "");
    }

    public void loadRooms() {
        if (!Utils.isNetworkAvailable(getContext())){
//            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }

        OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }

            @Override
            public void onResponse(JsonObject response) {

                super.onResponse(response);
                updateData(response.toString());
            }
        };
        BaseGetRequest request = new BaseGetRequest("http://192.168.1.125:8888/apiroom.php/rooms", new TypeToken<JsonObject>(){}.getType(),listener);
        App.addRequest(request, "Login");

    }

    public void updateData(String response){
        if(!this.response.equals(response)){
            this.response = response;
            try {
                JSONObject object = new JSONObject(response);
                List<RoomModel> list = LoganSquare.parseList(object.optString("rooms"),RoomModel.class);
//                adapter.clear();
                List<String> roomsString = new ArrayList<>();
                for(RoomModel room : list){
                    if(room.joiner_id != null && room.joiner_id != ""){
                        roomsString.add(room.roomname + " (2/2) ");
                    }else {
                        roomsString.add(room.roomname + " (1/2) ");
                    }
                }
                this.rooms = list;
                adapter.addAll(roomsString);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void registerRom(){
        if (!Utils.isNetworkAvailable(getActivity())){
            return;
        } else {
            OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
                @Override
                public void onErrorResponse(VolleyError error) {

                    super.onErrorResponse(error);
                }

                @Override
                public void onResponse(JsonObject response) {

                    super.onResponse(response);
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        if("1".equals(object.optString("returncode"))){
                            RoomModel room = LoganSquare.parse(object.optString("room"), RoomModel.class);

                            RoomDetailFragment fragment = new RoomDetailFragment();
                            fragment.isAdmin = true;
                            fragment.room = room;
                            ((TictacActivity)getActivity()).addFragment(fragment);
//                            ((TictacActivity)getActivity()).showMessage("register success!");
                        }else {
                            ((TictacActivity)getActivity()).showMessage("error!");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            BasePostRequest request = new BasePostRequest( "http://192.168.1.125:8888/apiroom.php/insert_room",
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("roomname", userName);
            request.setParam("creatername", userName);
            request.setParam("createrid", userId);
            App.addRequest(request);

        }
    }

    public void updateRoomJoiner(String roomId, String joinerId, String joinerName){
        if (!Utils.isNetworkAvailable(getActivity())){
            return;
        } else {
            OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
                @Override
                public void onErrorResponse(VolleyError error) {

                    super.onErrorResponse(error);
                }

                @Override
                public void onResponse(JsonObject response) {

                    super.onResponse(response);
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        if("1".equals(object.optString("returncode"))){
                            RoomModel room = LoganSquare.parse(object.optString("room"), RoomModel.class);
                            RoomDetailFragment fragment = new RoomDetailFragment();
                            fragment.isAdmin = false;
                            fragment.room = room;
                            ((TictacActivity)getActivity()).addFragment(fragment);
                        }else {
                            ((TictacActivity)getActivity()).showMessage("error!");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            BasePostRequest request = new BasePostRequest( "http://192.168.1.125:8888/apiroom.php/update_room_joiner",
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("joinername", joinerName);
            request.setParam("joinerid", joinerName);
            request.setParam("roomid", roomId);
            App.addRequest(request);

        }
    }



}
