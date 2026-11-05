import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import controlador.bibliotecaController;
import vista.menuView;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
			bibliotecaController controlador = new bibliotecaController();
			if (controlador.getErrorCarga() != null) {
				JOptionPane.showMessageDialog(null, controlador.getErrorCarga(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			new menuView(controlador).setVisible(true);
		});

	}

}
