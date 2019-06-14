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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cuongnv on 6/13/19.
 */

public class OnlineFragment extends Fragment{
    public ListView lstRooms;
    public String response = "";
    public ArrayAdapter<String> adapter;
    public Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnRight = (Button)getView().findViewById(R.id.btn_right);

        lstRooms = (ListView) getView().findViewById(R.id.lst_room);
        adapter = new ArrayAdapter<String>(getContext(),R.layout.row_devices);

        lstRooms.setAdapter(adapter);
        lstRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                NoteDetailFragment noteDetailFragment = new NoteDetailFragment();
//                noteDetailFragment.noteId = adapter.notes.get(i).order_id;
//                ((MainActivity)getContext()).addFragment(noteDetailFragment);
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
        BaseGetRequest request = new BaseGetRequest("https://cnv-tictactoe-test.000webhostapp.com/apiroom.php/room", new TypeToken<JsonObject>(){}.getType(),listener);
//        request.setEmail(email);
//        request.setPassword(password);
//        request.setDeviceToken(token);
//        request.setPara();
        App.addRequest(request, "Login");

    }

    public void updateData(String response){
        if(!this.response.equals(response)){
            this.response = response;
            try {
                JSONObject object = new JSONObject(response);
                List<RoomModel> list = LoganSquare.parseList(object.optString("rooms"),RoomModel.class);
                adapter.clear();
                List<String> roomsString = new ArrayList<>();
                for(RoomModel room : list){
                    if(room.joiner_id != null && room.joiner_id != ""){
                        roomsString.add(room.roomname + " (2/2) ");
                    }else {
                        roomsString.add(room.roomname + " (1/2) ");
                    }
                }
                adapter.addAll(roomsString);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
