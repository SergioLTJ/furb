package com.web.servlets;


import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.UsuarioDao;

/**
 * Servlet implementation class Cadastro
 */
@WebServlet("/Cadastro")
public class Cadastro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cadastro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonParser parser = new JsonParser();
		JsonObject objeto = (JsonObject) parser.parse(request.getReader());

		String usuario = objeto.get("usuario").getAsString();
		String senha = objeto.get("senha").getAsString();
		String email = objeto.get("email").getAsString();
				
		String senhaBase64 = Base64.getEncoder().encodeToString(senha.getBytes());
		
		UsuarioDao dao = new UsuarioDao();
		
		dao.inserirNovoUsuario(usuario, senhaBase64, email);
		
		response.getWriter().write("Sucesso");
	}

}
