package vista;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controlador.bibliotecaController;

public class menuView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblTitulo;
	private JButton btnLibros;
	private JButton btnPrestamos;
	private JButton btnCatalogo;
	private JButton btnSalir;

	private libroView libros;
	private prestamoView prestamos;
	private catalogoView catalogo;

	public menuView(bibliotecaController controlador) {
		setTitle("Biblioteca");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(320, 300);
		setLocationRelativeTo(null);

		libros = new libroView(controlador);
		prestamos = new prestamoView(controlador);
		catalogo = new catalogoView(controlador);

		lblTitulo = new JLabel("Gestión de Biblioteca", SwingConstants.CENTER);
		btnLibros = new JButton("Registrar / eliminar libros");
		btnPrestamos = new JButton("Registrar préstamo");
		btnCatalogo = new JButton("Catálogo");
		btnSalir = new JButton("Salir");

		JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		panel.add(lblTitulo);
		panel.add(btnLibros);
		panel.add(btnPrestamos);
		panel.add(btnCatalogo);
		panel.add(btnSalir);
		setContentPane(panel);

		btnLibros.addActionListener(e -> libros.setVisible(true));
		btnPrestamos.addActionListener(e -> prestamos.setVisible(true));
		btnCatalogo.addActionListener(e -> catalogo.setVisible(true));
		btnSalir.addActionListener(e -> System.exit(0));
	}
}
