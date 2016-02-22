package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import vista.VistaAgenda;

/**
 * 
 * @author Jonathan Lozano
 *
 */
public class Agenda implements Crud, BdConnection {

	private static Connection con = null;
	private static Statement ejec = null;
	private static ResultSet sent = null;
	private int eject = 0;

	/* Conexion y Gestion Base de Datos */
	public Agenda() {
		try {
			con = obtenerConexion();
			ejec = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creamos un metodo que nos conecta a la base de datos, este nos conecta a
	 * la base de datos y nos devuelve un "true" es decir que estamos
	 * conectados, si sucede algun error o entra en una excepcion este devuelve
	 * un "false" que quiere decir que no se a establecido un coneccion.
	 */
	@Override
	public boolean connect() {
		String bd = "agenda";
		String url = "jdbc:mysql://localhost/" + bd
				+ "?noAccessToProcedureBodies=true";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, "root", "");
			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error con la base de datos. "
					+ ex.getLocalizedMessage(), " Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (InstantiationException ex) {
			JOptionPane.showMessageDialog(null,
					"No se puedo establecer la conexion", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "No se econtro el Driver",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IllegalAccessException ex) {
			JOptionPane.showMessageDialog(null, "Error de compatibilidad",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Este compara nuestra variabla "con" que es de tipo Connection, si el
	 * valor de este es nulo llamara al metodo anterior (conectar()) y devuleve
	 * el valor de "con" que despues de ejecutarse el metodo anterior este ya
	 * tendra un valor osea una conexion, si el valor de esta variable no es
	 * nula simplemente devuelve el valor de "con".
	 */
	public Connection obtenerConexion() {
		if (con == null) {
			connect();
		}
		return con;
	}

	/**
	 * 
	 * @param query
	 * @return Devuelve el modelo de la tabla y recibe como parametro la
	 *         consulta a ejecutar mandando a llamar el metrodo executeQuery de
	 *         la Interfaz BdConnection, y llena la tabla para mostrar los
	 *         registros
	 */
	public DefaultTableModel llenarTabla(String query) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("");
		try {
			sent = executeQuery(query);
			while (sent.next()) {
				Object[] fila = new Object[1];
				for (int i = 0; i < fila.length; i++) {
					fila[i] = sent.getString(1);
				}
				modelo.addRow(fila);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return modelo;
	}

	/**
	 * 
	 * Llena las cajas de la ventana de contacto
	 */
	public String[] llenarForm(String query) {
		String data[] = new String[3];
		try {
			sent = executeQuery(query);
			if (sent.next()) {
				data[0] = sent.getString(1);
				data[1] = sent.getString(2);
				data[2] = sent.getString(3);
				data[3] = sent.getString(4);

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return data;
	}

	/**
	 * 
	 * @param ventana
	 * @param ventanaPrincipal
	 *            Recibe como parametro ventana que es cualquier JFrame y un
	 *            objeto de la clase VentanaPrincipal para asi poder desactivar
	 *            una ventana y mostrar la ventana principal
	 */
	public void cerrarVentana(JFrame ventana, VistaAgenda ventanaPrincipal) {
		ventana.dispose();
		ventanaPrincipal.setVisible(true);
	}

	
	@Override
	public void create(String nombre, String telefono, String mail,
			String direccion) {
		try {

			sent = executeQuery(String
					.format("SELECT * FROM contacto WHERE nombre LIKE '%s' OR telefono LIKE '%s'",
							nombre, telefono));
			if (sent.next()) {
				JOptionPane.showMessageDialog(null, "El contacto \"" + nombre
						+ "\" ya fue registrado.", "Error de usuario",
						JOptionPane.WARNING_MESSAGE);
			} else {

				if (nombre.isEmpty() || telefono.isEmpty() || mail.isEmpty()
						|| direccion.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Ningun campo puede ir vacio", "Error de usuario",
							JOptionPane.WARNING_MESSAGE);
				} else {
					eject = executeSentence(String
							.format("INSERT INTO contacto VALUES(null, '%s', '%s', '%s', '%s')",
									nombre, telefono, mail, direccion));
					if (eject != 0) {
						JOptionPane.showMessageDialog(null,
								"Contacto Registrado", "Registro exitoso",
								JOptionPane.INFORMATION_MESSAGE);
						llenarTabla("SELECT * FROM contacto");
					} else {
						JOptionPane.showMessageDialog(null,
								"El contacto no se pudo guardar",
								"Registro erroneo", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error: " + ex);
		}
	}

	
	@Override
	public void update(String nombre, String telefono, String mail,
			String direccion) {
		try {
			String telefono2 = telefono;

			if (nombre.isEmpty() || telefono.isEmpty() || mail.isEmpty()
					|| direccion.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Ningun campo puede ir vacio", "Error de usuario",
						JOptionPane.WARNING_MESSAGE);
			} else {
				sent = executeQuery(String
						.format("SELECT * FROM contacto WHERE nombre LIKE '%s' OR telefono LIKE '%s'",
								nombre, telefono));

				if (sent.next()) {

					JOptionPane.showMessageDialog(null, "El contacto \""
							+ nombre + "\" ya fue registrado.",
							"Error de usuario", JOptionPane.WARNING_MESSAGE);
				} else {

					eject = executeSentence(String
							.format("UPDATE contacto SET nombre = '%s', telefono = '%s', mail = '%s', direccion = '%s' WHERE telefono = '%s'",
									nombre, telefono, mail, direccion,
									telefono2));

					if (eject != 0) {

						llenarTabla("SELECT * FROM contacto");

						JOptionPane.showMessageDialog(null,
								"Usuario modificado", "Modificacion exitosa",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						JOptionPane.showMessageDialog(null,
								"No se pudieron guardar los cambios",
								"Modificacion erronea " + sent,
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error: " + ex);
		}
	}

	
	@Override
	public void delete(String nombre) {

		int resp = JOptionPane.showConfirmDialog(null,
				"¿Estas seguro de eliminar al contacto " + nombre + "?",
				"Eliminar", 2);

		if (resp == 0) {
			eject = executeSentence(String.format(
					"DELETE FROM contacto WHERE nombre LIKE '%s'", nombre));
			if (eject != 0) {
				llenarTabla("SELECT * FROM contacto");
				JOptionPane.showMessageDialog(null, "Se elimino el usuario");
			} else {
				JOptionPane.showMessageDialog(null,
						"No se pudo elimino el usuario");
			}
		} else {
			JOptionPane.showMessageDialog(null, "No se elimino el usuario");
		}
	}

	
	
	/**
	 * Se ejecutan las sentencias de movimientos
	 */
	@Override
	public int executeSentence(String query) {
		try {
			eject = ejec.executeUpdate(query);
			JOptionPane.showMessageDialog(null, eject + " Movimiento exitoso");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return eject;
	}

	
	/**
	 * Se ejecutan las consultas recibiendo como parametro la consulta
	 */
	@Override
	public ResultSet executeQuery(String query) {
		try {
			sent = ejec.executeQuery(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Error al ejecutar la operacion\n" + e);
		}
		return sent;
	}

}