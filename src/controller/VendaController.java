package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Dao.ProdutoDao;
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
