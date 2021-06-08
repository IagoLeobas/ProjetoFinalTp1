package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import Application.Session;
import Application.Util;
import Dao.UsuarioDao;
import model.TipoU;
import model.Usuario;



@Named
@RequestScoped
public class LoginController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4933925009488178406L;
	private Usuario usu;
	private TipoU tipo[];
	List<Usuario> listaU;
	
	
	public String logar() {

			Usuario usuarioLogado = UsuarioDao.validarLogin(usu);
			if (usuarioLogado != null) {
				Session.getInstance().set("usuarioLogado", usuarioLogado);
				return "template.xhtml?faces-redirect=true";
				
			}
			
		Util.addErrorMessage("Senha, usuário ou tipo inválidos!"); 
		return null;
	}

	
	public TipoU[] getTipo() {
		if(tipo == null) {
			tipo = TipoU.values();
		}
		return tipo;
		
	}

	public Usuario getUsu() {
		if(usu == null) {
			usu = new Usuario();
		}
		return usu;
	}


	public void setUsu(Usuario usu) {
		this.usu = usu;
	}


	public List<Usuario> getListaU() {
		if(listaU == null) {
			listaU = new ArrayList<Usuario>();
		}
		return listaU;
	}


	public void setListaU(List<Usuario> listaU) {
		this.listaU = listaU;
	}



	
	
	
	
	
	
	
}
