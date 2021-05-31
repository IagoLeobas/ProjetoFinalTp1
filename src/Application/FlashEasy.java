package Application;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class FlashEasy {
	
	private static Flash flash;
	
	public static Flash getInstance() {
		if(flash == null) {
			
			flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		}
		
		return flash;
		
	}
	
	
}
