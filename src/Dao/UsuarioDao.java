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
		sql.append("  u.idusu, ");
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

			if (rs.next()) {
				usuarioLogado = new Usuario();
				usuarioLogado.setId(rs.getInt("idusu"));
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

	public static boolean adicionar(Usuario usu) {

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

			stat = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, usu.getNome());
			stat.setString(2, usu.getLogin());
			stat.setString(3, Util.hash(usu.getSenha()));
			stat.setString(4, usu.getCpf());
			stat.setInt(5, usu.getTipo().getId());
			stat.setString(6, usu.getEmail());
			stat.execute();

			while (rs.next()) {
				usu.setId(rs.getInt("id"));
			}
			Util.addErrorMessage("Usu�rio inserido com sucesso!");
			return true;
		} catch (SQLException e) {

			Util.addErrorMessage("Problema ao inserir usu�rio.");
			e.printStackTrace();
			return false;
		}

	}

	public static void remover(Usuario usu) {

		Connection con = Dao.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM usuario ");
		sql.append("WHERE idusu = ");
		sql.append(usu.getId());

		PreparedStatement stat = null;

		try {
			stat = con.prepareStatement(sql.toString());
			stat.execute();
			Util.addErrorMessage("Usu�rio removido com sucesso!");
		} catch (SQLException e) {
			Util.addErrorMessage("Problema ao remover usu�rio.");
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
		sql.append("WHERE idusu = ");
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

			Util.addErrorMessage("Usu�rio Alterado com sucesso!");

		} catch (SQLException e) {
			Util.addErrorMessage("Problema ao alterar usu�rio.");
			e.printStackTrace();
		}
	}

	public static List<Usuario> obtertodos() {

		Connection con = Dao.getConnection();
		List<Usuario> usus = new ArrayList<Usuario>();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT ");
		sql.append(" idusu, ");
		sql.append(" nome, ");
		sql.append(" login, ");
		sql.append(" cpf, ");
		sql.append(" senha, ");
		sql.append(" tipo, ");
		sql.append(" email ");
		sql.append(" FROM ");
		sql.append(" usuario ");

		try {

			PreparedStatement stat = con.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {

				Usuario usu = new Usuario();

				usu.setId(rs.getInt("idusu"));
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
		sql.append(" idusu, ");
		sql.append(" nome, ");
		sql.append(" login, ");
		sql.append(" senha, ");
		sql.append(" cpf, ");
		sql.append(" tipo, ");
		sql.append(" email ");
		sql.append(" FROM ");
		sql.append(" usuario ");
		sql.append("WHERE ");
		sql.append(" idusu = ? ");

		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			stat = con.prepareStatement(sql.toString());
			stat.setInt(1, id);
			rs = stat.executeQuery();

			if (rs.next()) {
				usu = new Usuario();
				usu.setId(rs.getInt("idusu"));
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

		if (tipo == TipoU.ADM.getId())
			return TipoU.ADM;
		else if (tipo == TipoU.FUNCIONARIO.getId())
			return TipoU.FUNCIONARIO;
		else if (tipo == TipoU.CLIENTE.getId())
			return TipoU.CLIENTE;
		return null;

	}
}
