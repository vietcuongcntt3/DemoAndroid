<?php
class database{
    // $servername = "localhost";
	// $username = "id9898137_root";
	// $password = "123456a@";
	// $dbname = "id9898137_cnvtictactoe";
	private $servername = "127.0.0.1:3306";
	private	$username = "root";
	private	$password = "root";
	private	$dbname = "db_titactoe";
    public $conn;
 
    // get the database connection
    public function getConnection(){
 
        $this->conn = null;
 
        try{
            $this->conn = new mysqli($this->servername, $this->username, $this->password, $this->dbname);
           // $this->conn->exec("set names utf8");
        }catch(PDOException $exception){
            echo "Connection error: " . $exception->getMessage();
        }
 
        return $this->conn;
    }

    function __construct(){

    }
    
    function __destruct() {
    $this->conn->close();
    }

    function getStatusCodeMeeage($status){
        $codes = Array(
        100 => 'Continue',
        200 => 'OK'
        );
        
        return (isset($codes[$status])) ? $codes[$status] : ”;
        }
}
?>