package com.example.trente.myapplication.testapi;


import com.example.trente.myapplication.Constant;
import com.example.trente.myapplication.base.BasePostRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.google.gson.reflect.TypeToken;


/**
 * Created by ChienNV on 10/25/16.
 */

public class LoginRequest extends BasePostRequest<LoginResponse> {

    public LoginRequest(OnResponseListener<LoginResponse> listener) {
        super(Constant.URL_LOGIN, new TypeToken<LoginResponse>() {
        }.getType(), listener);
    }

    public void setEmail(String email) {
        setParam("email", email);
    }
    public void setPassword(String password){
        setParam("password", password);
    }
    public void setDeviceToken(String token){
        setParam("device_token", token);
    }
}
