package model;

public enum TipoU {

	ADM(1, "Administrador"), FUNCIONARIO(2, "Funcionario"), CLIENTE(3, "Cliente");

	private int id;
	private String label;

	private TipoU(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}
}
