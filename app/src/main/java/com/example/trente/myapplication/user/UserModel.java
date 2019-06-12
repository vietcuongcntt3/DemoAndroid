package com.example.trente.myapplication.user;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class UserModel implements Serializable {
    @SerializedName("user_name")
    public String user_name;
    @SerializedName("created_date")
    public String created_date;
    @SerializedName("token")
    public String token;

    @SerializedName("point_vote")
    public int point_vote;

    @SerializedName("user_id")
    public String user_id;
}
