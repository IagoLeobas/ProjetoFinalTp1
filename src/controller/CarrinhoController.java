package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import Application.Session;
import Application.Util;
import Dao.VendaDao;
import model.ItemVenda;
import model.Produto;
import model.Usuario;
import model.Venda;

@Named
@RequestScoped

public class CarrinhoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1485837909393811286L;
	private Venda venda;

	public void finalizar() {
		Usuario usuario = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuario == null) {
			Util.addErrorMessage("Impossível realizar a venda, faça o login no sistema.");
			return;
		}

		if (getVenda().getListaItemVenda() == null || getVenda().getListaItemVenda().isEmpty()) {
			Util.addErrorMessage("Não existem produtos no carrinho.");
			return;
		}

		// adicionando o usuario logado na venda
		getVenda().setUsuario(usuario);

		// salvando no banco de dados
		VendaDao dao = new VendaDao();
		if (dao.inserir(getVenda())) {
			dao.insereVal(getVenda().getId());
			Util.addInfoMessage("Venda realizada com sucesso.");
			// limpando o carrinho
			Session.getInstance().set("carrinho", null);
			// limpar a venda
			setVenda(null);
		} else {
			Util.addErrorMessage("Problemas ao concluir a venda. Tente novamente mais tarde.");
		}

	}

	public float pegaTot(List<ItemVenda> lista) {

		float tot = 0;

		for (int i = 0; i < lista.size(); i++) {

			tot += lista.get(i).getValorUnitario();

		}

		return tot;
	}

	public void remover(ItemVenda iv) {

		int index = getVenda().getListaItemVenda().indexOf(iv);
		getVenda().getListaItemVenda().remove(index);

		Util.addErrorMessage("Produto removido com sucesso!");

	}

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();

		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");

		if (carrinho == null)
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		else
			venda.setListaItemVenda(carrinho);

		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
