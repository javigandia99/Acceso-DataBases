package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import inferface.I_Acceso_A_Datos;
import java.util.logging.*;

@SuppressWarnings("deprecation")
public class HibernateManager implements I_Acceso_A_Datos {
	Users usu;
	Session session;
	protected HashMap<Integer, Users> listadohm;
	Scanner sc = new Scanner(System.in);
	String myusername;

	public HibernateManager() {
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		HibernateUtil util = new HibernateUtil();
		session = util.getSessionFactory().openSession();

	}

	@Override
	public HashMap<Integer, Users> leer() {

		listadohm = new HashMap<Integer, Users>();
		System.out.println("Leyendo...");
		Query q = session.createQuery("select u from Users u");
		List<Entry<Integer, Users>> results = q.list();

		Iterator<Entry<Integer, Users>> it = results.iterator();
		int contador = 0;
		while (it.hasNext()) {
			Users usu = (Users) it.next();
			contador++;
			listadohm.put(contador, usu);
		}
		return listadohm;

	}

	@Override
	public void insert() {
		usu = new Users();
		usu.setUsername(sc.nextLine());
		usu.setPassword(sc.nextLine());
		usu.setDescription(sc.nextLine());
		session.beginTransaction();
		session.save(usu);
		session.getTransaction().commit();
		System.out.println("Fin Inserción Masiva");

	}

	@Override
	public boolean update(String up_username, String newPassword, String newDescription) {
		boolean state = false;
		try {
			usu = (Users) session.load(Users.class, up_username);
			System.out.println(usu);
			Hibernate.initialize(usu);

			usu.setPassword(newPassword);
			usu.setDescription(newDescription);

			session.beginTransaction();
			session.update(usu);
			session.getTransaction().commit();

			System.out.println("UPDATE CORRECTO");
			state = true;

		} catch (Exception e) {
			state = false;
			e.printStackTrace();

		}
		return state;
	}

	@Override
	public boolean deleteone(String del_username) {
		boolean state = false;
		try {
			session.beginTransaction();
			System.out.println("Introduce un username para borrar:");
			usu = (Users) session.get(Users.class, sc.nextLine());

			if (usu != null) {
				session.delete(usu);
				System.out.println("Usuario borrado");
			} else {
				System.out.println("no se ha podido borrar, ya que no existe");
			}

			session.getTransaction().commit();
			System.out.println("Fin Borrado");
			state = true;
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
				state = false;
			}
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public boolean deleteall(String option) {
		boolean state = false;
		if (option.equalsIgnoreCase("si")) {

			try {
				session.beginTransaction();
				Query q = session.createQuery("DELETE FROM Users");
				q.executeUpdate();
				System.out.println("Borrado CORRECTAMENTE");
				session.getTransaction().commit();
				System.out.println("Fin Borrado");
				listadohm.clear();
				state = true;
			} catch (Exception e) {
				e.printStackTrace();
				state = false;
			}
		}
		return state;
	}

	public void intercambiodatos() {
		try {

			BufferedReader in = new BufferedReader(new FileReader("fichero.txt"));
			String fich;
			while ((fich = in.readLine()) != null) {

				String[] partes = fich.split(";");
				if (notexistUser(partes[0])) {

					usu = new Users();
					usu.setUsername(partes[0]);
					usu.setPassword(partes[1]);
					usu.setDescription(partes[2]);

					session.beginTransaction();
					session.save(usu);
					session.getTransaction().commit();
					System.out.println(partes[0] + " introducido");
				} else {
					System.out.println("username '" + partes[0] + "' repetido");
				}
			}

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();

			System.exit(0);
		}
	}

	public boolean notexistUser(String user) throws SQLException {
		Query q = session.createQuery("select u from Users u");
		List results = q.list();
		Iterator<Entry<Integer, Users>> it = results.iterator();
		int contador = 0;
		boolean exist = it.hasNext();
		if (exist) {
			return true;
		} else {
			return false;
		}
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
		System.out.println("No implementado Insert usu");
		return false;
	}

}