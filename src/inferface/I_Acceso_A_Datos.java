package inferface;

import java.util.HashMap;

import model.Users;

public interface I_Acceso_A_Datos {

	public HashMap<Integer, Users> leer();

	public boolean insertusu(Users newUsu);

	public boolean update(String up_username, String newPassword, String newDescription);

	public boolean deleteone(String del_username);

	public boolean deleteall(String option);

	public void intercambiodatoslist(HashMap<Integer, Users> newList);

}
