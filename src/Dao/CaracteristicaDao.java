package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Application.Util;
import model.Produto;

public class CaracteristicaDao {
						
	
	public static void adicionar(Produto p) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO caracteristica ");
		sql.append(" (idcara,categoria,origem) ");
		sql.append(" VALUES");
		sql.append(" (?, ?, ?) ");
		
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setInt(1, p.getCaracteristica().getId());
			stat.setString(2, p.getCaracteristica().getCategoria().getLabel());
			stat.setString(3, p.getCaracteristica().getOrigem().getLabel());
			stat.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}
	
	
	public static void remover(Produto p) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("DELETE FROM caracteristica ");
		sql.append(" WHERE idcara = ");
		sql.append(p.getId());
		
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			stat.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
public static void alterar(Produto p){
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE caracteristica SET ");
		sql.append(" origem = ?, ");
		sql.append(" categoria = ? ");
		sql.append("WHERE ");
		sql.append(" idcara  = ? ");
		
	
		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setString(1, p.getCaracteristica().getOrigem().getLabel());
			stat.setString(2, p.getCaracteristica().getCategoria().getLabel());
			stat.setInt(3, p.getId());
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
