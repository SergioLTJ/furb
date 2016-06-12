package com.web.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.web.database.MySql;
import com.web.model.Banda;

public class BandaDao {

	public List<Banda> obterTodos() {
		try {
			String query = "select bandas.idBanda, bandas.nome from bandas";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<Banda> retorno = montarBandas(rs);
			
			rs.close();
			stmt.close();
			
			return retorno;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public boolean inserir(String nome) {
		try {
			String insert = "insert into bandas (nome) values ('" + nome + "');";
			Statement stmt = MySql.getConexao().createStatement();
			int numeroLinhas = stmt.executeUpdate(insert);
			
			stmt.close();
			
			return numeroLinhas > 0;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	private List<Banda> montarBandas(ResultSet rs) throws SQLException{
		List<Banda> retorno = new ArrayList<>();
		
		while(rs.next()) {
			Banda banda = montarBanda(rs);
			retorno.add(banda);
		}
		
		return retorno;
	}

	private Banda montarBanda(ResultSet rs) throws SQLException {
		Banda banda = new Banda();
		banda.setIdBanda(rs.getInt(1));
		banda.setNome(rs.getString(2));
		return banda;
	}
}
