package com.example.trente.myapplication.base;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ChienNV on 10/24/16.
 */

public class BasePostRequest<T> extends BaseRequest<T> {

    private Map<String, String> mParams = new HashMap<>();

    protected static final Gson mGson = new GsonBuilder()
            .create();

    protected Type mType;
    protected Response.Listener<T> mListener;

    public BasePostRequest(String url, Type type, OnResponseListener<T> listener) {
        super(Request.Method.POST, url,listener);
        mType = type;
        mListener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i("Response post: ",json);

            return (Response<T>) Response.success
                    (
                            mGson.fromJson(json, mType),
                            HttpHeaderParser.parseCacheHeaders(response)
                    );
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    public void setParam(String key, String value){
        mParams.put(key, value);
    }


}
