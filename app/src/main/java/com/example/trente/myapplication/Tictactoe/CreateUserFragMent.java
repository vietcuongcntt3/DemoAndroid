package com.example.trente.myapplication.Tictactoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.Constant;
import com.example.trente.myapplication.R;
import com.example.trente.myapplication.Tictactoe.Model.RoomModel;
import com.example.trente.myapplication.Tictactoe.ultils.SharedPreferencesUtils;
import com.example.trente.myapplication.base.BasePostRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
import com.example.trente.myapplication.user.UserModel;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by cuongnv on 6/13/19.
 */

public class CreateUserFragMent extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_user, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EditText edtName = (EditText)getView().findViewById(R.id.edt_name);

        Button btnSubmit = (Button)getView().findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText() == null|| edtName.getText().toString().length()< 3 || edtName.getText().toString().length() > 20){
                    ((TictacActivity)getActivity()).showMessage("Please input name > 3 character and < 20 character");
                }else {

                    registerUser(edtName.getText().toString());
                }
            }
        });

    }

    public void registerUser(String name){

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
                            UserModel user = LoganSquare.parse(object.optString("user"), UserModel.class);
                            SharedPreferencesUtils mShare = new SharedPreferencesUtils(getContext());
                            mShare.writeStringPreference("userId", user.userid);
                            mShare.writeStringPreference("userName", user.username);

                            MainFragment fragment = new MainFragment();
                            ((TictacActivity)getActivity()).addFragment(fragment);
                            ((TictacActivity)getActivity()).showMessage("register success!");
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

            BasePostRequest request = new BasePostRequest( "http://192.168.1.125:8888/api.php/insert_user",
                    new TypeToken<JsonObject>(){}.getType(),listener);

            request.setParam("username", name);
            request.setParam("token", Utils.APP_TOKEN);
            request.setParam("point", "100");
            App.addRequest(request, "CompleteOrder");

        }
    }



}
