package com.example.trente.myapplication.base;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by ChienNV on 11/22/16.
 */

public class OnResponseListener<T> implements Response.Listener<T>, Response.ErrorListener{


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(T response) {

    }
}
