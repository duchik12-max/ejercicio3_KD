package modelo;

public class libro {

	public static final String DISPONIBLE = "Disponible";
	public static final String PRESTADO = "Prestado";

	private String codigo;
	private String nombre;
	private String estado;

	// Libro nuevo: siempre queda disponible (el estado no se ingresa)
	public libro(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.estado = DISPONIBLE;
	}

	// Libro leido desde el archivo: ya trae su estado
	public libro(String codigo, String nombre, String estado) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean estaPrestado() {
		return PRESTADO.equals(estado);
	}
}
