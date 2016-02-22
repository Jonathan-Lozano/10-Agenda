package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import modelo.Agenda;

public class VistaContacto extends JFrame implements ActionListener {

	private JPanel cont = new JPanel();

	private JLabel lblContacto = new JLabel("Contacto");

	private JLabel lblNombre = new JLabel("Nombre");
	private JTextField txtNombre = new JTextField();
	private JLabel lblNumero = new JLabel("Numero");
	private JTextField txtNumero = new JTextField();
	private JLabel lblEmail = new JLabel("E-mail");
	private JTextField txtMail = new JTextField();
	private JLabel lblDireccion = new JLabel("Direccion");
	private JTextField txtDireccion = new JTextField();

	private JButton btnCancelar = new JButton("Cancelar");
	private JButton btnGuardar = new JButton("Guardar");
	private JButton btnEliminar = new JButton("Borrar");

	private VistaAgenda vistaAgenda = new VistaAgenda();
	private Agenda agenda = new Agenda();

	public VistaContacto(String contacto) {
		super(contacto);
		setSize(382, 492);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		cargarControles();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				vistaAgenda.setVisible(true);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				llenarCajas(agenda
						.llenarForm(String
								.format("SELECT nombre, telefono, mail, direccion FROM contacto WHERE nombre LIKE '%s'",
										contacto)));
			}
		});
	}

	public VistaContacto() {
	}

	private void cargarControles() {
		cont.setBackground(Color.WHITE);
		setContentPane(cont);
		cont.setLayout(null);

		lblContacto.setBounds(27, 25, 32, 32);
		lblContacto.setIcon(new ImageIcon(getClass().getResource(
				"/img/contacto.png")));
		cont.add(lblContacto);

		lblNombre.setBounds(61, 68, 188, 14);
		cont.add(lblNombre);

		txtNombre.setBounds(91, 84, 158, 20);
		cont.add(txtNombre);
		txtNombre.setColumns(10);

		lblNumero.setBounds(61, 126, 188, 14);
		cont.add(lblNumero);

		txtNumero.setBounds(91, 139, 158, 20);
		cont.add(txtNumero);
		txtNumero.setColumns(10);

		lblEmail.setBounds(65, 180, 184, 14);
		cont.add(lblEmail);

		txtMail.setBounds(91, 193, 158, 20);
		cont.add(txtMail);
		txtMail.setColumns(10);

		lblDireccion.setBounds(58, 234, 191, 14);
		cont.add(lblDireccion);

		txtDireccion.setBounds(91, 250, 158, 20);
		cont.add(txtDireccion);
		txtDireccion.setColumns(10);

		btnEliminar.setBackground(Color.WHITE);
		btnEliminar.setBounds(270, 25, 97, 23);
		cont.add(btnEliminar);

		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBounds(0, 424, 188, 39);
		btnCancelar.addActionListener(this);
		cont.add(btnCancelar);

		btnGuardar.setBackground(Color.WHITE);
		btnGuardar.setBounds(188, 424, 188, 39);
		btnGuardar.addActionListener(this);
		cont.add(btnGuardar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGuardar) {
			guardar();
		} else if (e.getSource() == btnCancelar) {
			agenda.cerrarVentana(this, vistaAgenda);
		} else if (e.getSource() == btnEliminar) {
			eliminar();
		}
	}

	public void llenarCajas(String data[]) {
		for (int i = 0; i <= data.length; i++) {
			txtNombre.setText(data[0]);
			txtNumero.setText(data[1]);
			txtMail.setText(data[2]);
			txtDireccion.setText(data[3]);
		}
	}

	public void eliminar() {
		String nombre = txtNombre.getText();
		agenda.delete(nombre);
	}

	public void guardar() {
		String nombre = txtNombre.getText();
		String telefono = txtNumero.getText();
		String mail = txtMail.getText();
		String direccion = txtDireccion.getText();

		agenda.update(nombre, telefono, mail, direccion);
	}

}
