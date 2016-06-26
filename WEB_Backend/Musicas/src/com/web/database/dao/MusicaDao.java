package com.web.database.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.web.database.MySql;
import com.web.helper.ConversaoHelper;
import com.web.model.Album;
import com.web.model.Banda;
import com.web.model.Genero;
import com.web.model.Musica;
import com.web.model.relatorios.DadosRelatorio;

public class MusicaDao {

	public List<Musica> obterPorQuery(String valor) {
		try  {
			String query = "select musica.idMusica, musica.titulo, musica.descricao, banda.idBanda, banda.nome, album.idAlbum, album.capa, album.nome, album.ano, genero.idGenero, genero.nome, musica.letra, musica.descricao, musica.tags from musicas musica join generos genero on musica.idGenero = genero.idGenero join bandas banda on musica.idBanda = banda.idBanda join albuns album on musica.idAlbum = album.idAlbum where musica.tags like '%" + valor + "%'";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<Musica> retorno = montarMusicas(rs);
			
			rs.close();
			stmt.close();
			
			return retorno;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public List<Musica> obterPorParametros(String titulo, String banda, String genero, String letra, String tags) {
		try {
			String query = "select musica.idMusica, musica.titulo, musica.descricao, banda.idBanda, banda.nome, album.idAlbum, album.capa, album.nome, album.ano, genero.idGenero, genero.nome, musica.letra, musica.descricao, musica.tags from musicas musica join generos genero on musica.idGenero = genero.idGenero join bandas banda on musica.idBanda = banda.idBanda join albuns album on musica.idAlbum = album.idAlbum where"; 
			query = adicionarClausulaWhere(query, "musica.titulo", titulo);
			query = adicionarClausulaWhere(query, "banda.nome", banda);
			query = adicionarClausulaWhere(query, "genero.nome", genero);
			query = adicionarClausulaWhere(query, "musica.letra", letra);
			query = adicionarClausulaWhere(query, "musica.tags", tags);
			
			query += " COLLATE utf8_general_ci";
			
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			List<Musica> retorno = montarMusicas(rs);

			rs.close();
			stmt.close();

			return retorno;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	private String adicionarClausulaWhere(String comando, String campo, String valor) {
		if (valor == null) {
			return comando;
		}
		
		if (comando.endsWith("where")) {
			return comando += " " + campo + " like '%" + valor.replace("'", "\\'") + "%'";
		} else {
			return comando += " and " + campo + " like '%" + valor.replace("'", "\\'") + "%'";
		}
	}

	public List<Musica> obterFavoritasParaUsuario(int idUsuario) {
		try  {
			String query = "select musica.idMusica, musica.titulo, musica.descricao, banda.idBanda, banda.nome, album.idAlbum, album.capa, album.nome, album.ano, genero.idGenero, genero.nome, musica.letra, musica.descricao, musica.tags from musicas musica join generos genero on musica.idGenero = genero.idGenero join bandas banda on musica.idBanda = banda.idBanda join albuns album on musica.idAlbum = album.idAlbum join usuarios_musicas on musica.idMusica = usuarios_musicas.idMusica where usuarios_musicas.idUsuario = " + idUsuario + " and usuarios_musicas.favorita = 1";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			List<Musica> retorno = montarMusicas(rs);
			
			rs.close();
			stmt.close();
			
			return retorno;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public int obterNotaMusica(int idMusica, int idUsuario) {
		try  {
			String query = "select nota from usuarios_musicas where idMusica = " + idMusica + " and idUsuario = " + idUsuario + ";";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next())
				return rs.getInt(1);
			else 
				return 0;
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}
	
	public boolean ehMusicaFavorita(int idMusica, int idUsuario) {
		try  {
			String query = "select favorita from usuarios_musicas where idMusica = " + idMusica + " and idUsuario = " + idUsuario + ";";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next())
				return rs.getInt(1) == 1;
			else 
				return false;
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void atualizarMusica(Musica musica) {
		try {
			String template = "update musicas set idAlbum = %d, idBanda = %d, idGenero = %d, titulo = '%s', descricao = '%s', letra = '%s', tags = '%s' where idMusica = %d;"; 
			
			String update = String.format(template, 
										  musica.getAlbum().getIdAlbum(),
										  musica.getBanda().getIdBanda(),
										  musica.getGenero().getIdGenero(),
										  musica.getNome(),
										  musica.getInformacoes().replace("'", "\\'"),
										  musica.getLetra().replace("'", "\\'"),
										  musica.getTags(),
										  musica.getIdMusica());
			
			Statement stmt = MySql.getConexao().createStatement();
			stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
			stmt.executeUpdate(update);
			stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");

			stmt.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void inserirNovaMusica(Musica musica) {
		try {
			String template = "insert into musicas (idGenero, idAlbum, idBanda, titulo, descricao, letra, tags) values(%d, %d, %d, '%s', '%s', '%s', '%s');";

			String update = String.format(template, 
										  musica.getGenero().getIdGenero(), 
										  musica.getAlbum().getIdAlbum(),
										  musica.getBanda().getIdBanda(), 
										  musica.getNome(), 
										  musica.getInformacoes().replace("'", "\\'"),
										  musica.getLetra().replace("'", "\\'"), 
										  musica.getTags());

			Statement stmt = MySql.getConexao().createStatement();
			stmt.executeUpdate(update);

			stmt.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void atualizarStatusFavorita(int idMusica, int idUsuario, boolean favorita) {
		try  {
			String query = "select favorita from usuarios_musicas where idMusica = " + idMusica + " and idUsuario = " + idUsuario + ";";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int favoritaNumerico = (favorita ? 1 : 0); 
			
			if (rs.next())
				stmt.executeUpdate("update usuarios_musicas set favorita = " + favoritaNumerico + " where idMusica = " + idMusica + " and idUsuario = " + idUsuario + ";");
			else 
				stmt.executeUpdate("insert into usuarios_musicas (idUsuario, idMusica, nota, favorita) values (" + idUsuario + ", " + idMusica + ", 0, " + favoritaNumerico + ");");
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void atualizarNotaMusica(int idMusica, int idUsuario, int nota) {
		try  {
			String query = "select nota from usuarios_musicas where idMusica = " + idMusica + " and idUsuario = " + idUsuario + ";";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next())
				stmt.executeUpdate("update usuarios_musicas set nota = " + nota + " where idMusica = " + idMusica + " and idUsuario = " + idUsuario + ";");
			else 
				stmt.executeUpdate("insert into usuarios_musicas (idUsuario, idMusica, nota, favorita) values (" + idUsuario + ", " + idMusica + ", " + nota + ", 0);");
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public Musica obterPorId(int id) {
		try  {
			String query = "select musica.idMusica, musica.titulo, musica.descricao, banda.idBanda, banda.nome, album.idAlbum, album.capa, album.nome, album.ano, genero.idGenero, genero.nome, musica.letra, musica.descricao, musica.tags from musicas musica join generos genero on musica.idGenero = genero.idGenero join bandas banda on musica.idBanda = banda.idBanda join albuns album on musica.idAlbum = album.idAlbum where musica.idMusica = " + id;
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			Musica retorno = null;
			
			if (rs.next())
				retorno = montarMusica(rs);
			
			rs.close();
			stmt.close();
			
			return retorno;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public List<DadosRelatorio> obterMusicasMaisFavoritas() {
		try {
			String query = "select musicas.titulo, count(*) as quantidade from usuarios_musicas join musicas on musicas.idMusica = usuarios_musicas.idMusica where usuarios_musicas.favorita = 1 group by usuarios_musicas.idMusica order by quantidade desc limit 10;";
			Statement comando = MySql.getConexao().createStatement();
			ResultSet rs = comando.executeQuery(query);
			
			return new ConversaoHelper().converterParaDadosRelatorio(rs);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private List<Musica> montarMusicas(ResultSet rs) throws SQLException, UnsupportedEncodingException {
		List<Musica> retorno = new ArrayList<>();
		
		while(rs.next()) {
			Musica musica = montarMusica(rs);
			retorno.add(musica);
		}
		
		return retorno;
	}

	private Musica montarMusica(ResultSet rs) throws SQLException, UnsupportedEncodingException {
		Musica musica = new Musica();
		musica.setIdMusica(rs.getInt(1));
		musica.setNome(rs.getString(2));
		musica.setInformacoes(rs.getString(3));
		
		Banda banda = new Banda();
		banda.setIdBanda(rs.getInt(4));
		banda.setNome(rs.getString(5));
		musica.setBanda(banda);
		
		Album album = new Album();
		album.setIdAlbum(rs.getInt(6));
		album.setCapa(rs.getString(7));
		album.setNome(rs.getString(8));
		album.setAno(rs.getInt(9));
		musica.setAlbum(album);
		
		Genero genero = new Genero();
		genero.setIdGenero(rs.getInt(10));
		genero.setDescricao(rs.getString(11));
		musica.setGenero(genero);
		
		musica.setLetra(new String(rs.getString(12).getBytes(), "UTF-8"));
		musica.setInformacoes(rs.getString(13));
		musica.setTags(rs.getString(14));
		
		return musica;
	}
}
