package com.example.trente.myapplication.user;

//http://localhost:9000/api/user/list

import com.example.trente.myapplication.Constant;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.testapi.LoginResponse;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserListRequest extends BaseGetRequest<JsonObject> {

    public static String url = "http://192.168.1.125:9000/api/user/list";

    public UserListRequest(OnResponseListener<JsonObject> listener) {
        super(url, new TypeToken<JsonObject>(){}.getType(), listener);
    }
}