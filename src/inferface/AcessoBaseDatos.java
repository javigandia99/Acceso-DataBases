package inferface;
import java.sql.Connection;
import java.util.HashMap;

import Model.Usuarios;

public interface AcessoBaseDatos {

	public Connection getConnection();

	public HashMap<Integer, Usuarios> leer();

	public void insert();

	public void delete();

	public void intercambiodatos();
}
