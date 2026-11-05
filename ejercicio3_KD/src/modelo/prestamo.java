package modelo;

public class prestamo {

	private String nombreLibro;
	private String codigo;
	private String persona;

	public prestamo(String nombreLibro, String codigo, String persona) {
		this.nombreLibro = nombreLibro;
		this.codigo = codigo;
		this.persona = persona;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getPersona() {
		return persona;
	}
}
