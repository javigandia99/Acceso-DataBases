package inferface;

import java.util.HashMap;

import Model.Users;

public interface I_Acceso_A_Datos {

	public HashMap<Integer, Users> leer();

	public void insert();
	
	public boolean insertusu(Users newUsu);

	public void update(String up_username);

	public boolean deleteuno(String del_username);

	public boolean deleteall();

	public void intercambiodatoslist(HashMap<Integer, Users> newList);

}
