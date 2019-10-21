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
import inferface.AcessoBaseDatos;
import java.util.logging.*;

@SuppressWarnings("deprecation")
public class HibernateManager implements AcessoBaseDatos {
	Usuarios usu;
	Session session;
	protected HashMap<Integer, Usuarios> listadohm;
	Scanner sc = new Scanner(System.in);
	String myusername;

	public HibernateManager() {

		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		HibernateUtil util = new HibernateUtil();
		session = util.getSessionFactory().openSession();

	}

	@Override
	public HashMap<Integer, Usuarios> leer() {

		listadohm = new HashMap<Integer, Usuarios>();
		System.out.println("Leyendo...");
		Query q = session.createQuery("select u from Usuarios u");
		List<Entry<Integer, Usuarios>> results = q.list();

		Iterator<Entry<Integer, Usuarios>> it = results.iterator();
		int contador = 0;
		while (it.hasNext()) {
			Usuarios usu = (Usuarios) it.next();
			contador++;
			listadohm.put(contador, usu);
		}
		return listadohm;

	}

	@Override
	public void insert() {
		usu = new Usuarios();
		usu.setUsername(sc.nextLine());
		usu.setPassword(sc.nextLine());
		usu.setDescription(sc.nextLine());
		session.beginTransaction();
		session.save(usu);
		session.getTransaction().commit();
		System.out.println("Fin Inserción Masiva");

	}

	@Override
	public void update() {
		Usuarios usu;
		try {
			System.out.println("introduce un username: ");
			usu = (Usuarios) session.load(Usuarios.class, sc.nextLine());
			System.out.println(usu);
			Hibernate.initialize(usu);
			System.out.println("introduce password nuevo");
			usu.setPassword(sc.nextLine());
			System.out.println("introduce description");
			usu.setDescription(sc.nextLine());
			session.beginTransaction();
			session.update(usu);
			session.getTransaction().commit();
			System.out.println("UPDATE CORRECTO");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	@Override
	public void deleteuno() {
		try {
			session.beginTransaction();
			System.out.println("Introduce un username para borrar:");
			usu = (Usuarios) session.get(Usuarios.class, sc.nextLine());

			if (usu != null) {
				session.delete(usu);
				System.out.println("Usuario borrado");
			} else {
				System.out.println("no se ha podido borrar, ya que no existe");
			}

			session.getTransaction().commit();
			System.out.println("Fin Borrado");
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void deleteall() {
		System.out.println("Inicio Borrado");

		session.beginTransaction();
		Query q = session.createQuery("DELETE FROM Usuarios");
		q.executeUpdate();

		session.getTransaction().commit();
		System.out.println("Fin Borrado");

		listadohm.clear();

	}

	@Override
	public void intercambiodatos() {
		try {

			BufferedReader in = new BufferedReader(new FileReader("fichero.txt"));
			String fich;
			while ((fich = in.readLine()) != null) {

				String[] partes = fich.split(";");
				if (notexistUser(partes[0])) {

					usu = new Usuarios();
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
		Query q = session.createQuery("select u from Usuarios u");
		List results = q.list();
		Iterator<Entry<Integer, Usuarios>> it = results.iterator();
		int contador = 0;
		boolean exist = it.hasNext();
		if (exist) {
			return true;
		} else {
			return false;
		}
	}
}