package model;

public enum Categoria {

	MEMORIA(1, "Memoria"), GABINETE(2, "Gabinete"), CPU(3, "processador"), GPU(4, "Placa de vídeo"),
	PLACA_MAE(5, "Placa mãe"), SOM(6, "Som"), MONITOR(7, "Monitor"), PERIFERICOS(8, "Periférico");

	private int id;
	private String Label;

	private Categoria(int id, String label) {
		this.id = id;
		Label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return Label;
	}
}
