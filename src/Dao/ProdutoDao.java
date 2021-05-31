package Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Application.Util;
import model.Origem;
import model.Produto;

public class ProdutoDao implements Dao{
	
	public static void adicionar(Produto p) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO produto ");
		sql.append(" (produto,marca,descricao,origem) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?) ");
		
		
		PreparedStatement stat = null;
		try {
			
			
			stat = con.prepareStatement(sql.toString());
			stat.setString(1, p.getProduto());
			stat.setString(2, p.getMarca());
			stat.setString(3, p.getDescricao());
			stat.setString(4, p.getOrigem().getLabel());
			stat.execute();
			
			Util.addErrorMessage("Produto inserido com sucesso!");
		} catch (SQLException e) {
			Util.addErrorMessage("Problema ao inserir o produto.");
			e.printStackTrace();
		}
		finally {
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
		
	}
	
	public static void remover(Produto p) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM produto");
		sql.append(" WHERE idproduto = ");
		sql.append(p.getId());
		
		
		 ResultSet rs = null;
			try {
				PreparedStatement stat = con.prepareStatement(sql.toString());
				stat.execute();
				Util.addErrorMessage("Produto removido com sucesso!");
			} catch (SQLException e) {
				Util.addErrorMessage("Problema ao remover o produto.");
				e.printStackTrace();
			}
			finally {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
	 			}
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
			
			
		}
	
		
		
	public static List<Produto> obterTodos() {
		Connection conn = Dao.getConnection();
		
		List<Produto> listaproduto = new ArrayList<Produto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append(" FROM ");
		sql.append(" produto, ");
		
		
		
		PreparedStatement stat = null;
	
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
		
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("idproduto"));
				produto.setProduto(rs.getString("produto"));
				produto.setMarca(rs.getString("marca"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setOrigem(achaOrigem(rs.getString("origem")));
				
				listaproduto.add(produto);
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
			listaproduto = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (listaproduto == null || listaproduto.isEmpty())
			return null;
		
		return listaproduto;
	}

	
	public static Origem achaOrigem(String ori) {
		if(ori.equals(Origem.IMPORTADO.getLabel()))
			return Origem.IMPORTADO;
		else if(ori.equals(Origem.NACIONAL.getLabel()))
			return Origem.NACIONAL;
		
		
		return null;
	}
	
	
	
	public static void alterar(Produto p){
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produto SET ");
		sql.append(" produto = ?, ");
		sql.append(" marca = ?, ");
		sql.append(" descricao = ?, ");
		sql.append(" origem = ? ");
		sql.append("WHERE ");
		sql.append(" idproduto = ? ");
		
	
		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setString(1, p.getProduto());
			stat.setString(2, p.getMarca());
			stat.setString(3, p.getDescricao());
			stat.setString(4, p.getOrigem().getLabel());
			stat.setInt(5, p.getId());
			stat.execute();

			Util.addErrorMessage("Produto alterado com sucesso!");
		} catch (Exception e) {
			Util.addErrorMessage("Problema ao alterar o produto.");
			e.printStackTrace();
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
		
		
		
		
	}
	
	
	

	public static Produto obterUm(int id) {
		
		Connection con = Dao.getConnection();
		
		Produto p = null;
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ");
		sql.append("  produto.idproduto, ");
		sql.append("  produto.produto, ");
		sql.append("  produto.marca, ");
		sql.append("  produto.descricao, ");
		sql.append("  produto.origem, ");
		sql.append("FROM ");
		sql.append("  produto, ");
		sql.append("WHERE ");
		sql.append("  produto.idproduto = ? ");

	
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setInt(1,id);
			
			ResultSet rs = stat.executeQuery();
			
		    if(rs.next()) {
		    	p = new Produto();
				p.setId(rs.getInt("idproduto"));
				p.setProduto(rs.getString("produto"));
				p.setMarca(rs.getString("marca"));
				p.setDescricao(rs.getString("descricao"));
				p.setOrigem(achaOrigem(rs.getString("origem")));
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		return p;
	}
	

		
		
}
	
	
	
	
	
	
	
	

