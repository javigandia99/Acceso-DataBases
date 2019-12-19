<?php
require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD
$query = "DELETE FROM users";
$result = $conn->query($query);

if (isset($result) && $result) {
    // Si pasa por este if, obtiene resultado
} else {

    $arrMessage["state"] = "error";
    $arrMessage["mesagge"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
    $arrMessage["error"] = $conn->error;
    $arrMessage["query"] = $query;
}
$messageJSON = json_encode($arrMessage, JSON_PRETTY_PRINT);
//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas 
echo $messageJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador
$conn->close();
