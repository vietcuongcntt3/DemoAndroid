package com.example.trente.myapplication.user;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class UserModel implements Serializable {
    public String username;
    public String date;
    public String token;

    public int vote;

    public String userid;
}
