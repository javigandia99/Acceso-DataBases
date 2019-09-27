package Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import Model.BDManager;
import Model.FileManager;
import Model.Usuarios;

public class Controller {

	// private MongoManager mongo;
	protected HashMap<Integer, Usuarios> listbd;
	protected HashMap<Integer, Usuarios> listfile;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();

	public void mostrar(HashMap<Integer, Usuarios> list) {
		Iterator<Entry<Integer, Usuarios>> it = list.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("| 1: Leer datos de la BBDD\n" + "| 2: Agregar campo a la BBDD \n"
				+ "| 3: Eliminar campo de la BBDD\n" + "| 4: Leer datos de fichero\n"
				+ "| 5: Agregar campos al fichero \n" + "| 6: Eliminar campo del Fichero\n"
				+ "| 7: Poner BBDD en fichero\n" + "| 8: Poner Fichero en BBDD \n" + "| 0: FIN\n");

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
				bd.insert();
				mostrar(listbd);
				break;

			case 3:
				System.out.println("Opción: 3\n");
				bd.delete();
				mostrar(listbd);
				break;

			case 4:
				System.out.println("Opción: 4\n");
				listfile = file.leer();
				mostrar(listfile);
				break;

			case 5:
				System.out.println("Opción: 5\n");
				file.insert();
				mostrar(listfile);
				break;

			case 6:
				System.out.println("Opción: 6\n");
				file.delete();
				// mostrar(listfile);
				break;
			case 7:
				System.out.println("Opción: 7\n");
				bd.intercambiodatos();
				break;
			case 8:
				System.out.println("Opción: 8\n");
				file.intercambiodatos();
				break;
			}
			System.out.println("---------------------------------------------");
			System.out.println("||BBDD:        | 1: Leer datos\n" + "||             | 2: Agregar campo\n"
					+ "||             | 3: Eliminar campo\n" + "||Fichero:     | 4: Leer datos\n"
					+ "||             | 5: Agregar campos\n" + "||             | 6: Eliminar campo del Fichero\n"
					+ "||Intercambio: | 7: BBDD en fichero\n" + "||             | 8: Fichero en BBDD \n"
					+ "|| 0: FIN |");
			System.out.println("---------------------------------------------");
			System.out.println("Introduce otro numero o pon 0 para finalizar");
			vmenu = sc.nextInt();
		}
		System.out.println("PROGRAMA FINALIZADO");
		sc.close();

	}
}
