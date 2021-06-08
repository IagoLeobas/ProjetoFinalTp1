package Application;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Session {
	
private static Session session = null;
	
	private Session() {	
		
	}
	
	public static Session getInstance() {
		if (session == null)
			session = new Session();
		return session;
	}
	
	private ExternalContext getExternalContext() {
		if (FacesContext.getCurrentInstance() == null)
			throw new RuntimeException("Sua aplicação web não está rodando!");
		
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void set(String chave, Object obj) {
		getExternalContext().getSessionMap().put(chave, obj);
	}
	
	public Object get(String chave) {
		return getExternalContext().getSessionMap().get(chave);
	}
	
	public void invalidateSession() {
		getExternalContext().invalidateSession();
	}
}
