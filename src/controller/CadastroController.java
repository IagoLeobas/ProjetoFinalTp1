package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import Dao.UsuarioDao;
import model.TipoU;
import model.Usuario;

@Named
@RequestScoped
public class CadastroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8475305238982335867L;
	private Usuario usu;
	
	

	public String adicionar() {
		usu.setTipo(TipoU.CLIENTE);
		if(UsuarioDao.adicionar(getUsu()) == false) {
			return null;
		}
		limpar();
		return "login.xhtml?faces-redirect=true";
	}
	
	public void limpar() {
		
		setUsu(null);
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

	

}
