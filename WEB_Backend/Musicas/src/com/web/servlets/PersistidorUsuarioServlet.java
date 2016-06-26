package com.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.database.dao.UsuarioDao;

/**
 * Servlet implementation class PersistidorUsuarioServlet
 */
@WebServlet("/PersistidorUsuarioServlet")
public class PersistidorUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersistidorUsuarioServlet() {
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
		
		int idUsuario = objeto.get("idUsuario").getAsInt();
		String nome = objeto.get("nome").getAsString();
		String email = objeto.get("email").getAsString();
		
		String imagem = null;
		JsonElement elementoImagem = objeto.get("imagemPerfil");
		if (elementoImagem != null && !elementoImagem.isJsonNull()) {
			imagem = elementoImagem.getAsString();
		}

		UsuarioDao dao = new UsuarioDao();
		dao.atualizar(idUsuario, nome, email, imagem);
	}

}
