import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Controller {

	// private MongoManager mongo;
	protected HashMap<Integer, Usuarios> listbd;
	protected HashMap<Integer, Usuarios> listfile;
	protected BDManager bd = new BDManager();
	protected FileManager file = new FileManager();

	public void mostrar(HashMap<Integer, Usuarios> list) {
		Iterator it = list.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue().toString());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("1: Leer datos de la BBDD\n" + "2: Agregar campo a la BBDD\n"
				+ "3: Eliminar campo de la BBDD\n" + "4: Leer datos de fichero\n" + "5: Agregar campos al fichero\n"
				+ "6: Eliminar campo del Fichero\n" + "7: Intercambiar de Fichero a BBDD\n "
				+ "8: Intercambiar de BBDD a Fichero\n" + "0: FIN\n");

		int vmenu = sc.nextInt();
		do {
			switch (vmenu) {

			case 1:
				System.out.println("Opción: 1");
				listbd = bd.leer();
				mostrar(listbd);
				break;

			case 2:
				System.out.println("Opción: 2");
				bd.insert();
				mostrar(listbd);
				break;

			case 3:
				System.out.println("Opción: 3");
				bd.delete();
				mostrar(listbd);
				break;

			case 4:
				System.out.println("Opción: 4");
				listfile = file.leer();
				mostrar(listfile);
				break;

			case 5:
				System.out.println("Opción: 5");
				file.insert();
				mostrar(listfile);
				break;

			case 6:
				System.out.println("Opción: 6");
				file.delete();
				mostrar(listfile);
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
			vmenu = sc.nextInt();
		} while (vmenu != 0 && vmenu <= 9 && vmenu >= -1);
		sc.close();

	}
}
