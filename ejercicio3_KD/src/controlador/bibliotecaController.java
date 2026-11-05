package controlador;

import java.io.IOException;
import java.util.List;

import modelo.bibliotecaModel;
import modelo.libro;
import modelo.prestamo;
import modelo.utilidades;


public class bibliotecaController {

	private bibliotecaModel modelo = new bibliotecaModel();
	private String errorCarga = null;

	public bibliotecaController() {
		try {
			modelo.cargarLibros();
		} catch (IOException e) {
			errorCarga = "No se pudo leer " + bibliotecaModel.ARCHIVO_LIBROS + ": " + e.getMessage();
		}
	}

	public String getErrorCarga() {
		return errorCarga;
	}

	// ---------- Libros ----------

	public String registrarLibro(String codigo, String nombre) {
		codigo = codigo.trim();
		nombre = nombre.trim();

		if (utilidades.hayVacios(codigo, nombre)) {
			return "Debe llenar el código y el nombre del libro.";
		}
		if (!codigo.matches("\\d{3}")) {
			return "El código debe tener exactamente 3 dígitos numéricos.";
		}
		if (nombre.contains(";")) {
			return "El nombre no puede contener el carácter ';'.";
		}
		if (modelo.buscarLibro(codigo) != null) {
			return "Ya existe un libro con el código " + codigo + ".";
		}

		modelo.agregarLibro(new libro(codigo, nombre));
		return guardarLibros();
	}

	public String eliminarLibro(String codigo) {
		libro l = modelo.buscarLibro(codigo);
		if (l == null) {
			return "El libro no existe.";
		}
		if (l.estaPrestado()) {
			return "No se puede eliminar el libro \"" + l.getNombre() + "\" porque está prestado.";
		}
		modelo.quitarLibro(l);
		return guardarLibros();
	}

	// ---------- Prestamos ----------

	public String prestarLibro(String persona, String codigo) {
		persona = persona.trim();
		codigo = codigo.trim();

		if (utilidades.hayVacios(persona, codigo)) {
			return "Debe llenar el nombre de la persona y el código del libro.";
		}
		if (persona.contains(";")) {
			return "El nombre de la persona no puede contener el carácter ';'.";
		}
		libro l = modelo.buscarLibro(codigo);
		if (l == null) {
			return "No existe un libro con el código " + codigo + ".";
		}
		if (l.estaPrestado()) {
			return "El libro \"" + l.getNombre() + "\" ya está prestado.";
		}

		l.setEstado(libro.PRESTADO);
		try {
			modelo.guardarPrestamo(new prestamo(l.getNombre(), l.getCodigo(), persona));
		} catch (IOException e) {
			l.setEstado(libro.DISPONIBLE);
			return "No se pudo guardar el préstamo: " + e.getMessage();
		}
		return guardarLibros();
	}

	public String devolverLibro(String codigo) {
		libro l = modelo.buscarLibro(codigo);
		if (l == null) {
			return "El libro no existe.";
		}
		if (!l.estaPrestado()) {
			return "El libro ya está disponible.";
		}
		l.setEstado(libro.DISPONIBLE);
		return guardarLibros();
	}

	// ---------- Consultas ----------

	// Devuelve el nombre del libro o null si el codigo no existe
	public String nombreDeLibro(String codigo) {
		libro l = modelo.buscarLibro(codigo);
		if (l == null) {
			return null;
		}
		return l.getNombre();
	}

	public List<libro> obtenerTodos() {
		return modelo.getTodos();
	}

	public List<libro> obtenerPrestados() {
		return modelo.getPrestados();
	}

	private String guardarLibros() {
		try {
			modelo.guardarLibros();
			return null;
		} catch (IOException e) {
			return "No se pudo guardar " + bibliotecaModel.ARCHIVO_LIBROS + ": " + e.getMessage();
		}
	}
}
