package com.example.trente.myapplication;

import android.icu.lang.UScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
import com.example.trente.myapplication.user.UserListRequest;
import com.example.trente.myapplication.user.UserListResponse;
import com.example.trente.myapplication.user.UserModel;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = (Button) findViewById(R.id.button_load);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });


    }


    private void processLogin() {
        if (!Utils.isNetworkAvailable(this)){
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
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.optString("users");

                    List<UserModel> list = LoganSquare.parseList(object.optString("users"),UserModel.class);
//                    String key = list.get(0).userId;
                    String status2 = status;
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                String status = LoganSquare.parse("status",String);
            }
        };
        UserListRequest request = new UserListRequest(listener);
//        request.setEmail(email);
//        request.setPassword(password);
//        request.setDeviceToken(token);
        App.addRequest(request, "Login");

    }

//    private class LoginResponseListener extends OnResponseListener<LoginResponse>{
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            onLoginError("Có lỗi xảy ra, vui lòng thử lại.");
//            super.onErrorResponse(error);
//        }
//
//        @Override
//        public void onResponse(LoginResponse response) {
//            super.onResponse(response);
//            int status_code = response.getStatus_code();
//            if (status_code == 0){
//                onLoginSuccess(response.getLogin().getToken(), response.getLogin().getGarage());
//                showMessage(response.getMessage());
//            }else {
//                onLoginError(response.getMessage());
//            }
//        }
//    }
}
