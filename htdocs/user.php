<?php
class user{
 
    // database connection and table name
    private $conn;
    private $table_name = "user";
 
    // object properties
    public $userid;
    public $username;
    public $date;
    public $token;
    public $point;
    
    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // get all user
    function getUsers(){
        $query = "SELECT * FROM ".$this->table_name;
        $result = mysqli_query($this->conn, $query);
        return $result;
    }

    // insert user
    function insertUser(){
        $query = "INSERT INTO ".$this->table_name."(user_name, created_date, token, point_vote) VALUES ('".$this->username."',NOW(),'".$this->token."','".$this->point."')";
        $result = mysqli_query($this->conn, $query);
        if ($result) {
            $this->userid = mysqli_insert_id($this->conn);
            $row = $this->getUser();
            $this->date = $row["created_date"];
            $this->username = $row["user_name"];
            $this->token = $row["token"];
            $this->point = $row["point_vote"];
            return $this;
        }
        return null;
    }

    function getUser(){
        $query = "SELECT * FROM ".$this->table_name." WHERE user_id = '".$this->userid."'";
        $result = mysqli_query($this->conn, $query);
        $row = mysqli_fetch_assoc($result);
        return $row;
    }

    function updateUser(){
        $query = "UPDATE ".$this->table_name." SET user_name = '".$this->username."', token = '".$this->token."', point_vote = '".$this->point."' WHERE user_id ='".$this->userid."'";
        $result = mysqli_query($this->conn, $query);
        return $result;
    }

    function deleteUser(){
        $query = "DELETE  FROM ".$this->table_name." WHERE user_id = '".$this->userid."'";
        $result = mysqli_query($this->conn, $query);
        return $result;
    }
}