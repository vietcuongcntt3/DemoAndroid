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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.BasePostRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuongnv on 6/14/19.
 */

public class RoomDetailFragment extends Fragment {
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

        player1Name = (TextView)getView().findViewById(R.id.txt_name1);
        if(room.creater_name != null) {
            player1Name.setText(room.creater_name);
        }
        player2Name = (TextView)getView().findViewById(R.id.txt_name2);
        btnClear = (Button)getView().findViewById(R.id.btn_clear);
        btnStart = (Button)getView().findViewById(R.id.btn_start);
        btnCancel = (Button)getView().findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdmin){
                    deleteRoom(room.roomid);
                }else {
                    updateRoomJoiner(room.roomid, null, null);
                }
            }
        });

        if(isAdmin){
            btnStart.setVisibility(View.VISIBLE);
            btnClear.setVisibility(View.VISIBLE);

        }else {
            btnStart.setVisibility(View.INVISIBLE);
            btnClear.setVisibility(View.INVISIBLE);
        }

        timer = new Timer();
        //Set the schedule function
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Magic here
                loadRoom();
            }
        },0, 1000);
    }

    public void loadRoom() {
        if (!Utils.isNetworkAvailable(getContext())){
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
        Map<String, String> params = new HashMap<>();
        params.put("roomid", room.roomid);

        String url = BaseGetRequest.addParamsForUrl("http://192.168.1.125:8888/apiroom.php/get_room", params);
        BaseGetRequest request = new BaseGetRequest(url, new TypeToken<JsonObject>(){}.getType(),listener);
//        request.setParam("roomid", room.roomid);
        App.addRequest(request);

    }

    public void updateData(String response){
        if(!this.response.equals(response)){
            this.response = response;
            try {
                JSONObject object = new JSONObject(response);
                if("1".equals(object.optString("returncode"))) {
                    RoomModel room = LoganSquare.parse(object.optString("room"), RoomModel.class);
                    this.room = room;
                    showRoom(room);
                }else {
                    getFragmentManager().popBackStack();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void showRoom(RoomModel room){
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
                            getFragmentManager().popBackStack();
                        }else {
                            ((TictacActivity)getActivity()).showMessage("error!");
                        }


                    } catch (JSONException e) {
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

    public void deleteRoom(String roomId){
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
                            getFragmentManager().popBackStack();
                        }else {
                            ((TictacActivity)getActivity()).showMessage("error!");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            BasePostRequest request = new BasePostRequest( "http://192.168.1.125:8888/apiroom.php/delete_room",
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("roomid", roomId);
            App.addRequest(request);

        }
    }


}
