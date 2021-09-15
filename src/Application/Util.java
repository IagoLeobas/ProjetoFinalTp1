package Application;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;


public class Util {
	
	public static String hash(String valor) {
		return DigestUtils.sha256Hex(valor);
		
	}
	
	public static void addErrorMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_ERROR, null);
	}
	
	public static void addErrorMessage(String msg, String clientId) {
		addMessage(msg, FacesMessage.SEVERITY_ERROR, clientId);
	}
	
	public static void addInfoMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_INFO, null);
	}
	
	public static void addWarnMessage(String msg) {
		addMessage(msg, FacesMessage.SEVERITY_WARN, null);
	}

	private static void addMessage(String msg, Severity severity, String clientId) {
		FacesContext.getCurrentInstance().
		addMessage(clientId, 
				new FacesMessage(severity, msg, null));
	}
	
	/*
	 * esse redirect é usado para redirecionar para determinada página
	 */
	public static void redirect(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
}