package model;

public enum Origem {
	
	IMPORTADO(1,"Importado"),
	NACIONAL(2,"Nacional");
	
	private int id;
	private String label;
	
	private Origem(int id, String label) {
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
