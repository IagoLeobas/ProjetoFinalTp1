package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Application.FlashEasy;
import Dao.UsuarioDao;
import model.TipoU;
import model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386708627966985754L;
	private Usuario usu;
	private List<Usuario> listaUsu;
	private TipoU[] tipo;
	private boolean ativador = false;

	public UsuarioController() {
		Flash flash = FlashEasy.getInstance();
		flash.keep("usuTemp");
		setUsu((Usuario) flash.get("usuTemp"));
		if (usu != null) {
			setAtivador(true);
		}

	}

	public String voltar() {
		limpar();
		return "/pages/consultausu.xhtml?faces-redirect=true";
	}

	public void adicionar() {
		UsuarioDao.adicionar(getUsu());
		limpar();
	}

	public void alterar() {
		UsuarioDao.alterar(getUsu());
		limpar();

	}

	public void limpar() {
		setUsu(null);
	}

	public TipoU[] getTipo() {
		if (tipo == null) {
			tipo = TipoU.values();
		}
		return tipo;

	}

	public Usuario getUsu() {
		if (usu == null) {
			usu = new Usuario();
		}
		return usu;
	}

	public void setUsu(Usuario usu) {
		this.usu = usu;
	}

	public List<Usuario> getListaUsu() {
		if (listaUsu == null) {
			listaUsu = UsuarioDao.obtertodos();
		}
		return listaUsu;
	}

	public void setListaUsu(List<Usuario> listaUsu) {
		this.listaUsu = listaUsu;
	}

	public boolean isAtivador() {
		return ativador;
	}

	public void setAtivador(boolean ativador) {
		this.ativador = ativador;
	}
}
