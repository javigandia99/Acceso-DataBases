package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import inferface.AcessoBaseDatos;
import java.util.HashMap;
import java.util.Properties;

public class BDManager implements AcessoBaseDatos {

	private Connection conexione;
	private File config;
	private Properties properties;
	private InputStream input;
	private OutputStream output;
	private Scanner sc;
	protected HashMap<Integer, Usuarios> listadobd;
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
	public HashMap<Integer, Usuarios> leer() {
		listadobd = new HashMap<Integer, Usuarios>();
		Usuarios usu;
		int contador = 0;
		try {
			String query = "SELECT * FROM user";
			PreparedStatement stmt = conexione.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			System.out.println("Query ejecutada correctamente");
			while (rs.next()) {
				contador++;
				myusername = rs.getString(1);
				mypassword = rs.getString(2);
				mydescription = rs.getString(3);
				usu = new Usuarios(myusername, mypassword, mydescription);
				listadobd.put(contador, usu);
			}

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return listadobd;

	}

	@Override
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
	public void update() {
		try {

			sc = new Scanner(System.in);
			System.out.println("introduce un username: ");
			myusername = sc.nextLine();
			// vamos a modificar un registro
			if (notexistUser(myusername)) {
				System.out.println("USERNAME NO EXISTE");

			} else {

				System.out.println("Introduce que quieres modificar: todo   password    description");
				String posibilidad = sc.nextLine();
				String query2 = null;
				String nuevopassword = null;
				String nuevodescription = null;
				switch (posibilidad) {
				case "todo":
					System.out.println("El username NUNCA se va a poder Modificar");
					System.out.println("Nuevo password:");
					nuevopassword = sc.nextLine();
					System.out.println("Nueva description:");
					nuevodescription = sc.nextLine();

					query2 = "UPDATE user set db_password =  '" + nuevopassword + "', db_description =  '"
							+ nuevodescription + "' WHERE db_username = '" + myusername + "'";
					break;

				case "password":

					System.out.println("Nuevo password:");
					nuevopassword = sc.nextLine();
					query2 = "UPDATE user set db_password =  '" + nuevopassword + "' WHERE db_username = '" + myusername
							+ "'";
					break;

				case "description":
					System.out.println("Nueva description:");
					nuevodescription = sc.nextLine();
					query2 = "UPDATE user set db_description =  '" + nuevodescription + "' WHERE db_username = '"
							+ myusername + "'";
					break;
				}

				PreparedStatement stmt = conexione.prepareStatement(query2);
				stmt.executeUpdate(query2);
				System.out.println("Update correcto!");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void deleteuno() {
		try {

			sc = new Scanner(System.in);
			System.out.println("Introduce un username para borrar:");
			myusername = sc.nextLine();
			if (notexistUser(myusername)) {
				System.out.println("No existe el usuario escrito");
			} else {
				System.out.println("Borrando...");
				String query = "DELETE FROM user WHERE db_username LIKE ('" + myusername + "')";
				PreparedStatement stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Delete correcto!");
				stmt.close();
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void deleteall() {
		System.out.println("¿Estas seguro de borrar todo el contenido de la BBDD SQl?");
		System.out.println("No habra vuelta atras...");
		String opcion = sc.nextLine();
		if (opcion == "si") {
			System.out.println("Delete ALL correcto!");
			String query = "TRUNCATE user";
			PreparedStatement stmt;
			try {
				stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Fallo en ejecutar delete all");
				e.printStackTrace();
			}

		} else {
			System.out.println("NO HA BORRADO NADA");
		}

	}

	public boolean notexistUser(String user) throws SQLException {
		String query = "SELECT username FROM user WHERE username LIKE ?";
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
	public void intercambiodatos() {
		// DE FICHERO A BASE DE DATOS
		try {

			BufferedReader in = new BufferedReader(new FileReader("fichero.txt"));
			String fich;
			while ((fich = in.readLine()) != null) {

				String[] partes = fich.split(";");
				if (notexistUser(partes[0])) {
					System.out.println(
							"Username: " + partes[0] + " Password: " + partes[1] + " Description: " + partes[2]);

					String query = "INSERT INTO user (db_username, db_password, db_description) value ('" + partes[0]
							+ "','" + partes[1] + "','" + partes[2] + "')";
					PreparedStatement stmt = conexione.prepareStatement(query);
					stmt.executeUpdate(query);

				} else {
					System.out.println("username repetido");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean insertarusu(Usuarios usu) {
		HashMap<Integer, Usuarios> lista = leer();
		String username = usu.getUsername();

		PreparedStatement pstm;
		try {
			pstm = conexione
					.prepareStatement("insert into user (db_username,db_password,db_description) values (?,?,?)");
			pstm.setString(1, usu.getUsername());
			pstm.setString(2, usu.getPassword());
			pstm.setString(3, usu.getDescription());

			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void intercambiodatoslist(HashMap<Integer, Usuarios> listanueva) {
		deleteall();
		for (Entry<Integer, Usuarios> entry : listanueva.entrySet()) {
			insertarusu(listanueva.get(entry.getKey()));
		}
		System.out.println("INTERCAMBIO DE BBDD SQL CORRECTO");
	}

}
