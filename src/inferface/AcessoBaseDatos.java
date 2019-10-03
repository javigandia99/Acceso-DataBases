package inferface;

import java.util.HashMap;

import Model.Usuarios;

public interface AcessoBaseDatos {

	public HashMap<Integer, Usuarios> leer();

	public void insert();

	public void update();

	public void deleteuno();

	public void deleteall();

	public void intercambiodatos();

}
