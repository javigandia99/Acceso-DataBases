package Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import Model.BDManager;
import Model.FileManager;
import Model.HibernateManager;
import Model.MongoDBManager;
import Model.PHPJSONManager;
import Model.Users;

public class Controller {

	protected HashMap<Integer, Users> listbd;
	protected HashMap<Integer, Users> listfile;
	protected HashMap<Integer, Users> listhm;
	protected HashMap<Integer, Users> listmondb;
	protected HashMap<Integer, Users> listphpjson;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();
	protected HibernateManager hm = new HibernateManager();
	protected MongoDBManager mongo = new MongoDBManager();
	protected PHPJSONManager phpjson = new PHPJSONManager();
	// ------vista-----
	protected Scanner sc = new Scanner(System.in);
	String newUsername;
	String newPassword;
	String newDescription;
	String option;

	public void mostrar(HashMap<Integer, Users> list) {
		Iterator<Entry<Integer, Users>> it = list.entrySet().iterator();
		System.out.println("\nDatos:");
		System.out.println("____________________________________________\n");
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public void filtrar(HashMap<Integer, Users> list) {
		System.out.println("Buscador de username: ");
		String buscousername = sc.nextLine();
		Map<Integer, Users> filtermap = list.entrySet().stream()
				.filter(s -> s.getValue().getUsername().equalsIgnoreCase(buscousername))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println(filtermap.toString());
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("_______________________________________________");
		System.out.println("");
		System.out.println("____________________MENU:______________________\n");
		System.out.println("||BBDD:        | 1: Leer datos\n" + "||             | 2: Buscar por username\n"
				+ "||             | 3: Agregar campo\n" + "||             | 4: Actualizar campo\n"
				+ "||             | 5: Eliminar un campo\n" + "||             | 6: Eliminar todo\n"
				+ "||             |\n" + "||Fichero:     | 7: Leer datos\n"
				+ "||             | 8: Buscar por username\n" + "||             | 9: Agregar campos\n"
				+ "||             | 10: Actualizar campos\n" + "||             | 11: Eliminar un campo\n"
				+ "||             | 12: Eliminar todo\n" + "||             |\n" + "||HIBERNATE:   | 13: Leer datos\n"
				+ "||             | 14: Buscar por username\n" + "||             | 15: Agregar campos\n"
				+ "||             | 16: Actualizar campos\n" + "||             | 17: Eliminar un campo\n"
				+ "||             | 18: Eliminar todo\n" + "||             |\n" + "||MONGODB:     | 19: Leer datos\n"
				+ "||             | 20: Buscar por username\n" + "||             | 21: Agregar campos\n"
				+ "||             | 22: Actualizar campos\n" + "||             | 23: Eliminar un campo\n"
				+ "||             | 24: Eliminar todo\n" + "||             |\n" + "||PHP-JSON:    | 25: Leer datos\n"
				+ "||             | 26: Buscar por username\n" + "||             | 27: Agregar campos\n"
				+ "||             | 28: Actualizar campos\n" + "||             | 29: Eliminar un campo\n"
				+ "||             | 30: Eliminar todo\n" + "||             |\n" + "||Intercambio: | 31: BBDD\n"
				+ "||             | 32: Fichero\n" + "||             | 33: HIBERNATE\n"
				+ "||             | 34: MONGODB\n" + "||             | 35: PHP-JSON\n" + "" + "|| 0: FIN      |");
		System.out.println("_______________________________________________\n");

		int vmenu = sc.nextInt();
		while (vmenu != 0 && vmenu <= 35 && vmenu > -1) {
			switch (vmenu) {

			case 1:
				// BBDD
				System.out.println("Opción: 1\n");
				System.out.println("LECTURA");
				listbd = bd.leer();
				mostrar(listbd);
				break;

			case 2:
				// BBDD
				System.out.println("Opción: 2\n");
				System.out.println("FILTRAR");
				listbd = bd.leer();
				filtrar(listbd);
				break;

			case 3:
				// BBDD
				System.out.println("Opción: 3\n");
				System.out.println("REGISTRO");
				bd.insert();
				break;

			case 4:
				// BBDD
				System.out.println("Opción: 4\n");
				System.out.println("UPDATE");
				bd.update(null, null, null);
				break;

			case 5:
				// BBDD
				System.out.println("Opción: 5\n");
				System.out.println("DELETE ONE");
				bd.deleteone(null);
				break;

			case 6:
				// BBDD
				System.out.println("Opción: 6\n");
				System.out.println("DELETE ALL");
				bd.deleteall(null);
				break;

			case 7:
				// FILE
				System.out.println("Opción: 7\n");
				System.out.println("LECTURA");
				listfile = file.leer();
				mostrar(listfile);
				break;

			case 8:
				// FILE
				System.out.println("Opción: 8\n");
				System.out.println("FILTRAR");
				listfile = file.leer();
				filtrar(listfile);
				break;

			case 9:
				// FILE
				System.out.println("Opción: 9\n");
				System.out.println("REGISTRO");
				file.insert();
				break;

			case 10:
				// FILE
				System.out.println("Opción: 10\n");
				System.out.println("UPDATE");
				listfile = file.leer();
				mostrar(listfile);
				file.update(null, null, null);
				break;

			case 11:
				// FILE
				System.out.println("Opción: 11\n");
				System.out.println("DELETE ONE");
				file.deleteone(null);
				break;

			case 12:
				// FILE
				System.out.println("Opción: 12\n");
				System.out.println("DELETE ALL");

				System.out.println(
						"¿Estas seguro de borrar todo el contenido del fichero? (Introduce 'si' para borrar todo)");
				System.out.println("No habra vuelta atras...");

				option = sc.nextLine();
				file.deleteall(option);
				break;

			case 13:
				// HIBERNATE
				System.out.println("Opción: 13\n");
				System.out.println("LECTURA");
				listhm = hm.leer();
				mostrar(listhm);
				break;

			case 14:
				// HIBERNATE
				System.out.println("Opción: 14\n");
				System.out.println("FILTRAR");
				listhm = hm.leer();
				filtrar(listhm);
				break;

			case 15:
				// HIBERNATE
				System.out.println("Opción: 15\n");
				System.out.println("REGISTRO");
				Users usu = new Users();
				hm.insertusu(usu);
				break;

			case 16:
				// HIBERNATE
				System.out.println("Opción: 16\n");
				System.out.println("UPDATE");
				listhm = hm.leer();
				mostrar(listhm);
				hm.update(null, null, null);
				break;

			case 17:
				// HIBERNATE
				System.out.println("Opción: 17\n");
				System.out.println("DELETE ONE");
				hm.deleteone(null);
				break;

			case 18:
				// HIBERNATE
				System.out.println("Opción: 18\n");
				System.out.println("DELETE ALL");
				hm.deleteall("si");
				break;
			case 19:
				// MONGO
				System.out.println("Opción: 19\n");
				System.out.println("LECTURA");
				listmondb = mongo.leer();
				mostrar(listmondb);
				break;

			case 20:
				// MONGO
				System.out.println("Opción: 20\n");
				System.out.println("FILTRAR");
				listmondb = mongo.leer();
				filtrar(listmondb);
				break;

			case 21:
				// MONGO
				System.out.println("Opción: 21\n");
				System.out.println("REGISTRO");
				Users usus = new Users();
				mongo.insertusu(usus);
				break;

			case 22:
				// MONGO
				System.out.println("Opción: 22\n");
				System.out.println("UPDATE");
				listmondb = mongo.leer();
				mostrar(listmondb);
				mongo.update(null, null, null);
				break;

			case 23:
				// MONGO
				System.out.println("Opción: 23\n");
				System.out.println("DELETE ONE");
				mongo.deleteone(null);
				break;

			case 24:
				// MONGO
				System.out.println("Opción: 24\n");
				System.out.println("DELETE ALL");
				mongo.deleteall("si");
				break;

			case 25:
				// PHP-JSON
				System.out.println("Opción: 25\n");
				System.out.println("LECTURA");
				listphpjson = phpjson.leer();
				mostrar(listphpjson);
				break;

			case 26:
				// PHP-JSON
				System.out.println("Opción: 26\n");
				System.out.println("FILTRAR");
				listphpjson = phpjson.leer();
				filtrar(listphpjson);
				break;

			case 27:
				// PHP-JSON
				System.out.println("Opción: 27\n");
				System.out.println("REGISTRO");
				Users ususs = new Users();
				System.out.println("Nuevo username: ");
				sc.nextLine();
				newUsername = sc.nextLine();
				System.out.println("Password para " + newUsername + ": ");
				newPassword = sc.nextLine();
				System.out.println("Description: ");
				newDescription = sc.nextLine();
				ususs.setUsername(newUsername);
				ususs.setPassword(newPassword);
				ususs.setDescription(newDescription);
				phpjson.insertusu(ususs);
				break;

			case 28:
				// PHP-JSON
				System.out.println("Opción: 28\n");
				System.out.println("UPDATE");
				listphpjson = phpjson.leer();
				mostrar(listphpjson);
				sc.nextLine();
				System.out.println("Que usuario quieres actualizar: (username): ");
				String up_username = sc.nextLine();
				System.out.println("nuevo password para " + up_username + ": ");
				newPassword = sc.nextLine();
				System.out.println("nueva description para "+up_username+": ");
				newDescription = sc.nextLine();
				phpjson.update(up_username, newPassword, newDescription);
				break;

			case 29:
				// PHP-JSON
				System.out.println("Opción: 29\n");
				System.out.println("DELETE ONE");
				listphpjson = phpjson.leer();
				mostrar(listphpjson);
				sc.nextLine();
				System.out.println("Que usuario quieres borrar: (username): ");
				String del_username = sc.nextLine();
				phpjson.deleteone(del_username);
				break;

			case 30:
				// PHP-JSON
				System.out.println("Opción: 30\n");
				System.out.println("DELETE ALL");
				System.out.println("¿QUIERES BORRAR TODOS LOS USUARIOS: (si) ");
				option = sc.nextLine();
				phpjson.deleteall(option);
				break;

			case 31:
				// BBDD
				System.out.println("Opción: 31\n");
				System.out.println("INTRODUCE (1 FILE) (2 HIBERNATE) (3 MONGO) (4 PHP-JSON) PARA IMPORTARLO A BBDD");
				int importarabd = sc.nextInt();
				switch (importarabd) {
				case 1:
					bd.intercambiodatoslist(file.leer());
					break;
				case 2:
					bd.intercambiodatoslist(hm.leer());
					break;
				case 3:
					bd.intercambiodatoslist(mongo.leer());
					break;
				case 4:
					bd.intercambiodatoslist(phpjson.leer());
					break;
				}
				break;

			case 32:
				// FILE
				System.out.println("Opción: 32\n");

				System.out.println("INTRODUCE (1 BD) (2 FILE) (3 HIBERNATE) PARA IMPORTARLO A FILE");
				int importarafile = sc.nextInt();
				switch (importarafile) {
				case 1:
					mongo.intercambiodatoslist(bd.leer());
					break;
				case 2:
					mongo.intercambiodatoslist(mongo.leer());
					break;
				case 3:
					mongo.intercambiodatoslist(hm.leer());
					break;
				case 4:
					mongo.intercambiodatoslist(phpjson.leer());
					break;
				}
				break;

			case 33:
				// HIBERNATE
				System.out.println("Opción: 33\n");
				hm.intercambiodatos();
				break;

			case 34:
				// MONGO
				System.out.println("Opción: 34\n");
				System.out.println("INTRODUCE (1 BD) (2 FILE) (3 HIBERNATE) PARA IMPORTARLO A MONGO");
				int importaramongo = sc.nextInt();
				switch (importaramongo) {
				case 1:
					mongo.intercambiodatoslist(bd.leer());
					break;
				case 2:
					mongo.intercambiodatoslist(file.leer());
					break;
				case 3:
					mongo.intercambiodatoslist(hm.leer());
					break;
				case 4:
					mongo.intercambiodatoslist(phpjson.leer());
					break;
				}
				break;

			case 35:
				// PHP-JSON
				System.out.println("Opción: 35\n");
				System.out.println("INTRODUCE (1 BD) (2 FILE) (3 HIBERNATE) PARA IMPORTARLO A MONGO");
				int importaraphpjson = sc.nextInt();
				switch (importaraphpjson) {
				case 1:
					phpjson.intercambiodatoslist(bd.leer());
					break;
				case 2:
					phpjson.intercambiodatoslist(file.leer());
					break;
				case 3:
					phpjson.intercambiodatoslist(hm.leer());
					break;
				case 4:
					phpjson.intercambiodatoslist(mongo.leer());
					break;
				}
				break;

			}

			System.out.println("");
			System.out.println("____________________MENU:______________________\n");
			System.out.println("||BBDD:        | 1: Leer datos\n" + "||             | 2: Buscar por username\n"
					+ "||             | 3: Agregar campo\n" + "||             | 4: Actualizar campo\n"
					+ "||             | 5: Eliminar un campo\n" + "||             | 6: Eliminar todo\n"
					+ "||             |\n" + "||Fichero:     | 7: Leer datos\n"
					+ "||             | 8: Buscar por username\n" + "||             | 9: Agregar campos\n"
					+ "||             | 10: Actualizar campos\n" + "||             | 11: Eliminar un campo\n"
					+ "||             | 12: Eliminar todo\n" + "||             |\n"
					+ "||HIBERNATE:   | 13: Leer datos\n" + "||             | 14: Buscar por username\n"
					+ "||             | 15: Agregar campos\n" + "||             | 16: Actualizar campos\n"
					+ "||             | 17: Eliminar un campo\n" + "||             | 18: Eliminar todo\n"
					+ "||             |\n" + "||MONGODB:     | 19: Leer datos\n"
					+ "||             | 20: Buscar por username\n" + "||             | 21: Agregar campos\n"
					+ "||             | 22: Actualizar campos\n" + "||             | 23: Eliminar un campo\n"
					+ "||             | 24: Eliminar todo\n" + "||             |\n"
					+ "||PHP-JSON:    | 25: Leer datos\n" + "||             | 26: Buscar por username\n"
					+ "||             | 27: Agregar campos\n" + "||             | 28: Actualizar campos\n"
					+ "||             | 29: Eliminar un campo\n" + "||             | 30: Eliminar todo\n"
					+ "||             |\n" + "||Intercambio: | 31: BBDD\n" + "||             | 32: Fichero\n"
					+ "||             | 33: HIBERNATE\n" + "||             | 34: MONGODB\n"
					+ "||             | 35: PHP-JSON\n" + "" + "|| 0: FIN      |");
			System.out.println("_______________________________________________\n");
			System.out.println("Introduce otro numero o pon 0 para finalizar");
			vmenu = sc.nextInt();
		}

		System.out.println("PROGRAMA FINALIZADO");
		sc.close();

	}
}
