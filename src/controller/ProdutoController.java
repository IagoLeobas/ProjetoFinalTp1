package controller;
import java.io.Serializable;

import java.util.List;

import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Application.FlashEasy;
import Dao.ProdutoDao;
import model.Origem;
import model.Produto;
;

@Named
@ViewScoped

public class ProdutoController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7998095658194884469L;
	private Produto prod = null;
	private List<Produto> listaProd = null;
    private Origem origem[];
    private boolean ativador = false;
  
 
   public ProdutoController() {
	   Flash flash = FlashEasy.getInstance();
		flash.keep("produtoTemp");
		setProd((Produto)flash.get("produtoTemp"));
		if(prod != null) {
			ativador = true;
		}
	
	} 
   
   

   public String voltar() {
	   limpar();
	   return "consultaprod.xhtml?faces-redirect=true";
   }

public void adicionar() {
	   ProdutoDao.adicionar(getProd());
	   atualizador();
	   limpar();
   }
    
  
   public void alterar() {
	   ProdutoDao.alterar(getProd());
	   atualizador();
	   limpar();
   }
    
   public void limpar() {
	   
	   setProd(null);
	   
   }
    
    
   public void atualizador(){
	   
	   listaProd = ProdutoDao.obterTodos();
	
   }
    
    
    

    public Origem[] getOrigem() {
		if(origem == null) {
			origem = Origem.values();
		}
		return origem;
	}
    public Produto getProd() {
    	if(prod == null) {
			prod = new Produto(); 
		}
		return prod;
	}
	public void setProd(Produto prod) {
		this.prod = prod;
	}
	public List<Produto> getListaProd() {
		if(listaProd == null) {
			listaProd = ProdutoDao.obterTodos();
		}
		
		return listaProd;
	}

	public void setListaProd(List<Produto> listaProd) {
		this.listaProd = listaProd;
	}
	public void setOrigem(Origem[] origem) {
		this.origem = origem;
	}



	public boolean isAtivador() {
		return ativador;
	}

	public void setAtivador(boolean ativador) {
		this.ativador = ativador;
	}


    
    
    
    
	
	
}
