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
import Model.Usuarios;

public class Controller {

	protected HashMap<Integer, Usuarios> listbd;
	protected HashMap<Integer, Usuarios> listfile;
	protected HashMap<Integer, Usuarios> listhm;
	protected HashMap<Integer, Usuarios> listmondb;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();
	protected HibernateManager hm = new HibernateManager();
	protected MongoDBManager mongo = new MongoDBManager();
	protected Scanner sc = new Scanner(System.in);

	public void mostrar(HashMap<Integer, Usuarios> list) {
		Iterator<Entry<Integer, Usuarios>> it = list.entrySet().iterator();
		System.out.println("\nDatos:");
		System.out.println("____________________________________________\n");
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public void filtrar(HashMap<Integer, Usuarios> list) {
		System.out.println("Buscador de username: ");
		String buscousername = sc.nextLine();
		Map<Integer, Usuarios> filtermap = list.entrySet().stream()
				.filter(s -> s.getValue().getUsername().equalsIgnoreCase(buscousername))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println(filtermap.toString());
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("_______________________________________________");
		System.out.println("");
		System.out.println("____________________MENU:______________________\n");
		System.out.println("||BBDD:        | 1: Leer datos\n" 
				+ "||             | 2: Buscar por username\n"
				+ "||             | 3: Agregar campo\n"
				+ "||             | 4: Actualizar campo\n"
				+ "||             | 5: Eliminar un campo\n"
				+ "||             | 6: Eliminar todo\n"
				+ "||             |\n"
				+ "||Fichero:     | 7: Leer datos\n"
				+ "||             | 8: Buscar por username\n"
				+ "||             | 9: Agregar campos\n"
				+ "||             | 10: Actualizar campos\n"
				+ "||             | 11: Eliminar un campo\n"
				+ "||             | 12: Eliminar todo\n"
				+ "||             |\n"
				+ "||HIBERNATE:   | 13: Leer datos\n"
				+ "||             | 14: Buscar por username\n"
				+ "||             | 15: Agregar campos\n"
				+ "||             | 16: Actualizar campos\n"
				+ "||             | 17: Eliminar un campo\n"
				+ "||             | 18: Eliminar todo\n"
				+ "||             |\n"
				+ "||MONGODB:     | 19: Leer datos\n"
				+ "||             | 20: Buscar por username\n"
				+ "||             | 21: Agregar campos\n"
				+ "||             | 22: Actualizar campos\n"
				+ "||             | 23: Eliminar un campo\n"
				+ "||             | 24: Eliminar todo\n"
				+ "||             |\n"
				+ "||Intercambio: | 25: BBDD\n"
				+ "||             | 26: Fichero\n"
				+ "||             | 27: HIBERNATE\n"
				+ "||             | 28: MONGODB\n"
				+ "" + "|| 0: FIN      |");
		System.out.println("_______________________________________________\n");

		int vmenu = sc.nextInt();
		while (vmenu != 0 && vmenu <= 28 && vmenu >= -1) {
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
				bd.update();
				break;

			case 5:
				// BBDD
				System.out.println("Opción: 5\n");
				System.out.println("DELETE ONE");
				bd.deleteuno();
				break;

			case 6:
				// BBDD
				System.out.println("Opción: 6\n");
				System.out.println("DELETE ALL");
				bd.deleteall();
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
				file.update();
				break;

			case 11:
				// FILE
				System.out.println("Opción: 11\n");
				System.out.println("DELETE ONE");
				file.deleteuno();
				break;

			case 12:
				// FILE
				System.out.println("Opción: 12\n");
				System.out.println("DELETE ALL");
				file.deleteall();
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
				hm.insert();
				break;

			case 16:
				// HIBERNATE
				System.out.println("Opción: 16\n");
				System.out.println("UPDATE");
				listhm = hm.leer();
				mostrar(listhm);
				hm.update();
				break;

			case 17:
				// HIBERNATE
				System.out.println("Opción: 17\n");
				System.out.println("DELETE ONE");
				hm.deleteuno();
				break;

			case 18:
				// HIBERNATE
				System.out.println("Opción: 18\n");
				System.out.println("DELETE ALL");
				hm.deleteall();
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
				mongo.insert();
				break;

			case 22:
				// MONGO
				System.out.println("Opción: 22\n");
				System.out.println("UPDATE");
				listmondb = mongo.leer();
				mostrar(listmondb);
				mongo.update();
				break;

			case 23:
				// MONGO
				System.out.println("Opción: 23\n");
				System.out.println("DELETE ONE");
				mongo.deleteuno();
				break;

			case 24:
				// MONGO
				System.out.println("Opción: 24\n");
				System.out.println("DELETE ALL");
				mongo.deleteall();
				break;

			case 25:
				// BBDD
				System.out.println("Opción: 25\n");
				System.out.println("INTRODUCE (1 FILE) (2 HIBERNATE) (3 MONGO) PARA IMPORTARLO A BBDD");
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
				}
				break;

			case 26:
				// FILE
				System.out.println("Opción: 26\n");
				
				file.intercambiodatos();
				break;
			case 27:
				// HIBERNATE
				System.out.println("Opción: 27\n");
				hm.intercambiodatos();
				break;
			case 28:
				// MONGO
				System.out.println("Opción: 28\n");
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
				}
				break;
			}
			System.out.println("");
			System.out.println("____________________MENU:______________________\n");
			System.out.println("||BBDD:        | 1: Leer datos\n"
					+ "||             | 2: Buscar por username\n"
					+ "||             | 3: Agregar campo\n"
					+ "||             | 4: Actualizar campo\n"
					+ "||             | 5: Eliminar un campo\n"
					+ "||             | 6: Eliminar todo\n"
					+ "||             |\n"
					+ "||Fichero:     | 7: Leer datos\n"
					+ "||             | 8: Buscar por username\n"
					+ "||             | 9: Agregar campos\n"
					+ "||             | 10: Actualizar campos\n"
					+ "||             | 11: Eliminar un campo\n"
					+ "||             | 12: Eliminar todo\n"
					+ "||             |\n"
					+ "||HIBERNATE:   | 13: Leer datos\n"
					+ "||             | 14: Buscar por username\n"
					+ "||             | 15: Agregar campos\n"
					+ "||             | 16: Actualizar campos\n"
					+ "||             | 17: Eliminar un campo\n"
					+ "||             | 18: Eliminar todo\n"
					+ "||             |\n"
					+ "||MONGODB:     | 19: Leer datos\n"
					+ "||             | 20: Buscar por username\n"
					+ "||             | 21: Agregar campos\n"
					+ "||             | 22: Actualizar campos\n"
					+ "||             | 23: Eliminar un campo\n"
					+ "||             | 24: Eliminar todo\n"
					+ "||             |\n"
					+ "||Intercambio: | 25: BBDD\n"
					+ "||             | 26: Fichero\n"
					+ "||             | 27: HIBERNATE\n"
					+ "||             | 28: MONGODB\n"
					+ "|| 0: FIN      |");
			System.out.println("_______________________________________________\n");
			System.out.println("Introduce otro numero o pon 0 para finalizar");
			vmenu = sc.nextInt();
		}
		System.out.println("PROGRAMA FINALIZADO");
		sc.close();

	}
}
