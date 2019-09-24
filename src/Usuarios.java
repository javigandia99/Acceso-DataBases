
public class Usuarios {
	protected String username;
	protected int password;
	protected String description;

	public Usuarios(String username, int password, String description) {
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

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
