package com.example.trente.myapplication.user;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class UserModel implements Serializable {
    public String userName;
    public String userMail;
    public String userTel;

    @SerializedName("key")
    public String key;
}
