package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import inferface.I_Acceso_A_Datos;
import model.BDManager;
import model.FileManager;
import model.HibernateManager;
import model.MongoDBManager;
import model.PHPJSONManager;
import model.Users;
import view.View;

public class Controller {

	protected HashMap<Integer, Users> hashMap;
	protected View myView;
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

	public void filtrar(HashMap<Integer, Users> list, String searchUsername) {
		Map<Integer, Users> filtermap = list.entrySet().stream()
				.filter(s -> s.getValue().getUsername().equalsIgnoreCase(searchUsername))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		System.out.println(filtermap.toString());
	}

	public String searchUsername() {
		String searchUsu = null;
		System.out.println("Introduce username para buscar:");
		searchUsu = sc.nextLine();
		return searchUsu;
	}

	public Users newUsu() {
		Users usu = null;
		System.out.println("Introduce un nuevo username:");
		newUsername = sc.nextLine();
		System.out.println("Introduce un nuevo password:");
		newPassword = sc.nextLine();
		System.out.println("Introduce un nuevo description:");
		newDescription = sc.nextLine();

		usu = new Users(newUsername, newPassword, newDescription);
		return usu;
	}

	public String deleteUsername() {
		String delUsername = null;
		System.out.println("Introduce username para borrar:");
		delUsername = sc.nextLine();
		return delUsername;
	}

	public String deleteAll() {
		String choose = null;
		System.out.println("¿Seguro que quiere borrar todo?: ('si') - ('no')");
		choose = sc.nextLine();
		return choose;
	}

	public HashMap<Integer, Users> changeData(I_Acceso_A_Datos typeAccess) {
		HashMap<Integer, Users> changeList;
		String chooseDB = myView.typeAccess();
		switch (chooseDB) {
		case "SQL":
			typeAccess = new BDManager();
			break;

		case "File":
			typeAccess = new FileManager();
			break;
		case "Hibernate":
			typeAccess = new HibernateManager();
			break;
		case "Mongo":
			typeAccess = new MongoDBManager();
			break;
		case "PHP":
			typeAccess = new PHPJSONManager();
			break;
		}
		changeList = typeAccess.leer();
		return changeList;
	}

	public void chooseAction(int option, String typeAccess) {
		I_Acceso_A_Datos access = null;

		// type access to database
		switch (typeAccess) {
		case "SQL":
			access = new BDManager();
			break;

		case "File":
			access = new FileManager();
			break;
		case "Hibernate":
			access = new HibernateManager();
			break;
		case "Mongo":
			access = new MongoDBManager();
			break;
		case "PHP":
			access = new PHPJSONManager();
			break;
		}

		// type action
		switch (option) {
		case 1:
			hashMap = access.leer();
			mostrar(hashMap);
			break;

		case 2:
			hashMap = access.leer();
			mostrar(hashMap);
			filtrar(hashMap, searchUsername());
			break;

		case 3:
			if (access.insertusu(newUsu())) {
				System.out.println("Almacenado correctamente");
			} else {
				System.out.println("Ha ocurrido un error");
			}
			break;

		case 4:
			System.out.println("username a modificar:");
			String up_username = sc.nextLine();
			System.out.println("nuevo password:");
			newPassword = sc.nextLine();
			System.out.println("nueva description:");
			newDescription = sc.nextLine();
			if (access.update(up_username, newPassword, newDescription)) {
				System.out.println("Actualizado correctamente");
			} else {
				System.out.println("Ha ocurrido algun problema");
			}
			break;

		case 5:
			if (access.deleteone(deleteUsername())) {
				System.out.println("Borrado correctamente");
			} else {
				System.out.println("Ha ocurrido algun problema");
			}
			break;

		case 6:
			access.deleteall(deleteAll());
			break;

		case 7:
			access.intercambiodatoslist(changeData(access));
			break;
		}
	}
}
