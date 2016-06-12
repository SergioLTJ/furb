package com.web.servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.MusicaDao;
import com.web.edicao.ComparadorMusicas;
import com.web.helper.ConversaoHelper;
import com.web.model.Musica;
import com.web.model.Usuario;

/**
 * Servlet implementation class EdicaoMusicaServlet
 */
@WebServlet("/EdicaoMusicaServlet")
public class EdicaoMusicaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EdicaoMusicaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());
		JsonObject musicaJson = objeto.get("musica").getAsJsonObject();
		
		MusicaDao dao = new MusicaDao();
		ConversaoHelper conversor = new ConversaoHelper();
		
		Musica musicaNova = conversor.conveterJsonParaMusica(musicaJson);
		
		if (objeto.get("operacao") != null && objeto.get("operacao").getAsString().equals("inserir")) {
			dao.inserirNovaMusica(musicaNova);
		} else {
			Musica musicaBase = dao.obterPorId(musicaNova.getIdMusica());
			
			Usuario usuarioAlteracao = conversor.converterJsonParaUsuario(objeto.get("usuario").getAsJsonObject()); 
			
			ComparadorMusicas comparador = new ComparadorMusicas();
			comparador.comparar(usuarioAlteracao, musicaBase, musicaNova);
			
			dao.atualizarMusica(musicaNova);
		}
	}

}
