package com.web.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.web.database.MySql;
import com.web.model.Album;

public class AlbumDao {

	public boolean inserir(String nome, int ano, String imagem) {
		try {
			String template = "insert into albuns (nome, ano, capa) values ('%s', '%d', '%s');"; 
			String insert = String.format(template, nome.replace("'", "\\'"), ano, imagem);
			Statement stmt = MySql.getConexao().createStatement();
			int numeroLinhas = stmt.executeUpdate(insert);

			stmt.close();

			return numeroLinhas > 0;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public List<Album> obterTodosSemImagem() {
		try  {
			String query = "select albuns.idAlbum, albuns.nome, albuns.ano from albuns;";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<Album> retorno = montarAlbunsSemImagem(rs);
			
			rs.close();
			stmt.close();
			
			return retorno;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	private List<Album> montarAlbunsSemImagem(ResultSet rs) throws SQLException{
		List<Album> albuns = new ArrayList<>();
		
		while(rs.next()) {
			Album album = new Album();
			album.setIdAlbum(rs.getInt(1));
			album.setNome(rs.getString(2));
			album.setAno(rs.getInt(3));
			albuns.add(album);
		}
		
		return albuns;
	}

}
