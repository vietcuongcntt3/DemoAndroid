<?php
require 'restful_api.php';
include 'database.php';
include 'user.php';

class api extends restful_api {

	function __construct(){
		parent::__construct();
	}

	function user(){
		if ($this->method == 'GET'){
            $data = array();
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
                $user = new user($conn);
                $result = $user->getUsers();
    			$users = array();
    			while($row = mysqli_fetch_assoc($result)) {
                    $users[] = $row;
                }
		  	    $data["users"] = $users;
		  	     mysqli_close($conn);
			
            }
			$this->response(200, $data);
		}
    }
    
    function insert_user(){
		if ($this->method == 'POST'){

            $data = array();
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $user = new user($conn);
 
                $user->username = $_POST["username"];
                $user->token    = $_POST["token"];
                $user->point    = $_POST["point"];
                $result = $user->insertUser();
                if($result != null){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    $data["user"] = $result;
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Systerm error";
                }
                mysqli_close($conn);
            }
			$this->response(200, $data);
		}
    }

    function update_user(){
		if ($this->method == 'POST'){

            $data = array();
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $user = new user($conn);
                $user->userid = $_POST["userid"];
                $user->username = $_POST["username"];
                $user->token    = $_POST["token"];
                $user->point    = $_POST["point"];
                $result = $user->updateUser();
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
    
    function delete_user(){
		if ($this->method == 'POST'){
            $data = array();
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $user = new user($conn);
                $user->userid = $_POST["userid"];
                $result = $user->deleteUser();
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
    
    function get_user(){
		if ($this->method == 'GET'){

            $data = array();
            $database = new database();
            $conn = $database->getConnection();
            // Check connection
            if (!$conn) {
                $data["returncode"]="0";
                $data["messages"]="Connection to Database failed";
            }else {
                 
              // initialize object
                $user = new user($conn);
 
                $user->userid = $_GET["userid"];
                $result = $user->getUser();
                if($result != null){
                    $data["returncode"]="1";
                    $data["messages"]="";
                    $data["result"] = $result;
                }else {
                    $data["returncode"]="0";
                    $data["messages"]="Not found";
                }
                $data["GET"]= json_encode($_GET);
                $data["Post"]= json_encode($_POST);
                mysqli_close($conn);
                
			
            }
			$this->response(200, $data);
		}
	}
}

$user_api = new api();

?>
