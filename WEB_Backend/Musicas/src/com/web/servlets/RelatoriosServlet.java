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
import com.web.database.dao.MusicaDao;
import com.web.database.dao.TermosPesquisaDao;
import com.web.database.dao.UsuarioDao;

/**
 * Servlet implementation class RelatoriosServlet
 */
@WebServlet("/RelatoriosServlet")
public class RelatoriosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatoriosServlet() {
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
		String tipo = objeto.get("tipo").getAsString();
		
		Gson gson = new Gson();
		
		switch (tipo) {
		case "favoritas":
			response.getWriter().write(gson.toJson(new MusicaDao().obterMusicasMaisFavoritas()));
			break;
		case "usuarios":
			response.getWriter().write(gson.toJson(new UsuarioDao().obterUsuariosMaisAtivos()));
			break;
		case "termos":
			response.getWriter().write(gson.toJson(new TermosPesquisaDao().obterTermosMaisPesquisados()));
			break;
		}
	}

}
