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

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
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

public class MainFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_tictactoe, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnOnline = (Button)getView().findViewById(R.id.btn_online);
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineFragment fragment = new OnlineFragment();
                ((TictacActivity)getActivity()).addFragment(fragment);
            }
        });

        Button btnOffline = (Button)getView().findViewById(R.id.btn_offline);
        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfflineFragment fragment = new OfflineFragment();
                ((TictacActivity)getActivity()).addFragment(fragment);
            }
        });

    }



}
