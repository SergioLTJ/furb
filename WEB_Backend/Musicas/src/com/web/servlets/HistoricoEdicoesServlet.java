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
import com.web.database.dao.HistoricoEdicaoDao;

/**
 * Servlet implementation class HistoricoEdicoesServlet
 */
@WebServlet("/HistoricoEdicoesServlet")
public class HistoricoEdicoesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoricoEdicoesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());
		int idMusica = objeto.get("idMusica").getAsInt();
		
		response.getWriter().write(new Gson().toJson(new HistoricoEdicaoDao().obterPorIdMusica(idMusica)));
	}

}
