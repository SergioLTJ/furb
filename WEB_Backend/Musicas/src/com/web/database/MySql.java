package com.web.database;

import java.sql.DriverManager;
import java.sql.Connection;

public class MySql {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/musicas";
	private static final String USUARIO = "sergio";
	private static final String SENHA = "mysql";
	
	private static Connection conexao;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static Connection getConexao() {
		return conexao;
	}
}
