package com.example.trente.myapplication.Tictactoe.FragmentBase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.trente.myapplication.App;
import com.example.trente.myapplication.Tictactoe.TictacActivity;
import com.example.trente.myapplication.Tictactoe.ultils.APIConfig;
import com.example.trente.myapplication.base.BaseGetRequest;
import com.example.trente.myapplication.base.BasePostRequest;
import com.example.trente.myapplication.base.OnResponseListener;
import com.example.trente.myapplication.model.Utils;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by cuongnv on 6/17/19.
 */

public abstract class MyFragment extends Fragment{
    public boolean isNewFragment = true;

    public MyFragment(){
        super();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(isNewFragment){
            isNewFragment = false;
            initView();
            initData();
        }else {
            initData();
        }
    }

    protected void initView(){
    }

    protected void initData(){
    }

    public void getRequest(String url, Map<String, String> params) {
        if (!Utils.isNetworkAvailable(getContext())){
//            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }

        OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                onGetErrorResponse(error);
            }

            @Override
            public void onResponse(JsonObject response) {
                super.onResponse(response);
                try {
                    JSONObject object = new JSONObject(response.toString());
                    if("1".equals(object.optString("returncode"))){
                        String apiCode = object.optString("return_api");
                        onGetSuccessResponse(object, apiCode);
                    }else {
                        ((TictacActivity)getActivity()).showMessage("error!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String fUrl = BaseGetRequest.addParamsForUrl(APIConfig.getUrlAPI(url), params);
        BaseGetRequest request = new BaseGetRequest(fUrl, new TypeToken<JsonObject>(){}.getType(),listener);
        App.addRequest(request);

    }

    public void onGetErrorResponse(VolleyError error){

    }

    public void onGetSuccessResponse(JSONObject response, String responseUrl){

    }

    public void postRequest(String url, Map<String, String> params){

        if (!Utils.isNetworkAvailable(getActivity())){
            return;
        } else {
            OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    onPostErrorResponse(error);
                }

                @Override
                public void onResponse(JsonObject response) {
                    super.onResponse(response);
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        if("1".equals(object.optString("returncode"))){
                            String apiCode = object.optString("return_api");
                            onPostSuccessResponse(object, apiCode);
                        }else {
                            ((TictacActivity)getActivity()).showMessage("error!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            BasePostRequest request = new BasePostRequest(APIConfig.getUrlAPI(url),new TypeToken<JsonObject>(){}.getType(),listener);
            request.mParams = params;

            App.addRequest(request);

        }
    }

    public void onPostErrorResponse(VolleyError error){

    }

    public void onPostSuccessResponse(JSONObject response, String responseUrl){

    }

    @Override
    public void onPause() {
        super.onPause();
        pauseMyFragment();

    }

    @Override
    public void onResume() {
        super.onResume();
        resumeMyFragment();
    }

    public void pauseMyFragment(){
    }

    public void resumeMyFragment(){

    }
}
