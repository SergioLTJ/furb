package com.web.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.web.database.MySql;
import com.web.model.Usuario;

public class UsuarioDao {

	public Usuario obterUsuario(String login, String senha) {
		try  {
			String query = "select * from usuarios where usuario = '" + login + "' and senha = '" + senha + "';";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			Usuario usuario = null;
			
			if (rs.next()) {
				return this.montarUsuario(rs);
			}
			
			rs.close();
			stmt.close();
			
			return usuario;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public Usuario obterPorId(int idUsuario) {
		try  {
			String query = "select * from usuarios where idUsuario = " + idUsuario + ";";
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			Usuario usuario = null;
			
			if (rs.next()) {
				return this.montarUsuario(rs);
			}
			
			rs.close();
			stmt.close();
			
			return usuario;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	private Usuario montarUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(rs.getInt(1));
		usuario.setEmail(rs.getString(4));
		usuario.setNome(rs.getString(5));
		usuario.setImagemPerfil(rs.getString(6));
		return usuario;
	}
	
	public void inserirNovoUsuario(String usuario, String senhaBase64, String email) {
		try  {
			String insert = "insert into usuarios (usuario, senha, email) values ('" + usuario + "', '" + senhaBase64 + "', '" + email + "');";
			Statement stmt = MySql.getConexao().createStatement();
			stmt.executeUpdate(insert);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}	
	}

	public void atualizar(int id, String nome, String email, String imagem) {
		try  {
			String template = "update usuarios set nome = '%s', email = '%s', imagem = '%s' where idUsuario = '%d'";
			String update = String.format(template, nome, email, imagem, id);
			Statement stmt = MySql.getConexao().createStatement();
			
			stmt.executeUpdate(update);

			stmt.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}		
	}
	
}
