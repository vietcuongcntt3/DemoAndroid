<?php
require 'restful_api.php';
include 'database.php';
include 'Room.php';

class api extends restful_api {

	function __construct(){
		parent::__construct();
	}

	function rooms(){
		if ($this->method == 'GET'){
            $data = array();
            $data["return_api"]="apiroom.php/rooms";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 $data["returncode"]="1";
                 $data["messages"]="";
              // initialize object
                $room = new Room($conn);
                $result = $room->getRoomsList();
    			$rooms = array();
    			while($row = mysqli_fetch_assoc($result)) {
                    $rooms[] = $row;
                }
		  	    $data["rooms"] = $rooms;
		  	     mysqli_close($conn);
			
            }
			$this->response(200, $data);
		}
    }
    
    function insert_room(){
		if ($this->method == 'POST'){

            $data = array();
            $data["return_api"]="apiroom.php/insert_room";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $room = new Room($conn);
 
                $room->roomname = $_POST["roomname"];
                $room->createrid = $_POST["createrid"];
                $room->creatername    = $_POST["creatername"];
                $result = $room->insertRoom();
                if($result != null){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    $data["room"] = $result;
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Systerm error";
                }
                mysqli_close($conn);
            }
			$this->response(200, $data);
		}
    }

    function update_room_joiner(){
		if ($this->method == 'POST'){

            $data = array();
            $data["return_api"]="apiroom.php/update_room_joiner";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $room = new Room($conn);
                $room->roomid = $_POST["roomid"];
                $room->joinerid = $_POST["joinerid"];
                $room->joinername    = $_POST["joinername"];
                $result = $room->updateRoomJoiner();
                if($result != null){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    $roomDetail  = $room->getRoom();
                    $data["room"] = $roomDetail;
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Systerm error";
                }
                mysqli_close($conn);
            }
			$this->response(200, $data);
		}
    }

    function delete_room_joiner(){
		if ($this->method == 'POST'){

            $data = array();
            $data["return_api"]="apiroom.php/delete_room_joiner";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $room = new Room($conn);
                $room->roomid = $_POST["roomid"];
                $result = $room->deleteRoomJoiner();
                if($result != null && $result){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Systerm error";
                }
                mysqli_close($conn);
            }
			$this->response(200, $data);
		}
    }

    function update_room_data(){
		if ($this->method == 'POST'){

            $data = array();
            $data["return_api"]="apiroom.php/update_room_data";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $room = new Room($conn);
                $room->roomid = $_POST["roomid"];
                $room->data = $_POST["data"];
                $result = $room->updateRoomData();
                if($result != null){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    $roomDetail  = $room->getRoom();
                    $data["room"] = $roomDetail;
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Systerm error";
                }
                mysqli_close($conn);
            }
			$this->response(200, $data);
		}
    }
    
    function delete_room(){
		if ($this->method == 'POST'){
            $data = array();
            $data["return_api"]="apiroom.php/delete_room";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $room = new room($conn);
                $room->roomid = $_POST["roomid"];
                $result = $room->deleteRoom();
                if($result != null){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    $data["result"] = $result;
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Systerm error";
                }
                mysqli_close($conn);
            }
			$this->response(200, $data);
		}
    }
    
    function get_room(){
		if ($this->method == 'GET'){

            $data = array();
            $data["return_api"]="apiroom.php/get_room";
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $room = new room($conn);
 
                $room->roomid = $_GET["roomid"];
                $result = $room->getRoom();
                    $data["returncode"]="1";
                if($result != null){
                    $data["messages"]="";
                    $data["room"] = $result;
                }else {
                    $data["messages"]="Not found";
                }
                mysqli_close($conn);
                
			
            }
			$this->response(200, $data);
		}
	}
}

$room_api = new api();

?>
