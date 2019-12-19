<?php

/*  Formato JSON esperado */
$arrEsperadoAdd = array();
$arrEsperadoAdd["request"] = "add";

$arrUserEsperadoAdd = array();
$arrUserEsperadoAdd["username"] = "javi (String)";
$arrUserEsperadoAdd["password"] = "12345jaiv (String)";
$arrUserEsperadoAdd["description"] = "javi gamil suplored (String)";
/*
            $arrUser["username"] = $row["db_username"];
			$arrUser["password"] = $row["db_password"];
			$arrUser["description"] = $row["db_description"];
           
*/
$arrEsperadoDel = array();
$arrEsperadoDel["request"] = "del";

$arrUserEsperadoDel = array();
$arrUserEsperadoDel["username"] = "javi1234 (String)";
/*
			$arrUser["username"] = $row["db_username"];
			
*/

$arrEsperadoUpd = array();
$arrEsperadoUpd["request"] = "upd";

$arrEsperadoAdd["userAdd"] = $arrUserEsperadoAdd;
$arrEsperadoDel["userDel"] = $arrUserEsperadoDel;
$arrEsperadoUpd["userUpd"] = $arrUserEsperadoAdd;



/* Funcion para comprobar si el recibido es igual al esperado */
function JSONCorrectoAnnadir($recibido)
{

	$auxCorrecto = false;

	if (isset($recibido["request"]) && $recibido["request"] = "add" && isset($recibido["userAdd"])) {

		$auxUser = $recibido["userAdd"];
		if (isset($auxUser["username"]) && isset($auxUser["password"]) && isset($auxUser["description"])) {
			$auxCorrecto = true;
		}
	}

	if (isset($recibido["request"]) && $recibido["request"] = "del" && isset($recibido["userDel"])) {
		$auxUser = $recibido["userDel"];
		if (isset($auxUser["username"])) {
			$auxCorrecto = true;
		}
	}

	if (isset($recibido["request"]) && $recibido["request"] = "upd" && isset($recibido["userUpd"])) {
		$auxUser = $recibido["userUpd"];
		if (isset($auxUser["username"]) && isset($auxUser["password"]) && isset($auxUser["description"])) {
			$auxCorrecto = true;
		}
	}

	return $auxCorrecto;
}
