package com.example.trente.myapplication.base;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by ChienNV on 11/22/16.
 */

public abstract class BaseRequest<T> extends Request<T> {

    protected static final Gson mGson = new GsonBuilder()
            .create();

    protected Type mType;
    protected Response.Listener<T> mListener;

    public BaseRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }
}
