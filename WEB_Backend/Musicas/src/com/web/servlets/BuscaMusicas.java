package com.web.servlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.MusicaDao;
import com.web.helper.GsonHelper;
import com.web.model.Musica;

/**
 * Servlet implementation class BuscaMusicas
 */
@WebServlet("/BuscaMusicas")
public class BuscaMusicas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscaMusicas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());
		String tipoBusca = objeto.get("tipoBusca").getAsString();
		
		MusicaDao dao = new MusicaDao();
		List<Musica> musicas = new ArrayList<>();
		Gson gson = new Gson();
		
		if(tipoBusca.equals("Basica")) {
			musicas = dao.obterPorQuery(objeto.get("query").getAsString());
			response.getWriter().write(gson.toJson(musicas));
		} else if (tipoBusca.equals("Avancada")) {
			musicas = dao.obterPorParametros(
					      GsonHelper.obterValorOuNull(objeto.get("titulo")),
					      GsonHelper.obterValorOuNull(objeto.get("banda")), 
					      GsonHelper.obterValorOuNull(objeto.get("genero")), 
					      GsonHelper.obterValorOuNull(objeto.get("letra")), 
						  GsonHelper.obterValorOuNull(objeto.get("termosBusca"))
					  );
			
			response.getWriter().write(gson.toJson(musicas));
		} else {
			Musica musica = dao.obterPorId(objeto.get("id").getAsInt());
			response.getWriter().write(gson.toJson(musica));
		}
	}

	
	
}
