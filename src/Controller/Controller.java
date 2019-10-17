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
import Model.Usuarios;

public class Controller {

	// private MongoManager mongo;
	protected HashMap<Integer, Usuarios> listbd;
	protected HashMap<Integer, Usuarios> listfile;
	protected HashMap<Integer, Usuarios> listhm;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();
	protected HibernateManager hm = new HibernateManager();
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
		System.out.println("||BBDD:        | 1: Leer datos\n" + "||             | 2: Buscar por username\n"
				+ "||             | 3: Agregar campo\n" + "||             | 4: Actualizar campo\n"
				+ "||             | 5: Eliminar un campo\n" + "||             | 6: Eliminar todo\n"
				+ "||             |\n" + "||Fichero:     | 7: Leer datos\n"
				+ "||             | 8: Buscar por username\n" + "||             | 9: Agregar campos\n"
				+ "||             | 10: Actualizar campos\n" + "||             | 11: Eliminar un campo\n"
				+ "||             | 12: Eliminar todo\n" + "||             |\n" + "||HIBERNATE:   | 13: Leer datos\n"
				+ "||             | 14: Buscar por username\n" + "||             | 15: Agregar campos\n"
				+ "||             | 16: Actualizar campos\n" + "||             | 17: Eliminar un campo\n"
				+ "||             | 18: Eliminar todo\n" + "||             |\n"
				+ "||Intercambio: | 19: BBDD en fichero\n" + "||             | 20: Fichero en BBDD \n"
				+ "||             | 21: HIBERNATE \n" + "|| 0: FIN      |");
		System.out.println("_______________________________________________\n");

		int vmenu = sc.nextInt();
		while (vmenu != 0 && vmenu <= 22 && vmenu >= -1) {
			switch (vmenu) {

			case 1:
				System.out.println("Opción: 1\n");
				listbd = bd.leer();
				mostrar(listbd);
				break;

			case 2:
				System.out.println("Opción: 2\n");
				listbd = bd.leer();
				filtrar(listbd);
				break;

			case 3:
				System.out.println("Opción: 3\n");
				bd.insert();
				break;

			case 4:
				System.out.println("Opción: 4\n");
				bd.update();
				break;

			case 5:
				System.out.println("Opción: 5\n");
				bd.deleteuno();
				break;

			case 6:
				System.out.println("Opción: 6\n");
				bd.deleteall();
				break;

			case 7:
				System.out.println("Opción: 7\n");
				listfile = file.leer();
				mostrar(listfile);
				break;

			case 8:
				System.out.println("Opción: 8\n");
				listfile = file.leer();
				filtrar(listfile);
				break;

			case 9:
				System.out.println("Opción: 9\n");
				file.insert();
				break;

			case 10:
				System.out.println("Opción: 10\n");
				listfile = file.leer();
				mostrar(listfile);
				file.update();
				break;

			case 11:
				System.out.println("Opción: 11\n");
				file.deleteuno();
				break;

			case 12:
				System.out.println("Opción: 12\n");
				file.deleteall();
				break;

			case 13:
				System.out.println("Opción: 13\n");
				listhm = hm.leer();
				mostrar(listhm);
				break;

			case 14:
				System.out.println("Opción: 14\n");
				listhm = hm.leer();
				filtrar(listhm);
				break;

			case 15:
				System.out.println("Opción: 15\n");
				hm.insert();
				break;

			case 16:
				System.out.println("Opción: 16\n");
				listhm = hm.leer();
				mostrar(listhm);
				hm.update();
				break;

			case 17:
				System.out.println("Opción: 17\n");
				hm.deleteuno();
				break;

			case 18:
				System.out.println("Opción: 18\n");
				hm.deleteall();
				break;

			case 19:
				System.out.println("Opción: 19\n");
				file.intercambiodatos();
				break;

			case 20:
				System.out.println("Opción: 20\n");
				bd.intercambiodatos();
				break;
			case 21:
				System.out.println("Opción: 21\n");
				hm.intercambiodatos();
				break;

			}
			System.out.println("_______________________________________________");
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
					+ "||             |\n" + "||Intercambio: | 19: BBDD en fichero\n"
					+ "||             | 20: Fichero en BBDD \n" + "||             | 21: HIBERNATE \n"
					+ "|| 0: FIN      |");
			System.out.println("_______________________________________________\n");
			System.out.println("Introduce otro numero o pon 0 para finalizar");
			vmenu = sc.nextInt();
		}
		System.out.println("PROGRAMA FINALIZADO");
		sc.close();

	}
}
