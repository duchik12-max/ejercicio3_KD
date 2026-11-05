package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class bibliotecaModel {

	public static final String ARCHIVO_LIBROS = "libros.txt";
	public static final String ARCHIVO_PRESTAMOS = "prestamos.txt";
	private static final String SEPARADOR = ";";

	private List<libro> libros = new ArrayList<libro>();

	// ---------- Libros ----------

	public void cargarLibros() throws IOException {
		libros.clear();
		File archivo = new File(ARCHIVO_LIBROS);
		if (!archivo.exists()) {
			return;
		}
		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				String[] partes = linea.split(SEPARADOR);
				if (partes.length == 3) {
					utilidades.agregarTodos(libros, new libro(partes[0], partes[1], partes[2]));
				}
			}
		}
	}

	// Reescribe todo el archivo de libros con el estado actual
	public void guardarLibros() throws IOException {
		try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_LIBROS))) {
			for (libro l : libros) {
				escritor.println(utilidades.unir(SEPARADOR, l.getCodigo(), l.getNombre(), l.getEstado()));
			}
		}
	}

	public void agregarLibro(libro nuevo) {
		utilidades.agregarTodos(libros, nuevo);
	}

	public void quitarLibro(libro l) {
		libros.remove(l);
	}

	public libro buscarLibro(String codigo) {
		for (libro l : libros) {
			if (l.getCodigo().equals(codigo)) {
				return l;
			}
		}
		return null;
	}

	public List<libro> getTodos() {
		return new ArrayList<libro>(libros);
	}

	public List<libro> getPrestados() {
		List<libro> prestados = new ArrayList<libro>();
		for (libro l : libros) {
			if (l.estaPrestado()) {
				prestados.add(l);
			}
		}
		return prestados;
	}

	// ---------- Prestamos ----------

	// Solo se agrega una linea: libro, codigo y persona. La devolucion no se registra.
	public void guardarPrestamo(prestamo p) throws IOException {
		try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_PRESTAMOS, true))) {
			escritor.println(utilidades.unir(SEPARADOR, p.getNombreLibro(), p.getCodigo(), p.getPersona()));
		}
	}
}
