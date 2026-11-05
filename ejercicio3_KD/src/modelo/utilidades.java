package modelo;

import java.util.List;

public class utilidades {

	// agrega varios elementos a una lista
	@SafeVarargs
	public static <T> void agregarTodos(List<T> lista, T... elementos) {
		for (T elemento : elementos) {
			lista.add(elemento);
		}
	}

	// une varios valores con un separador sirve para armar cada linea de los archivos txt
	@SafeVarargs
	public static <T> String unir(String separador, T... elementos) {
		StringBuilder linea = new StringBuilder();
		for (int i = 0; i < elementos.length; i++) {
			if (i > 0) {
				linea.append(separador);
			}
			linea.append(elementos[i]);
		}
		return linea.toString();
	}

	//revisa si alguno de los campos esta vacio
	public static boolean hayVacios(String... campos) {
		for (String campo : campos) {
			if (campo == null || campo.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
