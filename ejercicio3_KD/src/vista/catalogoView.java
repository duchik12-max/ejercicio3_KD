package vista;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import controlador.bibliotecaController;
import modelo.libro;
import modelo.libroTableModel;

public class catalogoView extends JFrame {

	private static final long serialVersionUID = 1L;

	private bibliotecaController controlador;

	private JTabbedPane tpCatalogo;
	private JTable tblCatalogo;
	private JTable tblPrestados;
	private JButton btnDevolver;
	private libroTableModel modeloCatalogo;
	private libroTableModel modeloPrestados;

	public catalogoView(bibliotecaController controlador) {
		this.controlador = controlador;

		setTitle("Catálogo");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(520, 400);
		setLocationRelativeTo(null);

		modeloCatalogo = new libroTableModel();
		modeloPrestados = new libroTableModel();
		tblCatalogo = new JTable(modeloCatalogo);
		tblPrestados = new JTable(modeloPrestados);
		btnDevolver = new JButton("Devolver seleccionado");

		
		JPanel pestanaCatalogo = new JPanel(new BorderLayout());
		pestanaCatalogo.add(new JScrollPane(tblCatalogo), BorderLayout.CENTER);

		
		JPanel pestanaPrestados = new JPanel(new BorderLayout());
		pestanaPrestados.add(new JScrollPane(tblPrestados), BorderLayout.CENTER);
		JPanel panelBoton = new JPanel();
		panelBoton.add(btnDevolver);
		pestanaPrestados.add(panelBoton, BorderLayout.SOUTH);

		tpCatalogo = new JTabbedPane();
		tpCatalogo.addTab("Catálogo", pestanaCatalogo);
		tpCatalogo.addTab("Prestados", pestanaPrestados);
		getContentPane().add(tpCatalogo);

		btnDevolver.addActionListener(e -> devolver());

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				refrescar();
			}
		});
	}

	private void devolver() {
		int fila = tblPrestados.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un libro prestado.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		libro seleccionado = modeloPrestados.getLibroEn(fila);
		String error = controlador.devolverLibro(seleccionado.getCodigo());
		if (error != null) {
			JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Libro devuelto: ahora está disponible.");
		}
		refrescar();
	}

	private void refrescar() {
		modeloCatalogo.setLibros(controlador.obtenerTodos());
		modeloPrestados.setLibros(controlador.obtenerPrestados());
	}
}
