package Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import Model.BDManager;
import Model.FileManager;
import Model.Usuarios;

public class Controller {

	// private MongoManager mongo;
	protected HashMap<Integer, Usuarios> listbd;
	protected HashMap<Integer, Usuarios> listfile;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();
	protected Scanner sc = new Scanner(System.in);

	public void mostrar(HashMap<Integer, Usuarios> list) {
		Iterator<Entry<Integer, Usuarios>> it = list.entrySet().iterator();
		System.out.println("\nDatos:");
		System.out.println("____________________________________________\n");
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public void filtrar(HashMap<Integer, Usuarios> list) {
		String buscousername = sc.nextLine();
		Map<Integer, Usuarios> filtermap = list.entrySet()
		                   .stream()
		                   .filter(s -> s.getValue().getUsername().equalsIgnoreCase(buscousername))
		                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println(filtermap.toString());
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("_______________________________________________");
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
				+ "||Intercambio: | 13: BBDD en fichero\n" 
				+ "||             | 14: Fichero en BBDD \n"
				+ "|| 0: FIN      |");
		System.out.println("_______________________________________________\n");

		int vmenu = sc.nextInt();
		while (vmenu != 0 && vmenu <= 9 && vmenu >= -1) {
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
				bd.intercambiodatos();
				break;

			case 14:
				System.out.println("Opción: 14\n");
				file.intercambiodatos();
				break;
			}
			System.out.println("");
			System.out.println("_______________________________________________");
			System.out.println("_______________________________________________");
			System.out.println("____________________MENU:______________________\n");
			System.out.println("||BBDD:        | 1: Leer datos\n" 
					+ "||             | 2: Agregar campo\n"
					+ "||             | 3: Actualizar campo\n"
					+ "||             | 4: Eliminar un campo\n"
					+ "||             | 5: Eliminar todo\n"
					+ "||             |\n" + "||Fichero:     | 6: Leer datos\n"
					+ "||             | 7: Agregar campos\n" 
					+ "||             | 8: Actualizar campos\n"
					+ "||             | 9: Eliminar un campo\n"
					+ "||             | 10: Eliminar todo\n"
					+ "||             |\n" 
					+ "||Intercambio: | 11: BBDD en fichero\n"
					+ "||             | 12: Fichero en BBDD \n" + "|| 0: FIN      |");
			System.out.println("_______________________________________________\n");
			System.out.println("Introduce otro numero o pon 0 para finalizar");
			vmenu = sc.nextInt();
		}
		System.out.println("PROGRAMA FINALIZADO");
		sc.close();

	}
}
