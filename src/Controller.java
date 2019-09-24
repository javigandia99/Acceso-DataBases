import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Controller {

	// private MongoManager mongo;
	protected HashMap<Integer, Usuarios> listbd;
	protected HashMap<Integer, Usuarios> listfile;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();

	public void mostrar(HashMap<Integer, Usuarios> list) {
		Integer clave;
		Iterator<Integer> productos = list.keySet().iterator();
		System.out.println("Hay los siguientes productos:");
		while (productos.hasNext()) {
			clave = productos.next();
			System.out.println(clave + " - " + list.get(clave));
		}
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1: Leer datos de la BBDD\n");
		System.out.println("2: Agregar campo a la BBDD\n");
		System.out.println("3: Eliminar campo de la BBDD\n");
		System.out.println("");
		
		System.out.println("4: Leer datos de fichero\n");
		System.out.println("5: Agregar campos al fichero\n");
		System.out.println("6: Eliminar campo del Fichero\n");
		System.out.println("");
		
		System.out.println("7: Intercambiar de Fichero a BBDD\n ");
		System.out.println("8: Intercambiar de BBDD a Fichero\n");
		System.out.println("0: FIN\n");

		int vmenu = sc.nextInt();
		while (vmenu != 0 || vmenu <= 9 || vmenu >= -1) {
			switch (vmenu) {

			case 1:
				System.out.println("Opción: 1");
				listbd = bd.leer();
				mostrar(listbd);
				break;

			case 2:
				System.out.println("Opción: 2");
				bd.insert();

			case 3:
				System.out.println("Opción: 3");
				bd.delete();
				break;

			case 4:
				System.out.println("Opción: 4");
				listfile = file.leer();
				mostrar(listfile);
				break;

			case 5:
				System.out.println("Opción: 5");
				file.insert();
				break;

			case 6:
				System.out.println("Opción: 6");
				file.delete();
				break;
			case 7:
				System.out.println("Opción: 7 ");
				bd.intercambiodatos();
				break;
			case 8:
				System.out.println("Opción: 8 ");
				file.intercambiodatos();
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + vmenu);
			}
		}

	}
}
