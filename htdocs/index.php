<?php
// Powered by Zyro.com
//if ($this->method == 'GET'){
			// $servername = "localhost";
			// $username = "id9898137_root";
			// $password = "123456a@";
			// $dbname = "id9898137_cnvtictactoe";
			$servername = "127.0.0.1:3306";
			$username = "root";
			$password = "root";
			$dbname = "db_titactoe";
			
			
			$conn = mysqli_connect($servername, $username, $password, $dbname);
           
//          // Create connection
//$conn = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

echo "Connected successfully";
?>
