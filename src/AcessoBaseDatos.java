import java.util.HashMap;

public interface AcessoBaseDatos {

	public HashMap<Integer, Usuarios> leer();
	public void insert();
	public void delete();
	public void intercambiodatos();
}
