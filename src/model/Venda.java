package model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
	private Integer id;
	private LocalDate data;
	private float total;
	private Usuario usuario;
	private List<ItemVenda> listaIv;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaIv;
	}

	public void setListaItemVenda(List<ItemVenda> listaIv) {
		this.listaIv = listaIv;
	}
}
