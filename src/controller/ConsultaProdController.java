package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.Flash;
import javax.inject.Named;

import Application.FlashEasy;
import Dao.ProdutoDao;
import model.Produto;

@RequestScoped
@Named
public class ConsultaProdController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7750675864509277465L;
	private List<Produto> listaProd;
	private String filtro;
	

	public String editar(Produto p) {
		Produto produto = ProdutoDao.obterUm(p.getId());
		
		Flash flash = FlashEasy.getInstance();
		flash.keep("produtoTemp");
		flash.put("produtoTemp", produto);
		
		return "produto.xhtml?faces-redirect=true";
	   }
	
	public void pesquisar() {
		
		List<Produto> listaFiltro = new ArrayList<Produto>();
		
		for(int i=0; i < getListaProd().size();i++) {
			if(listaProd.get(i).getProduto().toLowerCase().contains(filtro.toLowerCase())) {
				
				listaFiltro.add(listaProd.get(i));
			}
			
			
		}
		listaProd = listaFiltro;
	}
	
	public void voltarLista() {
		listaProd = getListaProd();
		filtro = null;
		
	}
		
	 public void remover(Produto p) {
		   ProdutoDao.remover(p);
		   atualizador();
	   }
	   
	 public void atualizador(){
		   
		   listaProd = ProdutoDao.obterTodos();
		
	   }


	public List<Produto> getListaProd() {
		if(listaProd == null) {
			listaProd = ProdutoDao.obterTodos();
		}
		return listaProd;
	}


	public void setListaProd(List<Produto> listaProd) {
		this.listaProd = listaProd;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


	
	
	
}
