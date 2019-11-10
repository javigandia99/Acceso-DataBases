package Model;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import com.mongodb.*;
import inferface.AcessoBaseDatos;

public class MongoDBManager implements AcessoBaseDatos {
	private MongoClient mongo;
	private DB database;
	private DBCollection collection;
	private BasicDBObject object;
	protected HashMap<Integer, Usuarios> listadomongo;
	private Scanner sc;

	@SuppressWarnings("deprecation")
	public MongoDBManager() {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDB("db_users");
		collection = (DBCollection) database.getCollection("user");

	}

	@Override
	public HashMap<Integer, Usuarios> leer() {
		listadomongo = new HashMap<Integer, Usuarios>();
		Usuarios usu;
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

				usu = new Usuarios(username, password, description);
				listadomongo.put(contador, usu);
			}

		} catch (Exception e) {
			System.out.println("error: en lectura de datos");
		}
		return listadomongo;
	}

	@Override
	public void insert() {
		// INSERT
		object = new BasicDBObject();
		sc = new Scanner(System.in);

		System.out.println("Introduce nuevo username:");
		object.put("username", sc.nextLine());

		System.out.println("Introduce nuevo password:");
		object.put("password", sc.nextLine());

		System.out.println("Introduce nuevo description:");
		object.put("description", sc.nextLine());

		collection.insert(object);
		System.out.println("INSERT CORRECTO");
	}

	@Override
	public void update() {
		System.out.println("Introduce que username quiere cambiar:");
		BasicDBObject searchQuery = new BasicDBObject().append("username", sc.nextLine());
		BasicDBObject obupdate = new BasicDBObject();
		System.out.println("Nuevo Password:");
		obupdate.append("$set", new BasicDBObject().append("password", sc.nextLine()));
		System.out.println("Nueva Description:");
		obupdate.append("$set", new BasicDBObject().append("description", sc.nextLine()));
		collection.update(searchQuery, obupdate);
		System.out.println("UPDATE CORRECTO");

	}

	@Override
	public void deleteuno() {
		sc = new Scanner(System.in);
		object = new BasicDBObject();
		String delete_username = sc.nextLine();
		object.put("username", delete_username);
		collection.remove(object);
		System.out.println("DELETE: " + delete_username + " CORRECTO ");

	}

	@Override
	public void deleteall() {
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			collection.remove(cursor.next());
		}
	}

	@Override
	public void intercambiodatos() {
		//TODO: no hace falta implementar este metodo 
	}

	public void intercambiodatoslist(HashMap<Integer, Usuarios> listanueva) {
		deleteall();
		for (Entry<Integer, Usuarios> entry : listanueva.entrySet()) {
			insertarusu(listanueva.get(entry.getKey()));
		}
		System.out.println("CORRECTO INTERCAMBIO DE BBDD-MONGO");
	}

	public boolean insertarusu(Usuarios usu) {
		HashMap<Integer, Usuarios> lista = leer();
		String username = usu.getUsername();

		object = new BasicDBObject();
		object.put("username", username);
		object.put("password", usu.getPassword());
		object.put("description", usu.getDescription());
		collection.insert(object);
		System.out.println("insertusu correto");
		return true;
	}

}
