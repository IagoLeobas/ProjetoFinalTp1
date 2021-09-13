package model;

import javax.validation.constraints.NotEmpty;

public class Produto {

	@NotEmpty(message = "Campo nome não pode estar vazio!")
	private String produto;
	@NotEmpty(message = "Campo descrição não pode estar vazio!")
	private String descricao;
	@NotEmpty(message = "Campo marca não pode estar vazio!")
	private String marca;
	private Caracteristica caracteristica;
	private float preco;
	private int id;

	public Produto() {

	}

	public Produto(String produto, String descricao, String marca, Origem origem, int id, Categoria categoria,
			float preco, Caracteristica caracteristica) {
		super();
		this.produto = produto;
		this.descricao = descricao;
		this.marca = marca;
		this.caracteristica = caracteristica;
		this.id = id;
		this.preco = preco;

	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Caracteristica getCaracteristica() {
		if (caracteristica == null) {
			caracteristica = new Caracteristica();
		}
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

}
