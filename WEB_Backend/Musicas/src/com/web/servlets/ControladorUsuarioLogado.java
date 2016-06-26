package com.web.servlets;


import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject)parser.parse(request.getReader());
		
		switch (objeto.get("operacao").getAsString()) {
		case "login":
			this.realizarLogin(request, response, objeto);
			break;
		case "obterLogado":
			this.obterUsuarioLogado(request, response, objeto);
			break;
		case "logoff":
			this.realizarLogoff(request);
			break;
		}
	}

	private void realizarLogin(HttpServletRequest request, HttpServletResponse response, JsonObject objeto) throws IOException {
		String login = objeto.get("usuario").getAsString();
		
		String senha = objeto.get("senha").getAsString();
		String senhaBase64 = Base64.getEncoder().encodeToString(senha.getBytes());

		UsuarioDao dao = new UsuarioDao();
		Usuario usuario = dao.obterUsuarioSemImagem(login, senhaBase64);

		Gson gson = new Gson();
		
		if (usuario != null) {
			request.getSession().setAttribute("UsuarioLogado", usuario);
			response.getWriter().write(gson.toJson(usuario));
		}
	}
	
	private void obterUsuarioLogado(HttpServletRequest request, HttpServletResponse response, JsonObject objeto) throws IOException {
		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("UsuarioLogado");
		Gson gson = new Gson();
		
		if (usuarioLogado != null) {
			response.getWriter().write(gson.toJson(usuarioLogado));
			return;
		}
	}

	private void realizarLogoff(HttpServletRequest request) {
		request.getSession().setAttribute("UsuarioLogado", null);
	}
}
