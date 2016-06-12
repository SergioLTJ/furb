package com.web.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.AlbumDao;
import com.web.database.dao.BandaDao;
import com.web.database.dao.GeneroDao;

/**
 * Servlet implementation class CarregadorGeral
 */
@WebServlet("/CarregadorGeral")
public class CarregadorGeral extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarregadorGeral() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());
		 
		String tipo = objeto.get("tipo").getAsString();
		Gson gson = new Gson();
		
		switch (tipo) {
		case "generos":
			response.getWriter().write(gson.toJson(new GeneroDao().obterTodos()));
			break;
		case "bandas":
			response.getWriter().write(gson.toJson(new BandaDao().obterTodos()));
			break;
		case "albuns":
			response.getWriter().write(gson.toJson(new AlbumDao().obterTodosSemImagem()));
			break;
		}
	}

}
