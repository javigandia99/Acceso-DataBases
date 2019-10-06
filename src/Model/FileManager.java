package Model;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import inferface.AcessoBaseDatos;

public class FileManager implements AcessoBaseDatos {

	private Scanner sc = new Scanner(System.in);
	protected HashMap<Integer, Usuarios> listadofile = new HashMap<Integer, Usuarios>();
	protected BufferedReader in;
	private FileWriter ficherowriter;
	private PrintWriter pw;
	private String fichero;
	private String myusername;
	private String mypassword;
	private String mydescription;

	public FileManager() {
	}

	@Override
	public HashMap<Integer, Usuarios> leer() {
		Usuarios usu;
		int contador = 0;

		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			while ((fichero = in.readLine()) != null) {
				String[] partes = fichero.split(";");
				contador++;
				myusername = partes[0];
				mypassword = partes[1];
				mydescription = partes[2];
				usu = new Usuarios(myusername, mypassword, mydescription);
				listadofile.put(contador, usu);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error en lectura de Fichero");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listadofile;
	}

	@Override
	public void insert() {
		try {
			ficherowriter = new FileWriter("fichero.txt", true);
			pw = new PrintWriter(ficherowriter);
			System.out.println("username:");
			myusername = sc.nextLine();
			System.out.println("password:");
			mypassword = sc.nextLine();
			System.out.println("description:");
			mydescription = sc.nextLine();
			String contenido = myusername + ";" + mypassword + ";" + mydescription + "\n";
			pw.print(contenido);
			System.out.println("Insert correcto!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ficherowriter)
					ficherowriter.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public void update() {
		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			String fichero;
			System.out.println("Introduce username: ");
			myusername = sc.nextLine();
			while ((fichero = in.readLine()) != null) {

				if (fichero.contains(myusername)) {
					System.out.println("esta en  el fichero");

					System.out.println("Que quiere cambiar del username: " + myusername);
					System.out.println("todo - passsword - description - nada");
					String posibilidad = sc.nextLine();

					switch (posibilidad) {
					case "todo":
						System.out.println("nuevo password: ");

						System.out.println("nueva description: ");
						break;

					case "password":
						System.out.println("nuevo password: ");

						break;

					case "description":
						System.out.println("nueva description: ");

						break;

					case "nada":
						// Salida del switch
						break;
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void deleteuno() {

		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			fichero = null;
			System.out.println("Introduce un username para borrar:");
			myusername = sc.nextLine();
			while ((fichero = in.readLine()) != null) {
				if (fichero.contains(myusername)) {
					System.out.println("Borrando...");

				} else {
					System.out.println("No existe el usuario escrito");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void deleteall() {
		try {
			ficherowriter = new FileWriter("fichero.txt", false);

			pw = new PrintWriter(ficherowriter);
			System.out.println("¿Estas seguro de borrar todo el contenido del fichero?");
			System.out.println("No habra vuelta atras...");

			String opcion = sc.nextLine();
			if (opcion == "si") {
				pw.write("");
				System.out.println("Todo el fichero ha sido borrado");
			} else {
				System.out.println("NO HA BORRADO NADA");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void intercambiodatos() {
		// DE BASE DE DATOS A FICHERO
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("fichero.txt", true);

			pw = new PrintWriter(fichero);
			HashMap<Integer, Usuarios> listado = leer();
			Iterator<Entry<Integer, Usuarios>> it = listado.entrySet().iterator();
			while (it.hasNext()) {
				String contenido = it.next().getValue().tofichero();
				pw.print(contenido);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}