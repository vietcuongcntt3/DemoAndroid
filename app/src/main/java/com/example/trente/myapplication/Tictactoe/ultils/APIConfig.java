package com.example.trente.myapplication.Tictactoe.ultils;

/**
 * Created by cuongnv on 6/17/19.
 */

public class APIConfig {
//    public static final String mhost="http://192.168.1.125:8888/";
    public static final String mhost="https://cnv-tictactoe-test.000webhostapp.com/";

    public static final String API_ROOMS                = "apiroom.php/rooms";
    public static final String API_INSERT_ROOM          = "apiroom.php/insert_room";
    public static final String API_UPDATE_ROOM_JOINER   = "apiroom.php/update_room_joiner";
    public static final String API_DELETE_ROOM_JOINER   = "apiroom.php/delete_room_joiner";
    public static final String API_UPDATE_ROOM_DATA     = "apiroom.php/update_room_data";
    public static final String API_DELETE_ROOM          = "apiroom.php/delete_room";
    public static final String API_GET_ROOM             = "apiroom.php/get_room";

    public static final String API_INSERT_USER          = "api.php/insert_user";

    public static String getUrlAPI(String api){
        return mhost + api;
    }
}
