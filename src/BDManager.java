
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
			System.out.println("Conectando a base de datos: " + url + "...");
			conexione = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: DRIVER ");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: FALLO EN CONEXION DE BASE DE DATOS");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERROR: GENERAL");
			e.printStackTrace();
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
			System.out.println("Leyendo...BBDD");
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
			System.out.println("introducce un username: ");
			myusername = sc.nextLine();
			System.out.println("introducce una password: ");
			mypassword = sc.nextLine();

			System.out.println("introducce una description: ");
			mydescription = sc.nextLine();
			// vamos a insertar un registro
			if (notexistUser(myusername)) {

				System.out.println("Insertando...");
				String query2 = "insert into user (username, password, description) value ('" + myusername + "','"
						+ mypassword + "','" + mydescription + "')";
				PreparedStatement stmt = conexione.prepareStatement(query2);
				stmt.executeUpdate(query2);
				System.out.println("Insert correcto!");
			} else {
				System.out.println("USERNAME REPETIDO");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void delete() {
		try {

			sc = new Scanner(System.in);
			System.out.println("Introduce un username para borrar:");
			myusername = sc.nextLine();
			if (notexistUser(myusername)) {
				System.out.println("No existe el usuario escrito");
			} else {
				System.out.println("Borrando...");
				String query = "DELETE FROM user WHERE username LIKE ('" + myusername + "')";
				PreparedStatement stmt = conexione.prepareStatement(query);
				stmt.executeUpdate();
				System.out.println("Delete correcto!");
				stmt.close();
			}

		} catch (Exception e) {
			System.out.println(e);
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

	}
}
