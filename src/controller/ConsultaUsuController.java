package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.Flash;
import javax.inject.Named;

import Application.FlashEasy;
import Dao.UsuarioDao;
import model.Usuario;

@RequestScoped
@Named
public class ConsultaUsuController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6803288668002279947L;
	private List<Usuario> listausu;
	private String filtro;


	public void remover(Usuario u) {
		UsuarioDao.remover(u);
		
	}
	
	
	 public String editar(Usuario u) {
		 	Usuario usu = new Usuario();
		 	usu = UsuarioDao.obterUm(u.getId());
	    	Flash flash = FlashEasy.getInstance();	
	    	flash.put("usuTemp",usu);
	    	
	    	return "usuario.xhtml?faces-redirect=true";
	 }
	 
	 public void pesquisar() {
			
			List<Usuario> listaFiltro = new ArrayList<Usuario>();
			
			for(int i=0; i < getListausu().size();i++) {
				if(listausu.get(i).getNome().toLowerCase().contains(filtro.toLowerCase())) {
					
					listaFiltro.add(listausu.get(i));
				}
				
				
			}
			listausu = listaFiltro;
		}
		
		public void voltarLista() {
			listausu = getListausu();
			filtro = null;
			
		}


	public List<Usuario> getListausu() {
		if(listausu == null) {
			listausu = UsuarioDao.obtertodos();
		}
		return listausu;
	}


	public void setListausu(List<Usuario> listausu) {
		this.listausu = listausu;
	}


	public String getFiltro() {
		return filtro;
	}


	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	 
	 
	 
	 
	 
	 
	
}
