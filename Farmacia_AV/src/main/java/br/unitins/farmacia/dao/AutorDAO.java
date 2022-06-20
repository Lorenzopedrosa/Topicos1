package br.unitins.farmacia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.farmacia.model.Autor;

public class AutorDAO implements DAO<Autor> {

	@Override
	public boolean insert(Autor obj) {
		return false;
	}

	@Override
	public boolean update(Autor obj) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<Autor> getAll() {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Autor> lista = new ArrayList<Autor>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  a.id, ");
		sql.append("  a.nome, ");
		sql.append("  a.data_nascimento ");
		sql.append("FROM ");
		sql.append("  Autor a ");
		sql.append("ORDER BY ");
		sql.append("  a.nome ");

		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
				autor.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
				
				lista.add(autor);
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
	
	public Autor getById(int id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}
		
		Autor autor = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  a.id, ");
		sql.append("  a.nome, ");
		sql.append("  a.data_nascimento ");
		sql.append("FROM ");
		sql.append("  Autor a ");
		sql.append("WHERE ");
		sql.append("  a.id = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
				autor.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
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
		return autor;
	}


}
