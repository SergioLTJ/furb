package com.web.database.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.web.database.MySql;
import com.web.helper.ConversaoHelper;
import com.web.model.relatorios.DadosRelatorio;

public class TermosPesquisaDao {

	public void gerarOuAtualizarRegistro(String termo) {
		try {
			String query = "select idTermoPesquisa, quantidade from termos_pesquisa where termo = '" + termo + "';";
			Statement comando = MySql.getConexao().createStatement();
			ResultSet rs = comando.executeQuery(query);
			
			int quantidade = 0;
			
			if (rs.next()) {
				int id = rs.getInt(1);
				quantidade = rs.getInt(2);
				comando.executeUpdate("update termos_pesquisa set quantidade = " + ++quantidade + " where idTermoPesquisa = " + id + ";");
			} else {
				comando.executeUpdate("insert into termos_pesquisa (termo, quantidade) values ('" + termo + "', 1);");
			}
		} catch (Exception ex) { 
			ex.printStackTrace();
		}
	}
	
	public List<DadosRelatorio> obterTermosMaisPesquisados() {
		try {
			String query = "select termo, quantidade from termos_pesquisa order by quantidade desc limit 10";
			Statement comando = MySql.getConexao().createStatement();
			ResultSet rs = comando.executeQuery(query);
			
			return new ConversaoHelper().converterParaDadosRelatorio(rs);
		} catch (Exception ex) { 
			ex.printStackTrace();
			return null;
		}
	}
}
