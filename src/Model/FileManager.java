package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import inferface.AcessoBaseDatos;
import java.util.Properties;
import java.io.InputStream;
import java.io.OutputStream;

public class FileManager implements AcessoBaseDatos {
	private Connection conexione;
	private Scanner sc;
	protected HashMap<Integer, Usuarios> listadofile;
	private File config;
	private Properties properties;
	private InputStream input;
	private OutputStream output;
	protected BufferedReader in;
	private String myusername;
	private String mypassword;
	private String mydescription;

	public FileManager() {
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
			// System.out.println("Conectando a base de datos: " + url);
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
		FileWriter fichero = null;
		PrintWriter pw = null;

		try {

			fichero = new FileWriter("fichero.txt", true);
			pw = new PrintWriter(fichero);
			sc = new Scanner(System.in);
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
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public void delete() {
		try {

			sc = new Scanner(System.in);
			System.out.println("Introduce un username para borrar:");
			myusername = sc.nextLine();
			if (existUser(myusername)) {
				System.out.println("Borrando...");
				System.out.println("Delete proximamente");
			} else {
				System.out.println("No existe el usuario escrito");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void intercambiodatos() {

		try {

			in = new BufferedReader(new FileReader("fichero.txt"));
			String fich;
			while ((fich = in.readLine()) != null) {

				String[] partes = fich.split(";");
				if (existUser(partes[0])) {
					System.out.println("username repetido");
				} else {
					System.out.println(
							"Username: " + partes[0] + " Password: " + partes[1] + " Description: " + partes[2]);

					String query = "INSERT INTO user (username, password, description) value ('" + partes[0] + "','"
							+ partes[1] + "','" + partes[2] + "')";
					PreparedStatement stmt = conexione.prepareStatement(query);
					stmt.executeUpdate(query);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean existUser(String user) {
		String query = "SELECT username FROM user WHERE username LIKE ?";
		PreparedStatement stmt;
		try {
			stmt = conexione.prepareStatement(query);
			stmt.setString(1, user);
			ResultSet rset = stmt.executeQuery();
			boolean exist = rset.next();
			rset.close();
			stmt.close();

			if (exist) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
}
