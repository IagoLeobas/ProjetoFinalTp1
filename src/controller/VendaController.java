package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Application.Session;
import Application.Util;
import Dao.ProdutoDao;
import model.ItemVenda;
import model.Produto;


@Named
@ViewScoped
public class VendaController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898794228698382029L;
	private int tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;
	
	public void pesquisar() {
		
		if (getTipoFiltro() == 1) {
			
			setListaProduto(ProdutoDao.obterPeloNome(filtro));
		}
		else if(getTipoFiltro() == 2) {
			
			setListaProduto(ProdutoDao.obterPelaCategoria(filtro));
		}
		else {
			setListaProduto(ProdutoDao.obterTodos());
		}
			
	}
	
	public void addCarrinho(Produto produto) {

		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		if (carrinho == null) 
			carrinho = new ArrayList<ItemVenda>();
		
		
		ItemVenda iv = new ItemVenda();
		iv.setProduto(produto);
		iv.setQuantidade(1);
		iv.setValorUnitario((double) produto.getPreco());
	
		if (carrinho.contains(iv)) {
			int index = carrinho.indexOf(iv);
			int quantidade = carrinho.get(index).getQuantidade();
			carrinho.get(index).setQuantidade(++ quantidade );
			
		} else {
			carrinho.add(iv);
		}
		
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addInfoMessage("Item adicionado no carrinho.");
	
	}
			
	

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
		if(listaProduto == null) {
			listaProduto = new ArrayList<Produto>();
		}
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

}
