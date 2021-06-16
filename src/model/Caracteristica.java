package model;

public class Caracteristica {
	
	private int id;
	private Origem origem;
	private Categoria categoria;
	
	public Caracteristica() {
		
	
	}
	
	
	public Caracteristica(Origem origem, Categoria categoria) {
		super();
		this.origem = origem;
		this.categoria = categoria;
	}



	public Origem getOrigem() {
		return origem;
	}
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Caracteristica [id=" + id + ", origem=" + origem + ", categoria=" + categoria + "]";
	}
	
	
	
	
	
	
	
	
}
