package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

//modelo de las tablas
public class libroTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnas = { "Código", "Nombre", "Estado" };
	private List<libro> libros = new ArrayList<libro>();

	public void setLibros(List<libro> libros) {
		this.libros = libros;
		fireTableDataChanged();
	}

	public libro getLibroEn(int fila) {
		return libros.get(fila);
	}

	@Override
	public int getRowCount() {
		return libros.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public String getColumnName(int columna) {
		return columnas[columna];
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		libro l = libros.get(fila);
		switch (columna) {
		case 0:
			return l.getCodigo();
		case 1:
			return l.getNombre();
		default:
			return l.getEstado();
		}
	}
}
