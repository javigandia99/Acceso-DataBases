<?php

require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

$arrMessage = array();  // Este array es el codificaremos como JSON tanto si hay resultado como si hay error



$query = "SELECT * FROM users";

$result = $conn->query ( $query );

if (isset ( $result ) && $result) { // Si pasa por este if, la query está está bien y se obtiene resultado
	
	if ($result->num_rows > 0) { // Aunque la query esté bien puede no obtenerse resultado (tabla vacía). Comprobamos antes de recorrer
		
		$arrUsers = array();
		
		while ( $row = $result->fetch_assoc () ) {
			
			// Por cada vuelta del bucle creamos un user. Como es un objeto hacemos un array asociativo
			$arrUser = array();
			// Por cada columna de la tabla creamos una propiedad para el objeto
			$arrUser["username"] = $row["db_username"];
			$arrUser["password"] = $row["db_password"];
			$arrUser["description"] = $row["db_description"];
			// Por último, añadimos el nuevo jugador al array de users
			$arrUsers[] = $arrUser;
			
		}
		
		// Añadimos al $arrMessage el array de users y añadimos un campo para indicar que todo ha ido OK
		$arrMessage["state"] = "ok";
		$arrMessage["users"] = $arrUsers;
		
		
	} else {
		
		$arrMessage["state"] = "ok";
		$arrMessage["users"] = []; // Array vacío si no hay resultados
	}
	
} else {
	
	$arrMessage["state"] = "error";
	$arrMessage["message"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
	$arrMessage["error"] = $conn->error;
	$arrMessage["query"] = $query;
	
}

$messageJSON = json_encode($arrMessage,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas 
echo $messageJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();
