package Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Application.Util;
import model.Caracteristica;
import model.Categoria;
import model.Origem;
import model.Produto;

public class ProdutoDao implements Dao{
	
	public static void adicionar(Produto p) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO produto ");
		sql.append(" (produto,marca,descricao,preco) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?) ");
		
		
		PreparedStatement stat = null;
		try {
			
			
			stat = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, p.getProduto());
			stat.setString(2, p.getMarca());
			stat.setString(3, p.getDescricao());
			stat.setFloat(4, p.getPreco());
			stat.execute();
		
			
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				p.getCaracteristica().setId(rs.getInt("idprod"));
				
			}
			
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
		sql.append(" WHERE idprod = ");
		sql.append(p.getId());
		
		
		 ResultSet rs = null;
			try {
				PreparedStatement stat = con.prepareStatement(sql.toString());
				stat.execute();
				CaracteristicaDao.remover(p);
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
		sql.append("SELECT ");
		sql.append("  p.idprod, ");
		sql.append("  p.produto, ");
		sql.append("  p.marca, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  c.idcara, ");
		sql.append("  c.origem, ");
		sql.append("  c.categoria ");
		sql.append("FROM ");
		sql.append("  produto p, ");
		sql.append("  caracteristica c ");
		sql.append("WHERE ");
		sql.append("  p.idprod = c.idcara ");
		
		
		
		
		PreparedStatement stat = null;
	
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
		
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("idprod"));
				produto.setProduto(rs.getString("produto"));
				produto.setMarca(rs.getString("marca"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getFloat("preco"));
				
				produto.setCaracteristica(new Caracteristica());
				produto.getCaracteristica().setId(rs.getInt("idcara"));
				produto.getCaracteristica().setCategoria(achaCategoria(rs.getString("categoria")));
				produto.getCaracteristica().setOrigem(achaOrigem(rs.getString("origem")));
				
				System.out.println(produto.getCaracteristica());
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
	
	public static Categoria achaCategoria(String cat) {
		if(cat.equals(Categoria.MEMORIA.getLabel()))
			return Categoria.MEMORIA;
		else if(cat.equals(Categoria.GABINETE.getLabel()))
			return Categoria.GABINETE;
		else if(cat.equals(Categoria.CPU.getLabel()))
			return Categoria.CPU;
		else if(cat.equals(Categoria.GPU.getLabel()))
			return Categoria.GPU;
		else if(cat.equals(Categoria.PLACA_MAE.getLabel()))
			return Categoria.PLACA_MAE;
		else if(cat.equals(Categoria.SOM.getLabel()))
			return Categoria.SOM;
		else if(cat.equals(Categoria.MONITOR.getLabel()))
			return Categoria.MONITOR;
		else if(cat.equals(Categoria.PERIFERICOS.getLabel()))
			return Categoria.PERIFERICOS;
		
		
		
		return null;
	}
	
	
	
	public static void alterar(Produto p){
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produto SET ");
		sql.append(" produto = ?, ");
		sql.append(" marca = ?, ");
		sql.append(" descricao = ?, ");
		sql.append(" preco = ? ");
		sql.append("WHERE ");
		sql.append(" idprod  = ? ");
		
	
		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setString(1, p.getProduto());
			stat.setString(2, p.getMarca());
			stat.setString(3, p.getDescricao());
			stat.setFloat(4, p.getPreco());
			stat.setInt(5, p.getId());
			stat.execute();
			
			CaracteristicaDao.alterar(p);

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
		sql.append("  p.idprod, ");
		sql.append("  p.produto, ");
		sql.append("  p.marca, ");
		sql.append("  p.descricao, ");
		sql.append("  c.origem, ");
		sql.append("  c.categoria, ");
		sql.append("  p.preco ");
		sql.append("FROM ");
		sql.append("  produto p, caracteristica c ");
		sql.append("WHERE ");
		sql.append("  idprod = ? ");
		sql.append(" AND ");
		sql.append("  idcara = ? ");

	
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setInt(1,id);
			stat.setInt(2,id);
			
			ResultSet rs = stat.executeQuery();
			
		    if(rs.next()) {
		    	p = new Produto();
		    	Caracteristica carac = new Caracteristica(achaOrigem(rs.getString("origem")),achaCategoria(rs.getString("categoria")));
				p.setId(rs.getInt("idprod"));
				p.setProduto(rs.getString("produto"));
				p.setMarca(rs.getString("marca"));
				p.setDescricao(rs.getString("descricao"));
				p.setCaracteristica(carac);
				p.setPreco(rs.getFloat("preco"));
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		return p;
	}
	
	
	public static List<Produto> obterPelaCategoria(String filtro) {
		
		Connection con = Dao.getConnection();
		List<Produto> listaprod = new ArrayList<Produto>();
		
	    StringBuffer sql = new StringBuffer();
	    
	    sql.append("SELECT ");
	    sql.append("  p.idprod, ");
		sql.append("  p.produto, ");
		sql.append("  p.marca, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  c.origem, ");
		sql.append("  c.categoria ");
		sql.append(" FROM ");
		sql.append(" produto p, ");
		sql.append(" caracteristica c ");
		sql.append("WHERE ");
		sql.append(" c.categoria = ");
		sql.append("'"+filtro+"'");
		sql.append(" AND ");
		sql.append(" p.idprod = c.idcara ");
		
		PreparedStatement stat = null;
		
		
		try {
		
			
			stat =  con.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("idprod"));
				p.setProduto(rs.getString("produto"));
				p.setMarca(rs.getString("marca"));
				p.setDescricao(rs.getString("descricao"));
				p.getCaracteristica().setOrigem(achaOrigem(rs.getString("origem")));
				p.getCaracteristica().setCategoria(achaCategoria(rs.getString("categoria")));
				p.setPreco(rs.getFloat("preco"));
				
				listaprod.add(p);
				
				
				
			}
			
			
			
		} catch (SQLException e) {
			Util.addErrorMessage("Produto não encontrados");
			e.printStackTrace();
			return null;
		}
		return listaprod;
		
		

		
	}
	
	
	public static List<Produto> obterPeloNome(String filtro){
		
		Connection con = Dao.getConnection();
		List<Produto> listaprod = new ArrayList<Produto>();
		
		StringBuffer sql = new StringBuffer();
		
		    sql.append("SELECT ");
		    sql.append("  p.idprod, ");
			sql.append("  p.produto, ");
			sql.append("  p.marca, ");
			sql.append("  p.descricao, ");
			sql.append("  p.preco, ");
			sql.append("  c.origem, ");
			sql.append("  c.categoria ");
			sql.append("FROM ");
			sql.append(" produto p,");
			sql.append(" caracteristica c ");
			sql.append("WHERE ");
			sql.append(" produto = ");
			sql.append("'"+filtro+"'");
			sql.append(" AND ");
			sql.append(" p.idprod = c.idcara ");
			
			PreparedStatement stat = null;
			
			
			try {
			
				
				stat =  con.prepareStatement(sql.toString());
				ResultSet rs = stat.executeQuery();
				
				while(rs.next()) {
					Produto p = new Produto();
					p.setId(rs.getInt("idprod"));
					p.setProduto(rs.getString("produto"));
					p.setMarca(rs.getString("marca"));
					p.setDescricao(rs.getString("descricao"));
					p.getCaracteristica().setOrigem(achaOrigem(rs.getString("origem")));
					p.getCaracteristica().setCategoria(achaCategoria(rs.getString("categoria")));
					p.setPreco(rs.getFloat("preco"));
					
					listaprod.add(p);
					
					
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return listaprod;
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

		
		
}
	
	
	
	
	
	
	
	

