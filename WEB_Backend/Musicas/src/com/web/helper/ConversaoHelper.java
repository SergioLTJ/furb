package com.web.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.web.model.Album;
import com.web.model.Banda;
import com.web.model.Genero;
import com.web.model.Musica;
import com.web.model.Usuario;
import com.web.model.relatorios.DadosRelatorio;

public class ConversaoHelper {

	public Musica conveterJsonParaMusica(JsonObject musicaJson) {
		Musica musica = new Musica();
		musica.setIdMusica(obterValorInt(musicaJson, "idMusica"));
		musica.setInformacoes(obterValorString(musicaJson, "informacoes"));
		musica.setLetra(obterValorString(musicaJson, "letra"));
		musica.setNome(obterValorString(musicaJson, "nome"));
		musica.setTags(obterValorString(musicaJson, "tags"));
		
		musica.setAlbum(this.converterJsonParaAlbum(musicaJson.get("album").getAsJsonObject()));
		musica.setBanda(this.converterJsonParaBanda(musicaJson.get("banda").getAsJsonObject()));
		musica.setGenero(this.converterJsonParaGenero(musicaJson.get("genero").getAsJsonObject()));
		
		return musica;
	}
	
	public Album converterJsonParaAlbum(JsonObject albumJson) {
		Album album = new Album();
		
		album.setIdAlbum(obterValorInt(albumJson, "idAlbum"));
		album.setAno(obterValorInt(albumJson, "ano"));
		album.setNome(obterValorString(albumJson, "nome"));
		
		return album;
	}

	private Banda converterJsonParaBanda(JsonObject bandaJson) {
		Banda banda = new Banda();
		
		banda.setIdBanda(obterValorInt(bandaJson, "idBanda"));
		banda.setNome(obterValorString(bandaJson, "nome"));
		
		return banda;
	}

	private Genero converterJsonParaGenero(JsonObject generoJson) {
		Genero genero = new Genero();
		
		genero.setIdGenero(obterValorInt(generoJson, "idGenero"));
		genero.setDescricao(obterValorString(generoJson, "descricao"));
		
		return genero;
	}

	public Usuario converterJsonParaUsuario(JsonObject usuarioJson) {
		if (usuarioJson == null || usuarioJson.isJsonNull()) return null;
		
		Usuario usuario = new Usuario();
		
		usuario.setIdUsuario(obterValorInt(usuarioJson, "idUsuario"));
		
		return usuario;
	}
	
	public int obterValorInt(JsonObject objeto, String nomePropriedade) {
		JsonElement elemento = objeto.get(nomePropriedade);
		if (elemento == null || elemento.isJsonNull()) return 0;
		return elemento.getAsInt();
	}

	public String obterValorString(JsonObject objeto, String nomePropriedade) {
		JsonElement elemento = objeto.get(nomePropriedade);
		if (elemento == null || elemento.isJsonNull()) return null;
		return elemento.getAsString();
	}
	
	public List<DadosRelatorio> converterParaDadosRelatorio(ResultSet rs) throws SQLException {
		List<DadosRelatorio> retorno = new ArrayList<>();
		
		while (rs.next()) {
			DadosRelatorio dados = new DadosRelatorio();
			dados.addDado(rs.getString(1));
			dados.addDado(rs.getInt(2));
			retorno.add(dados);
		}
		
		return retorno;
	}
}
