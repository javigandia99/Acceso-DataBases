package Model;

public class Usuarios {

	protected String username;
	protected String password;
	protected String description;

	public Usuarios() {

	}

	public Usuarios(String username, String password, String description) {
		this.username = username;
		this.password = password;
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "Username: " + username + "\n" + "    Password: " + password + "\n" + "    Description: " + description
				+ "\n";
	}

	public String tofichero() {
		return username + ";" + password + ";" + description;
	}
}
