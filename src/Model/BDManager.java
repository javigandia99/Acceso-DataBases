package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Map.Entry;
import inferface.I_Acceso_A_Datos;
import java.util.HashMap;
import java.util.Properties;

public class BDManager implements I_Acceso_A_Datos {

	private Connection conexione;
	private File config;
	private Properties properties;
	private InputStream input;
	private OutputStream output;
	private Scanner sc;
	protected HashMap<Integer, Users> listadobd;
	private String myusername;
	private String mypassword;
	private String mydescription;

	public BDManager() {
		config = new File("src/configuracion.ini");
		properties = new Properties();
		output = null;
		getConnection();
	}

	public Connection getConnection() {
		String url = getProperty("url");
		String user = getProperty("user");
		String pass = getProperty("pass");
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver);
			conexione = DriverManager.getConnection(url, user, pass);
			System.out.println("CONEXIÓN CON BBDD SQL CORRECTA");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: DRIVER ");
			System.exit(-1);

		} catch (SQLException e) {
			System.out.println("ERROR: FALLO EN CONEXION DE BASE DE DATOS");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("ERROR: GENERAL");
			e.printStackTrace();
			System.exit(-1);
		}
		return conexione;
	}

	public String getProperty(String key) {
		input = null;
		try {
			input = new FileInputStream(config);
			properties.load(input);
		} catch (Exception e) {
			System.out.println("FALLO EN LECTURA DE FICHERO.INI" + e);
		}
		String property = properties.getProperty(key);
		return property;
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
		try {
			output = new FileOutputStream(config);
			properties.store(output, "");

		} catch (IOException e) {
			System.out.println("Algo salió mal");
		}
	}

	@Override
	public HashMap<Integer, Users> leer() {
		listadobd = new HashMap<Integer, Users>();
		Users usu;
		int contador = 0;
		try {
			String query = "SELECT * FROM user";
			PreparedStatement stmt = conexione.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				contador++;
				myusername = rs.getString(1);
				mypassword = rs.getString(2);
				mydescription = rs.getString(3);
				usu = new Users(myusername, mypassword, mydescription);
				listadobd.put(contador, usu);
			}

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return listadobd;

	}

	public void insert() {
		try {
			sc = new Scanner(System.in);
			System.out.println("introduce un username: ");
			myusername = sc.nextLine();
			System.out.println("introduce una password: ");
			mypassword = sc.nextLine();

			System.out.println("introduce una description: ");
			mydescription = sc.nextLine();
			// vamos a insertar un registro
			if (notexistUser(myusername)) {
				String query2 = "insert into user (db_username,db_password,db_description) value ('" + myusername
						+ "','" + mypassword + "','" + mydescription + "')";
				PreparedStatement stmt = conexione.prepareStatement(query2);
				stmt.executeUpdate(query2);
				System.out.println("Insert correcto!");
			} else {
				System.out.println("USERNAME REPETIDO");
			}

		} catch (SQLException e) {
			e.getMessage();
		}

	}

	@Override
	public boolean update(String up_username, String newPassword, String newDescription) {
		boolean state = false;
		try {
			if (notexistUser(up_username)) {
				System.out.println("USERNAME NO EXISTE");
				state = false;
			}
			System.out.println("El username NUNCA se va a poder Modificar");

			String query2 = "UPDATE user set db_password =  '" + newPassword + "', db_description =  '" + newDescription
					+ "' WHERE db_username = '" + myusername + "'";

			PreparedStatement stmt = conexione.prepareStatement(query2);
			stmt.executeUpdate(query2);
			System.out.println("Update correcto!");
			state = true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return state;
	}

	@Override
	public boolean deleteone(String del_username) {
		boolean state = false;
		try {

			if (notexistUser(del_username)) {
				System.out.println("No existe el usuario escrito");
				return false;
			} else {
				System.out.println("Borrando...");
				String query = "DELETE FROM user WHERE db_username LIKE ('" + myusername + "')";
				PreparedStatement stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Delete correcto!");
				stmt.close();
				state = true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return state;
	}

	@Override
	public boolean deleteall(String option) {
		boolean state = false;
		if (option.equalsIgnoreCase("si")) {

			String query = "DELETE FROM user";
			PreparedStatement stmt;
			try {
				stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Delete ALL correcto!");
				state = true;
			} catch (SQLException e) {
				System.err.println("Fallo en ejecutar: delete all");
				e.printStackTrace();
				state = false;
			}
		}
		return state;
	}

	public boolean notexistUser(String user) throws SQLException {
		String query = "SELECT username FROM user WHERE db_username LIKE ?";
		PreparedStatement stmt = conexione.prepareStatement(query);
		stmt.setString(1, user);
		ResultSet rset = stmt.executeQuery();
		boolean exist = rset.next();
		rset.close();
		stmt.close();
		if (exist) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean insertusu(Users usu) {
		HashMap<Integer, Users> listaadd = leer();
		PreparedStatement stmt;
		try {
			stmt = conexione.prepareStatement("SELECT * FROM user WHERE db_username LIKE '" + usu.getUsername() + "'");
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				for (Entry<Integer, Users> entry : listaadd.entrySet()) {
					if (entry.getValue().getUsername().equals(usu.getUsername())) {
						System.out.println("Username repetido");
						return false;
					}
				}
			}

			myusername = usu.getUsername();
			mypassword = usu.getPassword();
			mydescription = usu.getDescription();
			stmt = conexione.prepareStatement("insert into user (db_username,db_password,db_description) value ('"
					+ myusername + "','" + mypassword + "','" + mydescription + "')");
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void intercambiodatoslist(HashMap<Integer, Users> newList) {
		deleteall("si");
		for (Entry<Integer, Users> entry : newList.entrySet()) {
			insertusu(newList.get(entry.getKey()));
		}
		System.out.println("INTERCAMBIO DE BBDD SQL CORRECTO");
	}

}
