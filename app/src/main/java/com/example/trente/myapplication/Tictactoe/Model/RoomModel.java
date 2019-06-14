package com.example.trente.myapplication.Tictactoe.Model;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by cuongnv on 6/13/19.
 */

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class RoomModel implements Serializable {

    public String roomid;
    public String roomname;
    public String data;

    public String creater_id;
    public String creater_name;
    public String created_date;

    public String joiner_id;
    public String joiner_date;
    public String joiner_name;

}

