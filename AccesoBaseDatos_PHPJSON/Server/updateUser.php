<?php
require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD
require 'jsonEsperado.php';
/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */
$arrMessage = array();  // Este array es el codificaremos como JSON tanto si hay resultado como si hay error
/*
 * Lo primero es comprobar que nos han enviado la información via JSON
 */
$parameters = file_get_contents("php://input");
if (isset($parameters)) {
    // Parseamos el string json y lo convertimos a objeto JSON
    $recibido = json_decode($parameters, true);
    // Comprobamos que están todos los datos en el json que hemos recibido
    // Funcion declarada en jsonEsperado.php
    if (JSONCorrectoAnnadir($recibido)) {
        $user = $recibido["userUpd"];

        $username = $user["username"];
        $password = $user["password"];
        $description = $user["description"];
        $query  = "UPDATE `users` ";
        $query .= "SET `db_username`='$username', `db_password`='$password', `db_description`='$description' WHERE `db_username`='$username'";

        $result = $conn->query($query);

        if (isset($result) && $result) { // Si pasa por este if, la query está está bien y se ha insertado correctamente

            $arrMessage["state"] = "ok";
            $arrMessage["message"] = "Alumnos actualizado correctamente";
        } else { // Se ha producido algún error al ejecutar la query

            $arrMessage["state"] = "error";
            $arrMessage["message"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
            $arrMessage["error"] = $conn->error;
            $arrMessage["query"] = $query;
        }
    } else { // Nos ha llegado un json no tiene los campos necesarios

        $arrMessage["state"] = "error";
        $arrMessage["message"] = "EL JSON NO CONTIENE LOS CAMPOS ESPERADOS";
        $arrMessage["recibido"] = $recibido;
        $arrMessage["esperado"] = $arrEsperadoUpd;
    }
} else {    // No nos han enviado el json correctamente

    $arrMessage["state"] = "error";
    $arrMessage["menssage"] = "EL JSON NO SE HA ENVIADO CORRECTAMENTE";
}
$messageJSON = json_encode($arrMessage, JSON_PRETTY_PRINT);
//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $messageJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador
$conn->close();
die();
