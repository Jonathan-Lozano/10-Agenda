package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;

import modelo.Agenda;

public class VistaAgenda extends JFrame implements ActionListener {

	private JPanel cont = new JPanel();

	private JTabbedPane tbpAgenda = new JTabbedPane();
	private JPanel pContactos = new JPanel();
	private JPanel pFavoritos = new JPanel();
	private JPanel pGrupos = new JPanel();

	private JLabel lblBuscar = new JLabel("   Buscar Contacto");
	private JTextField txtBuscar = new JTextField();
	private JButton btnNuevoContacto = new JButton();
	private JButton btnBorrar = new JButton();

	public static JTable tblContactos = new JTable();
	private JScrollPane scpTabla = new JScrollPane();
	private DefaultTableModel model = new DefaultTableModel();

	private Agenda app = new Agenda();
	private VistaContactoNuevo vcnuevo = new VistaContactoNuevo();

	private JPopupMenu popup;

	public VistaAgenda() {
		super("Agenda");
		setSize(300, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				cargarModelo();
			}
		});
		cargarControles();
	}

	private void cargarControles() {

		pContactos.setLayout(null);

		cont.setLayout(null);
		cont.setBounds(45, 10, 170, 30);
		cont.setBackground(Color.WHITE);

		lblBuscar.setBounds(10, 5, 200, 20);
		lblBuscar.setIcon(new ImageIcon(getClass().getResource(
				"/img/buscar.png")));
		cont.add(lblBuscar);

		txtBuscar.setBounds(45, 0, 100, 30);
		txtBuscar.setBorder(null);
		txtBuscar.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				if (txtBuscar.getText().isEmpty()) {
					lblBuscar.setText("   Buscar Contacto");
					btnBorrar.setVisible(false);
					cargarModelo();
				} else {
					btnBorrar.setVisible(true);
					lblBuscar.setText("");
					searchBy(txtBuscar.getText());
				}
			}
		});
		cont.add(txtBuscar);

		btnBorrar.setBounds(147, 7, 16, 16);
		btnBorrar.setBorder(null);
		btnBorrar.setBackground(null);
		btnBorrar.setIcon(new ImageIcon(getClass().getResource(
				"/img/borrar.png")));
		btnBorrar.setVisible(false);
		btnBorrar.addActionListener(this);
		cont.add(btnBorrar);

		pContactos.add(cont);

		btnNuevoContacto.setBounds(215, 10, 30, 30);
		btnNuevoContacto.setBorder(null);
		btnNuevoContacto.setBackground(Color.WHITE);
		btnNuevoContacto.setIcon(new ImageIcon(getClass().getResource(
				"/img/nuevo.png")));
		btnNuevoContacto.addActionListener(this);
		pContactos.add(btnNuevoContacto);

		scpTabla.setViewportView(tblContactos);
		scpTabla.setBounds(0, 50, 415, 345);
		tblContactos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tblContactos.getSelectedRow();
				String contacto = (String) tblContactos.getModel().getValueAt(
						fila, 0);
//				dispose();
//				VistaContacto contact = new VistaContacto(contacto);
//				contact.setVisible(true);
				System.out.println(contacto);
			}
		});
		tblContactos.setRowHeight(50);
		tblContactos.setFont(new Font("Tahoma", 0, 20));
		pContactos.add(scpTabla);

		tbpAgenda.addTab("Contactos",
				new ImageIcon(getClass().getResource("/img/contactos.png")),
				pContactos, "Agregar Contacto y Buscar");

		// Se agrega el contenedor de pestañas a la ventana
		add(tbpAgenda);
	}

	private void searchBy(String letras) {
		model = app
				.llenarTabla(String
						.format("SELECT nombre FROM contacto WHERE nombre LIKE '%s%%' OR telefono LIKE '%s%%' OR mail LIKE '%s' OR direccion LIKE '%s'",
								letras, letras, letras, letras));
		tblContactos.setModel(model);
	}

	public void cargarModelo() {
		model = app.llenarTabla("Select nombre from contacto");
		tblContactos.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNuevoContacto) {

			this.disable();
			vcnuevo.setVisible(true);

		} else if (e.getSource() == btnBorrar) {

			txtBuscar.setText("");
			cargarModelo();

		}
	}
}