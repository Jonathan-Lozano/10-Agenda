package modelo;

import java.sql.ResultSet;

public interface Crud {
	
	public void create(String nombre, String telefono, String mail, String direccion);
		
	public void update(String nombre, String telefono, String mail, String direccion);
	
	public void delete(String nombre);

}
