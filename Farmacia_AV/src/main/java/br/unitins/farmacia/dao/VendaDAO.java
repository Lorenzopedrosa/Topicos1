package br.unitins.farmacia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.farmacia.model.ItemVenda;
import br.unitins.farmacia.model.Usuario;
import br.unitins.farmacia.model.Venda;
import br.unitins.farmacia.dao.DAO;
import br.unitins.farmacia.dao.RemedioDAO;

public class VendaDAO implements DAO<Venda> {

	@Override
	public boolean insert(Venda obj) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}
		
		//transação de forma manual(agora é obrigado a executar um commit)
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO venda ( ");
		sql.append("  data_venda, ");
		sql.append("  id_usuario ");
		sql.append(") VALUES ( ");
		sql.append("  ?, ");
		sql.append("  ? ");
		sql.append(") ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			//só salva a data sem a hora
			//tentar salvar a hora
			stat.setDate(1, Date.valueOf(obj.getDataVenda().toLocalDate()));
			stat.setInt(2, obj.getUsuario().getId());
			stat.execute();
			
			//obtendo um id gerado pelo banco
			ResultSet rs = stat.getGeneratedKeys();
			if(rs.next()) {
				obj.setId(rs.getInt("id"));
			}
			
			salvarItensVenda(obj, conn);
			
			//salvando no banco
			conn.commit();
			

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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


	private void salvarItensVenda(Venda obj, Connection conn) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO itemvenda ( ");
		sql.append("  valor, ");
		sql.append("  quantidade, ");
		sql.append("  id_remedio, ");
		sql.append("  id_venda ");
		sql.append(") VALUES ( ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?  ");
		sql.append(") ");

		PreparedStatement stat = null;
		for (ItemVenda itemVenda : obj.getListaItemVenda()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setDouble(1, itemVenda.getValor());
			stat.setInt(2, itemVenda.getQuantidade());			
			stat.setInt(3, itemVenda.getFarmacia().getId());
			stat.setInt(4, obj.getId());
			stat.execute();
		}
	}

	@Override
	public boolean delete(int id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM produto ");
		sql.append("WHERE ");
		sql.append("  id_produto = ?  ");

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

	public List<Venda> getByUsuario(Usuario usuario) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Venda> lista = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data_venda ");
		sql.append("FROM ");
		sql.append("  venda v ");
		sql.append("WHERE ");
		sql.append(" v.id_usuario = ? ");
		sql.append("ORDER BY ");
		sql.append("  v.data_venda DESC ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setDataVenda(LocalDateTime.of(rs.getDate("data_venda").toLocalDate(), LocalTime.MIN));
				venda.setUsuario(usuario);
				
				lista.add(venda);
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
	

	public Venda getByVenda(Venda venda) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}
		
		venda.setListaItemVenda(new ArrayList<ItemVenda>());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  i.id, ");
		sql.append("  i.valor, ");
		sql.append("  i.quantidade, ");
		sql.append("  i.id_remedio ");
		sql.append("FROM ");
		sql.append("  itemvenda i ");
		sql.append("WHERE ");
		sql.append(" i.id_venda = ? ");
		
		ResultSet rs = null;
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, venda.getId());
			rs = stat.executeQuery();
			RemedioDAO dao = new RemedioDAO();
			
			while(rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getDouble("valor"));
				item.setQuantidade(rs.getInt("quantidade"));
				item.setFarmacia(dao.getById(rs.getInt("id_remedio")));
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
		
		return venda;
	}

	@Override
	public boolean update(Venda obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Venda> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
