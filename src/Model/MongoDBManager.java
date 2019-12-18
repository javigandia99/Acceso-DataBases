package Model;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import com.mongodb.*;
import inferface.I_Acceso_A_Datos;

public class MongoDBManager implements I_Acceso_A_Datos {
	private MongoClient mongo;
	private DB database;
	private DBCollection collection;
	private BasicDBObject objUser;
	protected HashMap<Integer, Users> listadomongo;

	@SuppressWarnings("deprecation")
	public MongoDBManager() {
		try {
			mongo = new MongoClient("localhost", 27017);
			database = mongo.getDB("db_users");
			collection = (DBCollection) database.getCollection("user");
			System.out.println("CONEXIÓN CON MONGO CORRECTA");
		} catch (Exception e) {
			System.out.println("mongo no encendido");
			e.addSuppressed(e);
		}
	}

	@Override
	public HashMap<Integer, Users> leer() {
		listadomongo = new HashMap<Integer, Users>();
		Users usu;
		DBCursor cursor = collection.find();
		int contador = 0;
		String username = null;
		String password = null;
		String description = null;
		try {
			for (DBObject doc : cursor) {
				contador++;
				username = (String) doc.get("username");
				password = (String) doc.get("password");
				description = (String) doc.get("description");

				usu = new Users(username, password, description);
				listadomongo.put(contador, usu);
			}

		} catch (Exception e) {
			System.out.println("error: en lectura de datos");
		}
		return listadomongo;
	}
	
	@Override
	public boolean insertusu(Users newUsu) {

		objUser = new BasicDBObject();
		objUser.put("username", newUsu.getUsername());
		objUser.put("password", newUsu.getPassword());
		objUser.put("description", newUsu.getDescription());
		collection.insert(objUser);
		return true;
	}

	@Override
	public boolean update(String up_username,String newPassword,String newDescription) {
		boolean state = false;
		
		BasicDBObject searchQuery = new BasicDBObject().append("username", up_username);
		BasicDBObject obupdate = new BasicDBObject();
		System.out.println("Nuevo Password:");
		obupdate.append("$set", new BasicDBObject().append("password", newPassword));
		System.out.println("Nueva Description:");
		obupdate.append("$set", new BasicDBObject().append("description",newDescription));
		collection.update(searchQuery, obupdate);
		System.out.println("UPDATE CORRECTO");
return state;
	}

	@Override
	public boolean deleteone(String del_username) {
		boolean state = false;
		objUser = new BasicDBObject();
		if (del_username != null) {
			objUser.put("username", del_username);
			collection.remove(objUser);
			System.out.println("DELETE: " + del_username + " CORRECTO ");
			state = true;
		}
		return state;
	}

	@Override
	public boolean deleteall(String option) {
		boolean state = false;
		if (option.equalsIgnoreCase("si")) {

			DBCursor cursor = collection.find();
			if (cursor != null) {
				while (cursor.hasNext()) {
					collection.remove(cursor.next());
				}
				state = true;
			}
		}
		return state;
	}

	@Override
	public void intercambiodatoslist(HashMap<Integer, Users> newList) {
		deleteall("si");
		for (Entry<Integer, Users> entry : newList.entrySet()) {
			insertusu(newList.get(entry.getKey()));
		}
		System.out.println("INTERCAMBIO DE MONGO CORRECTO");
	}

}
