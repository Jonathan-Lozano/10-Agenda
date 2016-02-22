package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Agenda;

public class VistaGrupos extends JFrame implements ActionListener {

	private JPanel cont = new JPanel();

	private JLabel lblNombre = new JLabel("Nombre del grupo");
	private JTextField txtNombre = new JTextField();

	private JButton btnCancelar = new JButton("Cancelar");
	private JButton btnGuardar = new JButton("Guardar");

	private JLabel lblGrupo = new JLabel("Agregar a grupo: ");
	JComboBox cmbGrupos = new JComboBox(new String[] { "Selecciona un grupo" });

	private VistaContacto vc = new VistaContacto();
	private Agenda agenda = new Agenda();
	 
	
	public VistaGrupos() {
		super("Grupos");
		setSize(381, 353);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				vc.enable();
			}
		});
		cargarControles();
	}

	private void cargarControles() {
		cont.setBackground(Color.WHITE);
		setContentPane(cont);
		cont.setLayout(null);

		lblNombre.setBounds(61, 68, 188, 14);
		cont.add(lblNombre);

		txtNombre.setBounds(91, 84, 158, 20);
		cont.add(txtNombre);

		lblGrupo.setBounds(61, 130, 158, 20);
		cont.add(lblGrupo);

		cmbGrupos.setBounds(61, 170, 158, 50);
		cont.add(cmbGrupos);

		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBounds(0, 286, 188, 39);
		btnCancelar.addActionListener(this);
		cont.add(btnCancelar);

		btnGuardar.setBackground(Color.WHITE);
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setBounds(188, 286, 188, 39);
		btnGuardar.addActionListener(this);
		cont.add(btnGuardar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
