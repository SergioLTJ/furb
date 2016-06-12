package com.web.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.web.database.MySql;
import com.web.model.Genero;

public class GeneroDao {

	public List<Genero> obterTodos() {
		try  {
			String query = "select generos.idGenero, generos.nome from generos";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<Genero> retorno = montarGeneros(rs);
			
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
			String insert = "insert into generos (nome) values ('" + nome + "');";
			Statement stmt = MySql.getConexao().createStatement();
			int numeroLinhas = stmt.executeUpdate(insert);
			
			stmt.close();
			
			return numeroLinhas > 0;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	private List<Genero> montarGeneros(ResultSet rs) throws SQLException{
		List<Genero> retorno = new ArrayList<>();
		
		while(rs.next()) {
			Genero genero = montarGenero(rs);
			retorno.add(genero);
		}
		
		return retorno;
	}

	private Genero montarGenero(ResultSet rs) throws SQLException {
		Genero genero = new Genero();
		genero.setIdGenero(rs.getInt(1));
		genero.setDescricao(rs.getString(2));
		return genero;
	}
}
