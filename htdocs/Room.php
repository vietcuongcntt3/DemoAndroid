<?php
class Room{
 
    // database connection and table name
    private $conn;
    private $table_name = "room";
 
    // object properties
    public $roomid;
    public $roomname;
    public $createrid;
    public $createrdate;
    public $creatername;
    public $joinerid;
    public $joinerdate;
    public $joinername;
    public $data;
    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // get all rooms
    function getRoomsList(){
        $query = "SELECT * FROM ".$this->table_name;
        $result = mysqli_query($this->conn, $query);
        return $result;
    }

    // insert user
    function insertRoom(){
        $query = "INSERT INTO ".$this->table_name."(roomname, creater_id, creater_name, created_date) VALUES ('".$this->roomname."','".$this->createrid."','".$this->creatername."', NOW())";
        $result = mysqli_query($this->conn, $query);
        if ($result) {
            $this->roomid = mysqli_insert_id($this->conn);
            $row = $this->getRoom();
            $this->createrdate = $row["created_date"];
            $this->roomname = $row["roomname"];
            $this->createrid = $row["creater_id"];
            $this->creatername = $row["creater_name"];
            return $this;
        }
        return null;
    }

    function getRoom(){
        $query = "SELECT * FROM ".$this->table_name." WHERE roomid = '".$this->roomid."'";
        $result = mysqli_query($this->conn, $query);
        $row = mysqli_fetch_assoc($result);
        return $row;
    }

    function updateRoomJoiner(){
        $query = "UPDATE ".$this->table_name." SET joiner_name = '".$this->joinername."', joiner_id = '".$this->joinerid."', join_date = NOW() WHERE roomid ='".$this->roomid."'";
        $result = mysqli_query($this->conn, $query);
        return $result;
    }

    function deleteRoomJoiner(){
        $query = "UPDATE ".$this->table_name." SET joiner_name = null, joiner_id = null WHERE roomid ='".$this->roomid."'";
        $result = mysqli_query($this->conn, $query);
        return $result;
    }

    function updateRoomData(){
        $query = "UPDATE ".$this->table_name." SET data = '".$this->data."' WHERE roomid ='".$this->roomid."'";
        $result = mysqli_query($this->conn, $query);
        return $result;
    }

    function deleteRoom(){
        $query = "DELETE  FROM ".$this->table_name." WHERE roomid = '".$this->roomid."'";
        $result = mysqli_query($this->conn, $query);
        return $result;
    }
}