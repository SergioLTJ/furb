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

/**
 * Servlet implementation class NotaServlet
 */
@WebServlet("/NotaServlet")
public class NotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());
		String operacao = objeto.get("operacao").getAsString();
		
		int idUsuario = objeto.get("idUsuario").getAsInt();
		int idMusica = objeto.get("idMusica").getAsInt();
		
		MusicaDao dao = new MusicaDao();
		
		if (operacao.equals("obter")) {
			response.getWriter().write(new Gson().toJson(dao.obterNotaMusica(idMusica, idUsuario)));
		} else {
			int nota = objeto.get("nota").getAsInt();
			dao.atualizarNotaMusica(idMusica, idUsuario, nota);
		}
	}

}
