package Model;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import inferface.I_Acceso_A_Datos;

public class FileManager implements I_Acceso_A_Datos {

	private Scanner sc = new Scanner(System.in);
	protected HashMap<Integer, Users> listadofile = new HashMap<Integer, Users>();
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
	public HashMap<Integer, Users> leer() {
		Users usu;
		int contador = 0;

		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			while ((fichero = in.readLine()) != null) {
				String[] partes = fichero.split(";");
				contador++;
				myusername = partes[0];
				mypassword = partes[1];
				mydescription = partes[2];
				usu = new Users(myusername, mypassword, mydescription);
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
	public boolean update(String up_username, String newPassword, String newDescription) {
		boolean state = false;
		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			String fichero;
			while ((fichero = in.readLine()) != null) {

				if (fichero.contains(up_username)) {
					System.out.println("esta en  el fichero");
					// Nom implementado

					System.out.println("Cambiado: " + up_username + "ahora el nuevo password es" + newPassword
							+ "y la nueva description es" + newDescription);

					state = true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			state = false;
		}
		return state;
	}

	@Override
	public boolean deleteone(String del_username) {
		boolean state = false;
		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			fichero = null;
			while ((fichero = in.readLine()) != null) {
				if (fichero.contains(del_username)) {
					System.out.println("Delete one no implement");
					state = true;
				} else {
					System.out.println("No existe el usuario escrito");
					state = false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return state;
	}

	@Override
	public boolean deleteall(String option) {
		boolean state = false;
		try {
			ficherowriter = new FileWriter("fichero.txt", false);

			pw = new PrintWriter(ficherowriter);

			if (option.equalsIgnoreCase("si")) {
				pw.write("");
				System.out.println("Todo el fichero ha sido borrado");
				state = true;
			} else {
				System.out.println("NO HA BORRADO NADA");
				state = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return state;
	}

	@Override
	public void intercambiodatoslist(HashMap<Integer, Users> newList) {
		deleteall("si");
		for (Entry<Integer, Users> entry : newList.entrySet()) {
			insertusu(newList.get(entry.getKey()));
		}
		System.out.println("INTERCAMBIO DE FILE CORRECTO");

	}

	@Override
	public boolean insertusu(Users newUsu) {
		System.out.println("No implementado");
		return false;
	}

}