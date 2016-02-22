package modelo;

import java.sql.ResultSet;

public interface BdConnection {

	public boolean connect();
	
	public int executeSentence(String query);
	
	public ResultSet executeQuery(String query);
	
}
