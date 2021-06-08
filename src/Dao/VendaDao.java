package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ItemVenda;
import model.Origem;
import model.Produto;
import model.Categoria;
import model.Usuario;
import model.Venda;

public class VendaDao implements Dao {

	public boolean inserir(Venda obj) {
		Connection con = Dao.getConnection();
		boolean deuErro = false;
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO venda ");
		sql.append(" (data, idusuario) "); 
		sql.append("VALUES ");
		sql.append(" (?, ?) ");
		
		

		PreparedStatement stat = null;
		try {
			
			stat = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stat.setInt(2, obj.getUsuario().getId());
			

			stat.execute();
			
			
			

			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				obj.setId(rs.getInt("idvenda"));
				
			}
			
				
			
			
			
			salvarItens(obj, con);
			
			
			

			// salvando por definitivo no banco
			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			deuErro = true;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (deuErro)
			return false;

		return true;
	}

	private void salvarItens(Venda venda, Connection conn) throws SQLException {
		// salvando os itens de venda
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO itemvenda ");
		sql.append(" (quantidade, valorunit, iditem, idvenda) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?) ");

		PreparedStatement stat = null;
		for (ItemVenda item : venda.getListaItemVenda()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, item.getQuantidade());
			stat.setDouble(2, item.getValorUnitario());
			stat.setInt(3, item.getProduto().getId());
			stat.setInt(4, venda.getId());

			stat.execute();

			// o janio faria com o dao de produto
			// atualizandoEstoque(item.getProduto(), item.getQuantidade(), conn);
		}

	}

	public boolean alterar(Venda obj) {
		return false;
	}

	public List<Venda> obterTodos(Usuario usuario) {
		Connection con = Dao.getConnection();

		List<Venda> listaVenda = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  v.idusuario ");
		sql.append("FROM ");
		sql.append("  venda v ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = ? ");

		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(usuario);
				venda.setListaItemVenda(obterItensVenda(venda));

				listaVenda.add(venda);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaVenda = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (listaVenda == null || listaVenda.isEmpty())
			return null;

		return listaVenda;
	}

	private List<ItemVenda> obterItensVenda(Venda venda) {
		Connection con = Dao.getConnection();

		List<ItemVenda> listaItem = new ArrayList<ItemVenda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  i.iditem, ");
		sql.append("  i.idvenda, ");
		sql.append("  i.quantidade, ");
		sql.append("  i.valorunit, ");
		sql.append("  p.produto, ");
		sql.append("  p.descricao, ");
		sql.append("  p.categoria, ");
		sql.append("  p.preco, ");
		sql.append("  p.marca, ");
		sql.append("  p.origem ");
		sql.append("FROM ");
		sql.append("  itemvenda i, ");
		sql.append("  produto p ");
		sql.append("WHERE ");
		sql.append("  i.idprod = p.id ");
		sql.append("  AND i.idvenda = ? ");

		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setInt(1, venda.getId());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setQuantidade(rs.getInt("quantidade"));
				item.setValorUnitario(rs.getDouble("valor_unitario"));
				item.setProduto(new Produto());
				item.getProduto().setId(rs.getInt("id_produto"));
				item.getProduto().setProduto(rs.getString("produto"));
				item.getProduto().setDescricao(rs.getString("descricao"));
				item.getProduto().setCategoria(achaCategoria(rs.getInt("categoria")));
				item.getProduto().setPreco(rs.getFloat("preco"));
				item.getProduto().setMarca(rs.getString("marca"));
				item.getProduto().setOrigem(achaOrigem(rs.getInt("origem")));

				listaItem.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaItem = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (listaItem == null || listaItem.isEmpty())
			return null;

		return listaItem;
	}

	public static Categoria achaCategoria(int categoria) {

		if (categoria == Categoria.MEMORIA.getId())
			return Categoria.MEMORIA;
		else if (categoria == Categoria.GABINETE.getId())
			return Categoria.GABINETE;
		else if (categoria == Categoria.CPU.getId())
			return Categoria.CPU;
		else if (categoria == Categoria.GPU.getId())
			return Categoria.GPU;
		else if (categoria == Categoria.PLACA_MAE.getId())
			return Categoria.PLACA_MAE;
		else if (categoria == Categoria.SOM.getId())
			return Categoria.SOM;
		else if (categoria == Categoria.MONITOR.getId())
			return Categoria.MONITOR;
		else if (categoria == Categoria.PERIFERICOS.getId())
			return Categoria.PERIFERICOS;
		return null;
	}

	public static Origem achaOrigem(int origem) {

		if (origem == Origem.IMPORTADO.getId())
			return Origem.IMPORTADO;
		else if (origem == Origem.NACIONAL.getId())
			return Origem.NACIONAL;
		return null;
	}
	
	
	public float somaVal(Connection con,int id) {
		
		float val = 0;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT valorunit ");
		sql.append(" FROM ");
		sql.append(" itemvenda ");
		sql.append(" WHERE idvenda = ");
		sql.append(id);
		
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				
				val += rs.getFloat("valorunit");
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(val);
		return val;
		
		
		
	}
	
	public void insereVal(int id) {
		
		Connection con = Dao.getConnection();		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" UPDATE venda ");
		sql.append(" SET total = ? ");
		sql.append(" WHERE idvenda = ");
		sql.append(id);
		
		
		PreparedStatement stat = null;
		
		
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setFloat(1,somaVal(con,id));

			stat.execute();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
