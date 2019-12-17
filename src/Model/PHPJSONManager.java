package Model;

import java.util.HashMap;
import java.util.Map.Entry;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import Auxiliares.ApiRequests;
import inferface.I_Acceso_A_Datos;

public class PHPJSONManager implements I_Acceso_A_Datos {

	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET, SET_insert_USUARIO, SET_delete_USUARIO, SET_deleteAll_USUARIOS;
	HashMap<Integer, Users> listphp;

	public PHPJSONManager() {
		listphp = new HashMap<Integer, Users>();
		encargadoPeticiones = new ApiRequests();
		SERVER_PATH = "http://localhost/adat_alumnos/";
	}

	@Override
	public HashMap<Integer, Users> leer() {

		// recogerAlumnos = new HashMap<String, Alumno>();
		GET = "leerUsuarios.php";

		try {

			System.out.println("---------- Leemos datos de JSON --------------------");
			System.out.println("Lanzamos peticion JSON para alumnos");

			String url = SERVER_PATH + GET; // Sacadas de configuracion

			System.out.println("La url a la que lanzamos la peticion es " + url);

			String response = encargadoPeticiones.getRequest(url);

			System.out.println(response); // Traza para pruebas
			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray users = (JSONArray) respuesta.get("Alumnos");
			System.out.println("--------" + users);
			int contador = 0;
			for (Object object : users) {
				JSONObject json = (JSONObject) object;

				contador++;
				String db_username = json.get("username").toString();
				String db_password = json.get("password").toString();
				String db_description = json.get("description").toString();

				Users usu = new Users(db_username, db_password, db_description);

				listphp.put(contador, usu);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listphp;
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean insertusu(Users usu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String up_username, String newPassword, String newDescription) {
		boolean state = false;
		return state;
	}

	@Override
	public boolean deleteone(String del_username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteall(String option) {
		boolean state = false;
		if (option.equalsIgnoreCase("si")) {

			SET_deleteAll_USUARIOS = "deleteAllUsers.php";
			URL url = null;
			try {
				url = new URL(SERVER_PATH + SET_deleteAll_USUARIOS);
				URLConnection con = url.openConnection();
				con.getInputStream(); // retornamos la entrada de la conexion abierta con el PHP
			} catch (IOException e) {
				e.printStackTrace();
				state = false;
			}
			state = true;
		}
		return state;
	}

	@Override
	public void intercambiodatoslist(HashMap<Integer, Users> newList) {
		deleteall("si");
		for (Entry<Integer, Users> entry : newList.entrySet()) {
			insertusu(newList.get(entry.getKey()));
		}
		System.out.println("INTERCAMBIO DE PHP-JSON CORRECTO");
	}

}