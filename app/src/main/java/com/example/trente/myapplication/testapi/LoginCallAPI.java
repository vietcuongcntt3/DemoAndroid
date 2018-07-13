package com.example.trente.myapplication.testapi;


import com.example.trente.myapplication.App;
import com.example.trente.myapplication.base.OnResponseListener;

/**
 * Created by ChienNV on 10/25/16.
 */

public class LoginCallAPI {

    public void processForgotPassword(String email, String password, String token, OnResponseListener<LoginResponse> listener){
        LoginRequest request = new LoginRequest(listener);
        request.setEmail(email);
        request.setPassword(password);
        request.setDeviceToken(token);
        App.addRequest(request, "Login");
    }
}
