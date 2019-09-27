package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import inferface.AcessoBaseDatos;

public class FileManager implements AcessoBaseDatos {
	private Connection conexione;
	private File config;
	private Properties properties;
	private InputStream input;
	private OutputStream output;
	protected HashMap<Integer, Usuarios> listadofile;
	protected BDManager bd = new BDManager();
	protected BufferedReader in;
	private String myusername;
	private String mypassword;
	private String mydescription;

	public FileManager() {
		config = new File("src/configuracion.ini");
		properties = new Properties();
		output = null;

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
		listadofile = new HashMap<Integer, Usuarios>();
		Usuarios usu;
		int contador = 0;

		try {
			in = new BufferedReader(new FileReader("fichero.txt"));
			String fichero;
			System.out.println("Leyendo...");
			while ((fichero = in.readLine()) != null) {
				String[] partes = fichero.split(";");
				contador++;
				myusername = partes[0];
				mypassword = partes[1];
				mydescription = partes[2];
				usu = new Usuarios(myusername, mypassword, mydescription);
				listadofile.put(contador, usu);
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
		}
		sc.close();
	}

	@Override
	public void delete() {
		System.out.println("*Proximamente estara disponible*");
	}

	@Override
	public void intercambiodatos() {

		try {
			getConnection();
			String fichero;

			while ((fichero = in.readLine()) != null) {
				String[] partes = fichero.split(";");
				if (notexistUser(partes[0])) {
					System.out.println("username repetido");
				} else {
					System.out.println(
							"Username: " + partes[0] + " Password: " + partes[1] + " Description: " + partes[2]);

					String query = "insert into user (username, password, description) value ('" + partes[0] + "','"
							+ partes[1] + "','" + partes[2] + "')";
					PreparedStatement stmt = conexione.prepareStatement(query);
					stmt.executeUpdate(query);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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

}
