package com.example.trente.myapplication.Tictactoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trente.myapplication.R;

/**
 * Created by cuongnv on 6/13/19.
 */

public class OfflineFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("cuong", "onCreateView");
        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }
}
