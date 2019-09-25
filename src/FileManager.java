import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class FileManager implements AcessoBaseDatos {
	HashMap<Integer, Usuarios> listadofile;
	public FileManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public HashMap<Integer, Usuarios> leer() {
		// crea el flujo para leer desde el archivo
				BufferedReader in;
				try {
					in = new BufferedReader(new FileReader("fichero.txt"));
					String text = in.readLine();
					while (text != null) {
						System.out.println(text);
						// Para leer diferentes lineas
						text = in.readLine();
					}
					in.close();
				} catch (FileNotFoundException e) {
					System.out.println("Error en lectura de Fichero");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return listadofile;

	}

	@Override
	public void insert() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		Scanner sc = new Scanner(System.in);
		try {
			fichero = new FileWriter("fichero.txt", true);
			pw = new PrintWriter(fichero);
			pw.println(sc.nextLine());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}sc.close();
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void intercambiodatos() {
		// TODO Auto-generated method stub
		
	}

}
