package br.unitins.farmacia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.farmacia.model.Perfil;
import br.unitins.farmacia.model.Usuario;

public class UsuarioDAO implements DAO<Usuario> {
	@Override
	public boolean insert(Usuario obj) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO usuario ( ");
		sql.append("  nome, ");
		sql.append("  login, ");
		sql.append("  senha, ");
		sql.append("  perfil, ");
		sql.append("  datanascimento ");
		sql.append(") VALUES ( ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?  ");
		sql.append(") ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getLogin());
			stat.setString(3, obj.getSenha());
			if (obj.getPerfil() == null)
				stat.setObject(4, null);
			else
				stat.setInt(4, obj.getPerfil().getId());
			
			if (obj.getDataNascimento() == null)
				stat.setObject(5, null);
			else
				stat.setDate(5, Date.valueOf(obj.getDataNascimento()));

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		}

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
		return resultado;
	}

	@Override
	public boolean update(Usuario obj) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE usuario SET  ");
		sql.append("  nome = ?, ");
		sql.append("  login = ?, ");
		sql.append("  senha = ?, ");
		sql.append("  perfil = ?, ");
		sql.append("  datanascimento = ? ");
		sql.append("WHERE ");
		sql.append("  id = ?  ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getLogin());
			stat.setString(3, obj.getSenha());
			if (obj.getPerfil() == null)
				stat.setObject(4, null);
			else
				stat.setInt(4, obj.getPerfil().getId());
			
			if (obj.getDataNascimento() == null)
				stat.setObject(5, null);
			else
				stat.setDate(5, Date.valueOf(obj.getDataNascimento()));
			
			stat.setInt(6, obj.getId());

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		}

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
		return resultado;
	}

	@Override
	public boolean delete(int id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM usuario ");
		sql.append("WHERE ");
		sql.append("  id = ?  ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		}

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
		return resultado;
	}

	@Override
	public List<Usuario> getAll() {

		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Usuario> lista = new ArrayList<Usuario>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("ORDER BY ");
		sql.append("  u.nome ");

		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				
				Date data = rs.getDate("datanascimento");
				if (data != null)
					usuario.setDataNascimento(data.toLocalDate());

				lista.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lista = null;
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public Usuario getById(int id) {

		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.id = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				Date data = rs.getDate("datanascimento");
				if (data != null)
					usuario.setDataNascimento(data.toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}

	public Usuario verificarLogin(String login, String senha) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.login = ? ");
		sql.append(" AND u.senha = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, login);
			stat.setString(2, senha);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				Date data = rs.getDate("datanascimento");
				if (data != null)
					usuario.setDataNascimento(data.toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}


	//public boolean login(Usuario obj) {
//		String sql = "select * from usuario where usuario.login = ? and usuario.senha = ?";
	//	
//		Usuario user = new Usuario();
//		boolean result = true;
//		Connection conn=null;
//		ResultSet rs = null;
//		PreparedStatement stat = null;
	//	
//		try {
//			conn = DAO.getConnection();
//			if(conn==null) {
//				result = false;
//			}
//			stat = conn.prepareStatement(sql);
//			stat.setString(1, obj.getLogin());
//			stat.setString(2, Util.hash(obj));
//			rs=stat.executeQuery();
//			
//			if(rs.next()) {
//				user.setLogin(rs.getString("email")); 
//				user.setSenha(rs.getString("senha"));
//			}else {
//				System.out.println("Sem dados");
//			}
//			obj.setSenha(Util.hash(obj));
//			if(obj.checkEmail(user)) {
//				Util.redirect("index.xhtml");
//				System.out.println("Login feito com sucesso");
//			}else {
//				System.out.println("Usuário não encontrado");
//				System.out.println(obj);
//				System.out.println(user);
//			}
//			
//		}catch(SQLException e) {
//			System.out.println("Erro ao fazer o login");
//			e.printStackTrace();
//		}finally {
//			try {
//				conn.close();
//			}catch(SQLException e) {}
//			try {
//				stat.close();
//			}catch(SQLException e) {}
//			try {
//				rs.close();
//			}catch(SQLException e) {}
	//
//		}
//		return result;
	//}

	public List<Usuario> getByNome(String nome){
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Usuario> lista = new ArrayList<Usuario>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append(" u.nome iLIKE ? ");
		sql.append("ORDER BY ");
		sql.append("  u.nome ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + nome + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				Date data = rs.getDate("datanascimento");
				if (data != null)
					usuario.setDataNascimento(data.toLocalDate());

				lista.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lista = null;
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<Usuario> getByLogin(String nome) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Usuario> lista = new ArrayList<Usuario>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.login, ");
		sql.append("  u.senha, ");
		sql.append("  u.perfil, ");
		sql.append("  u.datanascimento ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append(" u.login iLIKE ? ");
		sql.append("ORDER BY ");
		sql.append("  u.nome ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + nome + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				Date data = rs.getDate("datanascimento");
				if (data != null)
					usuario.setDataNascimento(data.toLocalDate());

				lista.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lista = null;
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	}
