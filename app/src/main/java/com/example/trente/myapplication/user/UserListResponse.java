package com.example.trente.myapplication.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserListResponse implements Serializable {
    @SerializedName("status")
    String status;
}
