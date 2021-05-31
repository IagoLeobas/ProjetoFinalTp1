package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Application.Util;
import model.TipoU;
import model.Usuario;

public class UsuarioDao implements Dao {
	
	public static Usuario validarLogin(Usuario usuario) {
		Connection con = Dao.getConnection();
		
		Usuario usuarioLogado = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.idusuario, ");
		sql.append("  u.cpf, ");
		sql.append("  u.nome, ");
		sql.append("  u.email, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.tipo ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.login = ? ");
		sql.append("  AND u.senha = ? ");
		sql.append("  AND u.tipo = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = con.prepareStatement(sql.toString());
			stat.setString(1, usuario.getLogin());
			stat.setString(2, Util.hash(usuario.getSenha()));
			stat.setInt(3, usuario.getTipo().getId());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				usuarioLogado = new Usuario();
				usuarioLogado.setId(rs.getInt("idusuario"));
				usuarioLogado.setCpf(rs.getString("cpf"));
				usuarioLogado.setNome(rs.getString("nome"));
				usuarioLogado.setLogin(rs.getString("login"));
				usuarioLogado.setSenha(rs.getString("senha"));
				usuarioLogado.setEmail(rs.getString("email"));
				usuarioLogado.setTipo(achaTipo(rs.getInt("tipo")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			usuarioLogado = null;
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
		
		return usuarioLogado;		
	}
	
	public static void adicionar(Usuario usu) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO usuario ");
		sql.append(" (nome,login,senha,cpf,tipo,email) ");
		sql.append("VALUES (? ,? ,? ,? ,? ,? )");
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			rs = stat.getGeneratedKeys();
			
			stat = con.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			stat.setString(1,usu.getNome());
			stat.setString(2,usu.getLogin());
			stat.setString(3,Util.hash(usu.getSenha()));
			stat.setString(4,usu.getCpf());
			stat.setInt(5,usu.getTipo().getId());
			stat.setString(6,usu.getEmail());
			stat.execute(); 
			
			while(rs.next()) {
				usu.setId(rs.getInt("id"));
			}
			Util.addErrorMessage("Usuário inserido com sucesso!");
		} catch (SQLException e) {
			Util.addErrorMessage("Problema ao inserir usuário.");
			e.printStackTrace();
		}
		
		
	}	
		
	
	public static void remover(Usuario usu) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("DELETE FROM usuario ");
		sql.append("WHERE idusuario = ");
		sql.append(usu.getId());
		
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			stat.execute();
			Util.addErrorMessage("Usuário removido com sucesso!");
		} catch (SQLException e) {
			Util.addErrorMessage("Problema ao remover usuário.");
			e.printStackTrace();
		}

		
		
		
	}
	
	public static void alterar(Usuario usu) {
		
		Connection con = Dao.getConnection();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE usuario SET");
		sql.append(" nome = ?, ");
		sql.append(" login = ?, ");
		sql.append(" senha = ?, ");
		sql.append(" cpf = ?, ");
		sql.append(" tipo = ?, ");
		sql.append(" email = ? ");
		sql.append("WHERE idusuario = ");
		sql.append(usu.getId());
		
		PreparedStatement stat = null;
		
		try {
			stat = con.prepareStatement(sql.toString());
			
			stat.setString(1, usu.getNome());
			stat.setString(2, usu.getLogin());
			stat.setString(3, Util.hash(usu.getSenha()));
			stat.setString(4, usu.getCpf());
			stat.setInt(5, usu.getTipo().getId());
			stat.setString(6, usu.getEmail());
			stat.execute();
			
			Util.addErrorMessage("Usuário Alterado com sucesso!");
			
		} catch (SQLException e) {
			Util.addErrorMessage("Problema ao alterar usuário.");
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	public static List<Usuario> obtertodos(){
			
			Connection con = Dao.getConnection();
			List<Usuario> usus = new ArrayList<Usuario>();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT ");
			sql.append("usuario.idusuario, ");
			sql.append("usuario.nome, ");
			sql.append("usuario.login, ");
			sql.append("usuario.cpf, ");
			sql.append("usuario.senha, ");
			sql.append("usuario.tipo ");
			sql.append("usuario.email ");
			sql.append("FROM ");
			sql.append("usuario ");
			
			try {
				
				PreparedStatement stat = con.prepareStatement(sql.toString());
				ResultSet rs = stat.executeQuery();
				while(rs.next()) {
					
					
					Usuario usu = new Usuario();
					
					usu.setId(rs.getInt("idusuario"));
					usu.setNome(rs.getString("nome"));
					usu.setCpf(rs.getString("cpf"));
					usu.setLogin(rs.getString("login"));
					usu.setSenha(rs.getString("senha"));
					usu.setTipo(achaTipo(rs.getInt("tipo")));
					usu.setEmail(rs.getString("email"));
					
					usus.add(usu);
					
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return usus;
	
		
	}
		
		
	public static Usuario obterUm(int id) {
		
		Connection con = Dao.getConnection();
		
		Usuario usu = null;
		
		StringBuffer sql = new StringBuffer(); 
			
			sql.append("SELECT ");
			sql.append(" u.idusuario, ");
			sql.append(" u.nome, ");
			sql.append(" u.login, ");
			sql.append(" u.senha, ");
			sql.append(" u.cpf, ");
			sql.append(" u.tipo, ");
			sql.append(" u.email ");
			sql.append(" FROM ");
			sql.append(" usuario u");
			sql.append(" WHERE ");
			sql.append(" idusuario = ? ");
			
			PreparedStatement stat = null;
			ResultSet rs = null;
			
			try {
				stat = con.prepareStatement(sql.toString());
				stat.setInt(1,id);
				rs = stat.executeQuery();
				
				
				if(rs.next()) {
					usu = new Usuario();
					usu.setId(rs.getInt("idusuario"));
					usu.setNome(rs.getString("nome"));
					usu.setLogin(rs.getString("login"));
					usu.setSenha(rs.getString("senha"));
					usu.setCpf(rs.getString("cpf"));
					usu.setTipo(achaTipo(rs.getInt("tipo")));
					usu.setEmail(rs.getString("email"));
				}
				
			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
				return usu;
				
	}
			
			
	
	public static TipoU achaTipo(int tipo) {
		
		if(tipo == TipoU.ADM.getId())
			return TipoU.ADM;
		else if(tipo == TipoU.FUNCIONARIO.getId())
			return TipoU.FUNCIONARIO;
		return null;	

			
	}
		
	
	
	
	
	
	
}
