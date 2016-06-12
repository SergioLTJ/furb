package com.web.servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.AlbumDao;
import com.web.database.dao.BandaDao;
import com.web.database.dao.GeneroDao;

/**
 * Servlet implementation class InsercaoServlet
 */
@WebServlet("/InsercaoServlet")
public class InsercaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsercaoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());

		boolean retorno = false;
		
		switch(objeto.get("tipo").getAsString()) {
		case "banda":
			String nomeBanda = objeto.get("nome").getAsString();
			retorno = new BandaDao().inserir(nomeBanda);
			break;
		case "genero":
			String nomeGenero = objeto.get("nome").getAsString();
			retorno = new GeneroDao().inserir(nomeGenero);
			break;
		case "album":
			String nomeAlbum = objeto.get("nome").getAsString();
			int anoAlbum = objeto.get("ano").getAsInt();
			String imagemBase64 = objeto.get("imagem").getAsString();
			retorno = new AlbumDao().inserir(nomeAlbum, anoAlbum, imagemBase64); 
		}
		
		response.getWriter().write(String.valueOf(retorno));
	}

}
