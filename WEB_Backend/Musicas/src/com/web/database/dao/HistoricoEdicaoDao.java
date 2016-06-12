package com.web.database.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.web.database.MySql;
import com.web.model.HistoricoEdicao;
import com.web.model.Usuario;

public class HistoricoEdicaoDao {

	public void gerarRegistrosHistorico(List<HistoricoEdicao> alteracoes) {
		try  {
			for (HistoricoEdicao historicoEdicao : alteracoes) {
				String insert = gerarInsertParaHistorico(historicoEdicao); 
				Statement stmt = MySql.getConexao().createStatement();
				stmt.executeUpdate(insert);

				stmt.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private String gerarInsertParaHistorico(HistoricoEdicao historicoEdicao) {
		String template = "insert into historico_edicao (idUsuario, idMusica, campo, valor_antigo, valor_novo) values (%s, %d, '%s', '%s', '%s');";

		Usuario usuarioAlteracao = historicoEdicao.getUsuario();
		
		return String.format(template, 
							 usuarioAlteracao != null ? usuarioAlteracao.getIdUsuario() : "NULL", 
							 historicoEdicao.getMusica().getIdMusica(),
							 historicoEdicao.getCampo(),
							 historicoEdicao.getValorAntigo(),
							 historicoEdicao.getValorNovo());
	}
	
	public List<HistoricoEdicao> obterPorIdMusica(int idMusica) {	
		try {
			String query = "select h.*, u.nome from historico_edicao h join usuarios u on h.idUsuario = u.idUsuario where h.idMusica = " + idMusica; 
			
			Statement stmt = MySql.getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			List<HistoricoEdicao> retorno = montarHistoricos(rs);

			rs.close();
			stmt.close();

			return retorno;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	private List<HistoricoEdicao> montarHistoricos(ResultSet rs) throws SQLException, UnsupportedEncodingException {
		List<HistoricoEdicao> historicos = new ArrayList<>();
		
		while(rs.next()) {
			HistoricoEdicao historico = new HistoricoEdicao();
			historico.setCampo(rs.getString(4));
			historico.setValorAntigo(new String(rs.getString(5).getBytes(), "UTF-8"));
			historico.setValorNovo(new String(rs.getString(6).getBytes(), "UTF-8"));
			historico.setTipoAlteracao("Alteração");
			
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(rs.getInt(2));
			usuario.setNome(rs.getString(7));
			historico.setUsuario(usuario);
			
			historicos.add(historico);
		}
		
		return historicos;
		
	}
}
