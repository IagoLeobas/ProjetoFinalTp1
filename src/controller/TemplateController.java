package controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Application.Session;
import model.TipoU;
import model.Usuario;

@ViewScoped
@Named
public class TemplateController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8882450473392981712L;
	private Usuario usuarioLogado;
	private Boolean limitador = Boolean.FALSE;

	public String encerrarSessao() {
		Session.getInstance().invalidateSession();
		return "/faces/login.xhtml?faces-redirect=true";
	}

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
			daPermissao(usuarioLogado);
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public void daPermissao(Usuario usu) {

		if (usu.getTipo() == TipoU.ADM) {
			setLimitador(Boolean.TRUE);
		} else {
			setLimitador(Boolean.FALSE);
		}
		
	}

	public boolean isLimitador() {
		return limitador;
	}

	public void setLimitador(boolean limitador) {
		this.limitador = limitador;
	}
}
