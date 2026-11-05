package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controlador.bibliotecaController;
import modelo.libro;
import modelo.libroTableModel;

public class libroView extends JFrame {

	private static final long serialVersionUID = 1L;

	private bibliotecaController controlador;

	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JButton btnRegistrar;
	private JButton btnEliminar;
	private JTable tblLibros;
	private libroTableModel modeloTabla;

	public libroView(bibliotecaController controlador) {
		this.controlador = controlador;

		setTitle("Libros");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(480, 400);
		setLocationRelativeTo(null);

		lblCodigo = new JLabel("Código (3 dígitos):");
		lblNombre = new JLabel("Nombre del libro:");
		txtCodigo = new JTextField();
		txtNombre = new JTextField();
		btnRegistrar = new JButton("Registrar");
		btnEliminar = new JButton("Eliminar seleccionado");

		modeloTabla = new libroTableModel();
		tblLibros = new JTable(modeloTabla);

		JPanel panelForm = new JPanel(new GridLayout(3, 2, 8, 8));
		panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelForm.add(lblCodigo);
		panelForm.add(txtCodigo);
		panelForm.add(lblNombre);
		panelForm.add(txtNombre);
		panelForm.add(new JLabel());
		panelForm.add(btnRegistrar);

		JPanel panelSur = new JPanel();
		panelSur.add(btnEliminar);

		setLayout(new BorderLayout());
		add(panelForm, BorderLayout.NORTH);
		add(new JScrollPane(tblLibros), BorderLayout.CENTER);
		add(panelSur, BorderLayout.SOUTH);

		btnRegistrar.addActionListener(e -> registrar());
		btnEliminar.addActionListener(e -> eliminar());

		// Cada vez que la ventana se activa se actualiza la tabla
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				refrescar();
			}
		});
	}

	private void registrar() {
		String error = controlador.registrarLibro(txtCodigo.getText(), txtNombre.getText());
		if (error != null) {
			JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, "Libro registrado como disponible.");
		txtCodigo.setText("");
		txtNombre.setText("");
		refrescar();
	}

	private void eliminar() {
		int fila = tblLibros.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		libro seleccionado = modeloTabla.getLibroEn(fila);
		int opcion = JOptionPane.showConfirmDialog(this,
				"¿Eliminar el libro \"" + seleccionado.getNombre() + "\"?", "Confirmar",
				JOptionPane.YES_NO_OPTION);
		if (opcion != JOptionPane.YES_OPTION) {
			return;
		}
		String error = controlador.eliminarLibro(seleccionado.getCodigo());
		if (error != null) {
			JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
		refrescar();
	}

	private void refrescar() {
		modeloTabla.setLibros(controlador.obtenerTodos());
	}
}
