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
import com.web.database.dao.UsuarioDao;
import com.web.model.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/CarregadorUsuarioServlet")
public class CarregadorUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarregadorUsuarioServlet() {
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
		int idUsuario = objeto.get("id").getAsInt();
		
		UsuarioDao dao = new UsuarioDao();
		Usuario usuario = dao.obterPorId(idUsuario);
		
		if(objeto.get("carregarMusicas").getAsBoolean()) {
			MusicaDao musicaDao = new MusicaDao();
			usuario.setFavoritas(musicaDao.obterFavoritasParaUsuario(idUsuario));
		}
		
		response.getWriter().write(new Gson().toJson(usuario));
	}

}
