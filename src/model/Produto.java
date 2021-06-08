package model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Produto {
	
		@NotEmpty(message="Campo nome não pode estar vazio!")
		private String produto;
		@NotEmpty(message="Campo descrição não pode estar vazio!")
	    private String descricao;
		@NotEmpty(message="Campo marca não pode estar vazio!")
	    private String marca;
		private Categoria categoria;
	    private Origem origem;
	    private float preco;
	    private int id;
	 
	    
	    public Produto() {
	    	
	    }
	    
		
		
		public Produto(String produto, String descricao, String marca, Origem origem, int id,Categoria categoria,float preco) {
			super();
			this.produto = produto;
			this.descricao = descricao;
			this.marca = marca;
			this.origem = origem;
			this.id = id;
			this.categoria = categoria;
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
		public Origem getOrigem() {
			return origem;
		}
		public void setOrigem(Origem origem) {
			this.origem = origem;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}



		public Categoria getCategoria() {
			return categoria;
		}



		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}



		public float getPreco() {
			return preco;
		}



		public void setPreco(float preco) {
			this.preco = preco;
		}
	    
	    
	    
	
	
	
}
