package br.unitins.farmacia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.farmacia.model.Remedio;
import br.unitins.farmacia.model.Tipo;

public class RemedioDAO implements DAO<Remedio> {

	@Override
	public boolean insert(Remedio obj) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO remedio ( ");
		sql.append("  nome, ");
		sql.append(" tipo, ");
		sql.append("  valor ");
		sql.append(") VALUES ( ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ? ");
		sql.append(") ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());	
			if (obj.getTipo() == null)
				stat.setObject(2, null);
			else
				stat.setInt(2, obj.getTipo().getId());

			stat.setDouble( 3, obj.getValor());
			
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
	public boolean update(Remedio obj) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}
		
		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE remedio SET  ");
		sql.append("  nome = ?, ");
		sql.append("  tipo = ?, ");
		sql.append("  valor= ? ");
		sql.append("WHERE ");
		sql.append("  id = ?  ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			if (obj.getTipo() == null)
				stat.setObject(2, null);
			else
				stat.setInt(2, obj.getTipo().getId());
			stat.setDouble(3, obj.getValor());

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
		sql.append("DELETE FROM remedio");
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
	public List<Remedio> getAll() {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Remedio> lista = new ArrayList<Remedio>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  id, ");
		sql.append("  nome, ");
		sql.append("  tipo_remedio, ");
		sql.append("  valor ");
		sql.append("FROM ");
		sql.append("  remedio ");
		
		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Remedio  farmacia = new Remedio();
				farmacia.setId(rs.getInt("id"));
				farmacia.setNome(rs.getString("nome"));
				farmacia.setTipo(Tipo.valueOf(rs.getInt("tipo")));
				farmacia.setValor(rs.getDouble("valor"));

				lista.add(farmacia);
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

	public Remedio getById(int id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Remedio farmacia = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  id, ");
		sql.append("  nome, ");
		sql.append("  tipo, ");
		sql.append("  valor ");
		sql.append("FROM ");
		sql.append("  remedio ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			rs = stat.executeQuery();
			if (rs.next()) {
				farmacia = new Remedio();
				farmacia.setId(rs.getInt("id"));
				farmacia.setNome(rs.getString("nome"));
				farmacia.setTipo(Tipo.valueOf(rs.getInt("tipo")));
				farmacia.setValor(rs.getDouble("valor"));
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
		return farmacia;
	}

	public List<Remedio> getByNome(String nome) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Remedio> lista = new ArrayList<Remedio>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  id, ");
		sql.append("  nome, ");
		sql.append("  tipo, ");
		sql.append("  valor ");
		sql.append("FROM ");
		sql.append("  remedio ");
		sql.append("WHERE ");
		sql.append(" nome iLIKE ? ");
		sql.append("ORDER BY ");
		sql.append("  nome ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + nome + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				Remedio farmacia = new Remedio();
				farmacia.setId(rs.getInt("id"));
				farmacia.setNome(rs.getString("nome"));
				farmacia.setTipo(Tipo.valueOf(rs.getInt("tipo")));
				farmacia.setValor(rs.getDouble("valor"));

				lista.add(farmacia);
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
