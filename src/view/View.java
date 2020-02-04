package view;

import java.util.Scanner;

import controller.Controller;
import model.Users;

public class View {
	Controller myController;
	Scanner sc;
	Users newUsu;

	public View() {
		myController = new Controller();
		sc = new Scanner(System.in);
	}

	public String typeAccess() {
		String type = null;
		System.out.println("Tipo de base de datos quiere utlizar: "
				+ " (1 => SQL) (2 => File) (3 => Hibernate) (4 => Mongo) (5 => PHP-JSON) (6 => NodeJS Api)");
		int option = sc.nextInt();
		switch (option) {
		case 1:
			type = "SQL";
			break;

		case 2:
			type = "File";
			break;

		case 3:
			type = "Hibernate";
			break;

		case 4:
			type = "Mongo";
			break;

		case 5:
			type = "PHP";
			break;
		case 6:
			type = "NodeJS";
			break;
		}

		return type;

	}

	public void action(String type) {
		System.out.println("Introduce que quiere hacer: "
				+ "(1 => Read) - (2 => Filter) - (3 => Insert) - (4 => Update)  - (5 => DeleteOne) - (6 => DeleteAll) - (0 => EXIT)");
		int option = sc.nextInt();
		while (option != 0) {

			myController.chooseAction(option, type);
			System.out.println("");
			System.out.println("Introduce que quiere hacer: "
					+ "(1 => Read) - (2 => Filter) - (3 => Insert) - (4 => Update)  - (5 => DeleteOne) - (6 => DeleteAll) - (0 => EXIT)");
			option = sc.nextInt();
		}
	}
}
