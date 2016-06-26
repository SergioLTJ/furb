package com.web.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.web.database.MySql;
import com.web.helper.ConversaoHelper;
import com.web.model.Usuario;
import com.web.model.relatorios.DadosRelatorio;

public class UsuarioDao {

	public Usuario obterUsuarioSemImagem(String login, String senha) {
		try  {
			String query = "select idUsuario, usuario, senha, email, nome from usuarios where usuario = '" + login + "' and senha = '" + senha + "';";
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
		
		if (rs.getMetaData().getColumnCount() > 5) {
			usuario.setImagemPerfil(rs.getString(6));
		}
		
		return usuario;
	}
	
	public void inserirNovoUsuario(String usuario, String senhaBase64, String email) {
		try  {
			String insert = "insert into usuarios (usuario, senha, email, nome) values ('" + usuario + "', '" + senhaBase64 + "', '" + email + "', 'Anônimo');";
			Statement stmt = MySql.getConexao().createStatement();
			stmt.executeUpdate(insert);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}	
	}

	public void atualizar(int id, String nome, String email, String imagem) {
		try  {
			String template = "update usuarios set nome = '%s', email = '%s'";

			if (imagem != null) {
				template += ", imagem = '" + imagem + "'";
			}

			template += " where idUsuario = '%d'";

			String update = String.format(template, nome, email, id);
			Statement stmt = MySql.getConexao().createStatement();
			
			stmt.executeUpdate(update);

			stmt.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}		
	}

	public List<DadosRelatorio> obterUsuariosMaisAtivos() {
		try {
			String query = "select usuarios.nome, count(*) as quantidade from historico_edicao join usuarios on usuarios.idUsuario = historico_edicao.idUsuario group by historico_edicao.idUsuario order by quantidade desc limit 10;";
			Statement comando = MySql.getConexao().createStatement();
			ResultSet rs = comando.executeQuery(query);
			
			return new ConversaoHelper().converterParaDadosRelatorio(rs);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
