package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TesteBanco {

	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver não encontrado. Faça o download.");
			e.printStackTrace();
			System.exit(-1);
		}
	
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/farmacia", 
					"topicos1", "123456");
		} catch (SQLException e) {
			System.out.println("Problema ao conectar no banco de dados. Verifique as informacoes de conexao.");
			e.printStackTrace();
		}
		
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  id, ");
		sql.append("  nome, ");
		sql.append("  tipo_remedio, ");
		sql.append("  valor ");
		sql.append("FROM ");
		sql.append("  remedio  ");
		
		
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			System.out.println("nada");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("nome"));
				System.out.println(rs.getString("tipo_remedio"));
				System.out.println(rs.getDouble("valor"));
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
