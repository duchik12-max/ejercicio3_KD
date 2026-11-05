package vista;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controlador.bibliotecaController;

public class prestamoView extends JFrame {

	private static final long serialVersionUID = 1L;

	private bibliotecaController controlador;

	private JLabel lblPersona;
	private JLabel lblCodigo;
	private JLabel lblNombreLibro;
	private JTextField txtPersona;
	private JTextField txtCodigo;
	private JTextField txtNombreLibro;
	private JButton btnPrestar;

	public prestamoView(bibliotecaController controlador) {
		this.controlador = controlador;

		setTitle("Préstamos");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(420, 240);
		setLocationRelativeTo(null);

		lblPersona = new JLabel("Nombre de la persona:");
		lblCodigo = new JLabel("Código del libro:");
		lblNombreLibro = new JLabel("Nombre del libro:");
		txtPersona = new JTextField();
		txtCodigo = new JTextField();
		txtNombreLibro = new JTextField();
		txtNombreLibro.setEditable(false);
		btnPrestar = new JButton("Prestar");

		JPanel panel = new JPanel(new GridLayout(4, 2, 8, 12));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.add(lblPersona);
		panel.add(txtPersona);
		panel.add(lblCodigo);
		panel.add(txtCodigo);
		panel.add(lblNombreLibro);
		panel.add(txtNombreLibro);
		panel.add(new JLabel());
		panel.add(btnPrestar);
		setContentPane(panel);

		// Al escribir el codigo, el nombre del libro se completa solo
		txtCodigo.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				completarNombre();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				completarNombre();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				completarNombre();
			}
		});

		btnPrestar.addActionListener(e -> prestar());
	}

	private void completarNombre() {
		String codigo = txtCodigo.getText().trim();
		String nombre = controlador.nombreDeLibro(codigo);
		if (nombre != null) {
			txtNombreLibro.setText(nombre);
		} else if (codigo.length() >= 3) {
			txtNombreLibro.setText("(no existe)");
		} else {
			txtNombreLibro.setText("");
		}
	}

	private void prestar() {
		String error = controlador.prestarLibro(txtPersona.getText(), txtCodigo.getText());
		if (error != null) {
			JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, "Préstamo registrado.");
		txtPersona.setText("");
		txtCodigo.setText("");
		txtNombreLibro.setText("");
	}
}
