package com.web.servlets;


import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.UsuarioDao;
import com.web.model.Usuario;

/**
 * Servlet implementation class ControladorUsuarioLogado
 */
@WebServlet("/ControladorUsuarioLogado")
public class ControladorUsuarioLogado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControladorUsuarioLogado() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("UsuarioLogado");
		Gson gson = new Gson();
		
		if (usuarioLogado != null) {
			response.getWriter().write(gson.toJson(usuarioLogado));
			return;
		}
		
		JsonParser parser = new JsonParser();
		Object json = parser.parse(request.getReader());
		JsonObject objeto = json instanceof JsonNull ? null : (JsonObject)json;

		if (objeto != null) {
			String login = objeto.get("usuario").getAsString();
			
			String senha = objeto.get("senha").getAsString();
			String senhaBase64 = Base64.getEncoder().encodeToString(senha.getBytes());

			UsuarioDao dao = new UsuarioDao();
			Usuario usuario = dao.obterUsuario(login, senhaBase64);

			if (usuario != null) {
				request.getSession().setAttribute("UsuarioLogado", usuario);
				response.getWriter().write(gson.toJson(usuario));
			}
		}
	}

}
